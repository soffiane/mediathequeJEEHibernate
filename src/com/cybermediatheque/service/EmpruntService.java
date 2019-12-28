package com.cybermediatheque.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;

import com.cybermediatheque.domain.Abonne;
import com.cybermediatheque.domain.Document;
import com.cybermediatheque.domain.Emprunt;
import com.cybermediatheque.domain.EmpruntPk;
import com.cybermediatheque.domain.Livre;

/**
 * Ce composant permet de gerer les services liés aux emprunts
 * @author sylar
 * @version 1.0
 *
 */
public class EmpruntService extends HibernateService {

	/**
	 * Cette fonction permet de recuperer la liste des emprunts contracté par un abonné
	 * @param abonneId l'id de l'abonné
	 * @return la liste des emprunts de l'abonné
	 */
	public List<Emprunt> recupererLivresEmpruntesParIdAbonne(String abonneId) {
		Query q = getSession().createNamedQuery("HQL_GET_EMPRUNTS_BY_ABONNE_ID");
		q.setString("abonneId", abonneId);
		return q.list();
	}

	/**
	 * Cette fonction permet a un abonné de reserver un livre
	 * @param livreId id du livre reservé
	 * @param abonneId id de l'abonné qui reserve le livre
	 * @return le livre reservé
	 */
	public Document reserverLivre(String livreId, String abonneId) {
		Date dateRetour = new Date();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, 14);
		dateRetour = cal.getTime();
		Livre document = this.findLivreById(livreId);
		Abonne abonne = this.findAbonneById(abonneId);
		document.setQuantite(document.getQuantite() - 1);
		Emprunt emprunt = new Emprunt();
		emprunt.setDateEmprunt(new Date());
		emprunt.setPk(new EmpruntPk(document, abonne));
		emprunt.setDateRetour(dateRetour);
		getSession().beginTransaction();
		getSession().save(emprunt);
		getSession().save(document);
		getSession().getTransaction().commit();
		return document;
	}
	
	/**
	 * Cette fonction permet de trouver un livre par son id
	 * @param livreId id du livre recherché
	 * @return le livre trouvé (ou rien)
	 */
	public Livre findLivreById(String livreId) {
		Query q = getSession().createNamedQuery("HQL_GET_BOOK_BY_ID");
		q.setString("id", livreId);
		return (Livre) q.getSingleResult();
	}
	
	/**
	 * Cette fonction permet de trouver un abonné grace a son id
	 * @param abonneId id de l'abonné recherché
	 * @return abonné trouvé (ou rien)
	 */
	private Abonne findAbonneById(String abonneId) {
		Query q = getSession().createNamedQuery("HQL_FIND_ABONNE_BY_ID");
		q.setString("id", abonneId);
		return (Abonne) q.getSingleResult();
	}
	
	/**
	 * Cette fonction permet de restituer un livre
	 * @param livreId id du livre rendu
	 * @param abonneId id de l'abonné qui rend le livre
	 * @return le livre rendu
	 */
	public Livre rendreLivre(String livreId, String abonneId) {
		Livre livre = this.findLivreById(livreId);
		Abonne abonne = this.findAbonneById(abonneId);
		livre.setQuantite(livre.getQuantite()+1);
		getSession().beginTransaction();
		Emprunt emprunt = getSession().find(Emprunt.class, new EmpruntPk(livre, abonne));
		getSession().remove(emprunt);
		getSession().getTransaction().commit();
		return livre;
	}

}
