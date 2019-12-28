package com.cybermediatheque.service;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;

import org.hibernate.Query;

import com.cybermediatheque.domain.Abonne;
import com.cybermediatheque.domain.Alerte;
import com.cybermediatheque.domain.AlertePk;
import com.cybermediatheque.domain.Livre;
import com.cybermediatheque.utilities.EmailSender;

/**
 * Ce composant permet de gerer les services liés aux alertes
 * 
 * @author sylar
 * @version 1.0
 *
 */
public class AlerteService extends HibernateService {

	/**
	 * Cette fonction permet de trouver une alerte
	 * 
	 * @param idLivre  id du livre de l'alerte
	 * @param idAbonne id de l'abonné qui a crée l'alerte
	 * @return l'alerte (ou rien)
	 **/
	public Alerte trouverAlerte(String idLivre, String idAbonne) {
		Query query = getSession().getNamedQuery("HQL_FIND_ALERTE_BY_IDS");
		query.setString("idLivre", idLivre);
		query.setString("idAbonne", idAbonne);
		return (Alerte) query.uniqueResult();
	}

	/**
	 * Cette fonction permet de crée une alerte
	 * 
	 * @param livreId  id du livre de l'alerte
	 * @param abonneId id de l'abonné qui a crée l'alerte
	 * @return le livre en cours
	 * @throws IOException        en cas d'erreur lors du chargement de la
	 *                            configuration
	 * @throws MessagingException en cas d'erreur lors de l'envoi du mail
	 **/
	public Livre creerAlerte(String livreId, String abonneId) throws MessagingException, IOException {

		Livre livre = this.findLivreById(livreId);
		Abonne abonne = this.findAbonneById(abonneId);

		Alerte alerte = new Alerte();
		alerte.setEmail(abonne.getEmail());
		alerte.setPk(new AlertePk(livre, abonne));

		getSession().beginTransaction();
		getSession().save(alerte);
		try {
			EmailSender.envoyerMail(abonne.getEmail(), "Création alerte",
					"Une alerte a été crée sur le livre " + livre.getTitre());
			getSession().getTransaction().commit();
		} catch (Exception e) {
			getSession().getTransaction().rollback();
		}

		return livre;
	}

	/**
	 * Cette fonction permet de trouver un livre a partir de son id
	 * 
	 * @param livreId id du livre de l'alerte
	 * @return le livre (ou rien)
	 **/
	public Livre findLivreById(String livreId) {
		Query q = getSession().createNamedQuery("HQL_GET_BOOK_BY_ID");
		q.setString("id", livreId);
		return (Livre) q.getSingleResult();
	}

	/**
	 * Cette fonction permet de trouver un abonne a partir de son login
	 * 
	 * @param abonneId id de l'abonné
	 * @return l'abonné (ou rien)
	 **/
	private Abonne findAbonneById(String abonneId) {
		Query q = getSession().createNamedQuery("HQL_FIND_ABONNE_BY_ID");
		q.setString("id", abonneId);
		return (Abonne) q.getSingleResult();
	}

	/**
	 * Cette fonction permet d'envoyer des mails aux abonnés qui ont crée des
	 * alertes sur le livre
	 * 
	 * @param livreId id dont on cherche les alertes
	 * @throws MessagingException probleme d'envoi de mail
	 * @throws IOException        probleme de chargement des fichiers de conf
	 */
	public void notifier(String livreId) throws MessagingException, IOException {
		Query query = getSession().getNamedQuery("HQL_FIND_ALERTE_BY_BOOK_ID");
		query.setString("idLivre", livreId);
		List<Alerte> result = query.list();

		getSession().beginTransaction();
		for (Alerte alerte : result) {
			EmailSender.envoyerMail(alerte.getEmail(), "Un livre est de nouveau disponible",
					"Le livre " + alerte.getDocument().getTitre() + " est de nouveau disponible");
			getSession().delete(alerte);
		}
		getSession().getTransaction().commit();
	}

}
