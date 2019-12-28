package com.cybermediatheque.service;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;

import com.cybermediatheque.domain.Counter;
import com.cybermediatheque.domain.Emprunt;
import com.cybermediatheque.domain.Livre;
import com.cybermediatheque.exception.CheckException;

/**
 * Ce composant permet de gerer les services liés aux livres
 * @author sylar
 * @version 1.0
 */
public class LivreService extends HibernateService{

	/**
	 * Cette fonction permet de lister les livres de la cybermédiathèque
	 * @return la liste des livres de la cybermédiathèque
	 */
	public List<Livre> recupererLivres() {
		Query query =  getSession().getNamedQuery("HQL_GET_ALL_BOOKS");
		List<Livre> allLivres = query.getResultList();
		return allLivres;
	}
	
	/**
	 * Cette fonction permet de récuperer la liste des livres en cours d'emprunt
	 * @return la liste des emprunts
	 */
	public List<Emprunt> recupererLivresEmpruntes() {
		Query query =  getSession().getNamedQuery("HQL_GET_ALL_BOROWED_BOOKS");
		List<Emprunt> emprunts = query.list();
		return emprunts;
	}
	
	/**
	 * Cette fonction permet de recuperer les livres en fonction de l'auteur
	 * @param author auteur des livres recherché
	 * @return les livres de l'auteur saisis
	 */
	public List<Livre> recupererLivresParAuteur(String author) {
		Query query =  getSession().getNamedQuery("HQL_GET_ALL_BOOKS_BY_AUTHOR");
		query.setString("author", "%"+author.toLowerCase()+"%");
		List<Livre> allLivres = query.list();
		return allLivres;
	}
	
	/**
	 * Cette fonction permet de trouver un livre grace a son ISBN
	 * @param isbn identifiant national du livre recherché
	 * @return le livre dont l'isbn est celui renseigné (ou rien)
	 */
	public List<Livre> recupererLivreParISBN(String isbn) {
		Query query =  getSession().getNamedQuery("HQL_GET_BOOK_BY_ISBN");
		query.setString("isbn", isbn);
		List<Livre> allLivres = query.list();
		return allLivres;
	}

	/**
	 * Cette fonction permet de trouver un livre grace a son id
	 * @param id id du livre recherché
	 * @return le livre dont l'id est celui renseigné (ou rien)
	 */
	public Livre recupererLivreParId(String id) {
		Query query =  getSession().getNamedQuery("HQL_GET_BOOK_BY_ID");
		query.setString("id", id);
		Livre livre = (Livre) query.uniqueResult();
		return livre;
	}

	/**
	 * Cette fonction permet de modifier le nombre d'exemplaires d'un livre
	 * @param livreId identifiant du livre recherché
	 * @param quantité la nouvelle quantité du livre
	 * @return le livre dont la quantité vient d'etre modifié
	 * @throws CheckException si la quantité renseignée n'est pas un entier
	 */
	public Livre modifierQuantité(String livreId, String quantité) throws CheckException {
		Livre livre = this.recupererLivreParId(livreId);
		try{
			livre.setQuantite(Integer.valueOf(quantité));
		} catch(NumberFormatException e){
			throw new CheckException("La quantité doit être un entier");
		}
		getSession().beginTransaction();
		getSession().save(livre);
		getSession().getTransaction().commit();
		return livre;
	}

	/**
	 * Cette fonction permet de créer un livre
	 * @param livre livre en cours de création
	 * @param document pdf du livre
	 * @param image miniature de la couverture du livre
	 * @throws IOException en cas d'erreur lors du chargement du pdf
	 * @throws CheckException si le formulaire est mal rempli
	 */
	public void creerLivre(Livre livre, String document, String image) throws IOException, CheckException {
		
		if(livre.getTitre().isEmpty())
			throw new CheckException("Le titre doit etre renseigné");
		if(livre.getAuteur().isEmpty())
			throw new CheckException("L'auteur doit etre renseigné");
		if(livre.getEditeur().isEmpty())
			throw new CheckException("L'editeur doit etre renseigné");
		if(livre.getIsbn().isEmpty() || livre.getIsbn().matches("[0-9]"))
			throw new CheckException("L'ISBN doit etre renseigné et n'etre composé que de chiffres");
		
		this.verifierLivreDoublon(livre);
		
		this.chargerDocumentEtImage(livre, document,image);
		
		String nextId = this.getUniqueId("livre");
		livre.setId("liv"+nextId);
		livre.setQuantite(1);
		getSession().beginTransaction();
		getSession().save(livre);
		getSession().getTransaction().commit();
		this.updateUniqueId(nextId, "livre");
		
	}

	/**
	 * Cette fonction permet de convertir le document et l'image du livre au format blob
	 * @param livre
	 * @param document
	 * @param image
	 * @throws CheckException si format invalide
	 * @throws IOException si probleme de chargement de fichier
	 */
	private void chargerDocumentEtImage(Livre livre, String document, String image) throws CheckException, IOException {
		
		if(document != null && !"".equals(document)){
			String[] extensionDocument = document.split("\\.");
			if(!extensionDocument[1].equals("pdf"))
				throw new CheckException("Le document chargé n'est pas au format PDF");
				
			InputStream documentStream = new FileInputStream(document);
			int length = documentStream.available();
			byte[] data = new byte[length];
			BufferedInputStream in = new BufferedInputStream(documentStream);
			int result = in.read(data, 0, length);
			Blob blobDocument = Hibernate.getLobCreator(getSession()).createBlob(data);
			livre.setDocument(blobDocument);
			in.close();
		}
		
		if(image != null && !"".equals(image)){
			String[] extensionImage = image.split("\\.");
			if(!extensionImage[1].equals("jpg") && !extensionImage[1].equals("jpeg") && !extensionImage[1].equals("png"))
				throw new CheckException("L'image chargée n'est pas au format jpg/jpeg/png");
			
			InputStream imageStream = new FileInputStream(image);
			int lengthImage = imageStream.available();
			byte[] dataImage = new byte[lengthImage];
			BufferedInputStream inImage = new BufferedInputStream(imageStream);
			int resultImage = inImage.read(dataImage, 0, lengthImage);
			Blob blobImage = Hibernate.getLobCreator(getSession()).createBlob(dataImage);
			livre.setImage(blobImage);
			inImage.close();
		}
		
	}

	/**
	 * Cette fonction permet de modifier un livre
	 * @param livre le livre en cours de modification
	 * @param image miniature de la couverture du livre
	 * @param document pdf du livre
	 * @throws CheckException si le formulaire est incomplet
	 * @throws IOException en cas d'erreur de chargement d'un fichier
	 */
	public void modifierLivre(Livre livre, String document, String image) throws CheckException, IOException {
		if(livre.getTitre().isEmpty())
			throw new CheckException("Le titre doit etre renseigné");
		if(livre.getAuteur().isEmpty())
			throw new CheckException("L'auteur doit etre renseigné");
		if(livre.getEditeur().isEmpty())
			throw new CheckException("L'editeur doit etre renseigné");
		if(livre.getIsbn().isEmpty() || livre.getIsbn().matches("[0-9]"))
			throw new CheckException("L'ISBN doit etre renseigné et n'etre composé que de chiffres");
		
//		this.verifierLivreDoublon(livre);
		
		this.chargerDocumentEtImage(livre, document, image);
		
		getSession().beginTransaction();
		Object mergedBook = getSession().merge(livre);
		getSession().saveOrUpdate(mergedBook);
		getSession().getTransaction().commit();
	}

	/**
	 * Méthode de vérification de doublon
	 * @param livre
	 * @throws CheckException
	 */
	private void verifierLivreDoublon(Livre livre) throws CheckException {
		Livre livreStocké = this.recupererLivreParId(livre.getId());
		if(livre.getTitre().equals(livreStocké.getTitre()))
			throw new CheckException("Il existe deja un livre du meme titre");
		if(livre.getIsbn().equals(livreStocké.getIsbn()))
			throw new CheckException("Il existe deja un livre du meme ISBN");
	}

	/**
	 * Cette fonction permet de supprimer un livre 
	 * @param livreId id du livre a supprimer
	 */
	public void supprimerLivre(String livreId) {
		Livre livre = this.recupererLivreParId(livreId);
		getSession().beginTransaction();
		getSession().delete(livre);
		getSession().getTransaction().commit();
		
	}
	
	/**
	 * Cette fonction permet de trouver et de renvoyer le prochain id du livre en cours de créaton
	 * @param name nom du composant pour lequel on cherche l'id suivant
	 * @return id du livre en cours de création
	 */
	public String getUniqueId(final String name) {

		int nextId = 0;
		Query q = getSession().getNamedQuery("HQL_GET_CURRENT_ID");
		q.setString("name", name);
		Counter counter = (Counter) q.getSingleResult();
		nextId = counter.getNextId()+1;
		return String.valueOf(nextId);
	}
	
	/**
	 * Cette fonction permet de mettre à jour la table counter qui stocke les id
	 * @param nextId l'id du livre a stocker
	 * @param name le nom pour lequel on fait evoluer l'id
	 */
	public void updateUniqueId(String nextId, String name){
		getSession().beginTransaction();
		Query query = getSession().getNamedQuery("HQL_UPDATE_CURRENT_ID");
		query.setString("nextId", nextId);
		query.setString("name", name);
		query.executeUpdate();
		getSession().getTransaction().commit();
	}

}
