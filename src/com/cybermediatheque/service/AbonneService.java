package com.cybermediatheque.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.Query;

import com.cybermediatheque.domain.Abonne;
import com.cybermediatheque.exception.CheckException;
import com.cybermediatheque.exception.FinderException;

/**
 * Composant permettant la gestion des services liés aux abonnés
 * @author sylar
 * @version 1.0
 *
 */
public class AbonneService extends HibernateService {

	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);

	/**
	 * Cette fonction permet de connecter un abonné
	 * @param id : login de l'abonné
	 * @param password : mot de passe de l'abonné
	 * @return l'abonné connecté
	 * @throws FinderException : si on ne trouve pas l'abonné
	 * @throws CheckException : si le formulaire de connection est mal rempli
	 */
	public Abonne authenticate(String id, String password) throws FinderException, CheckException {
		Query query = getSession().getNamedQuery("HQL_FIND_ABONNE_BY_ID");
		query.setString("id", id);
		Abonne abonne = (Abonne) query.uniqueResult();
		if (abonne == null) {
			throw new FinderException("Utilisateur inconnu");
		}
		if (!password.equals(abonne.getPassword())) {
			throw new CheckException("Mot de passe invalde");
		}
		return abonne;
	}

	/**
	 * Cette fonction permet de creer un nouvel abonné
	 * @param abonne l'abonné en cours de creation
	 * @param password2 mot de passe vérification
	 * @param dateNaissance date naissance abonné
	 * @throws CheckException si erreur lors de la saisie
	 * @throws ParseException si la date de naissance n'est pas une date valide
	 */
	public void creerAbonne(Abonne abonne, String password2, String dateNaissance)
			throws CheckException, ParseException {

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (dateNaissance.isEmpty()) {
			throw new CheckException("date naissance non renseignée");
		}
		abonne.setDateNaissance(dateFormat.parse(dateNaissance));

		if ("".equals(abonne.getId()) || "".equals(abonne.getPassword()) || "".equals(password2))
			throw new CheckException("Le login et le mot de passe doivent etre renseignés");

		if (!abonne.getPassword().equals(password2))
			throw new CheckException("Les mots de passe saisis sont différents");

		if (abonne.getNom().isEmpty() || abonne.getPrenom().isEmpty() || abonne.getDateNaissance().toString().isEmpty()
				|| abonne.getEmail().isEmpty())
			throw new CheckException("Le formulaire doit etre rempli");

		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(abonne.getEmail());
		if (!matcher.find())
			throw new CheckException("Format mail invalide");

		Abonne abonneIdExistant = this.trouverAbonneParId(abonne.getId());
		if (abonneIdExistant != null)
			throw new CheckException("Ce compte existe déjà");

		Abonne abonneMailExistant = this.trouverAbonneParMail(abonne.getEmail());
		if (abonneMailExistant != null)
			throw new CheckException("Il y a deja un compte associé à ce mail");

		getSession().beginTransaction();
		getSession().save(abonne);
		getSession().getTransaction().commit();
	}

	/**
	 * Cette fonction permet de trouver un abonné grace a son login
	 * @param idAbonne login de l'abonné
	 * @return l'abonné trouvé en base (ou pas)
	 */
	public Abonne trouverAbonneParId(String idAbonne) {
		Query query = getSession().getNamedQuery("HQL_FIND_ABONNE_BY_ID");
		query.setString("id", idAbonne);
		return (Abonne) query.uniqueResult();
	}

	/**
	 * Cette foncton permet de trouver un abonné grace a son email
	 * @param email email de l'abonné
	 * @return l'abonné trouvé en base (ou pas)
	 */
	public Abonne trouverAbonneParMail(String email) {
		Query query = getSession().getNamedQuery("HQL_FIND_ABONNE_BY_EMAIL");
		query.setString("email", email);
		return (Abonne) query.uniqueResult();
	}

	/**
	 * Cette fonction permet de recuperer le mot de passe d'un abonné depuis son mal
	 * @param email mail de l'abonné
	 * @return l'abonné trouvé en base
	 * @throws CheckException si le mail n'est pas au bon format
	 * @throws FinderException si l'abonné n'existe pas
	 */
	public Abonne recupererMotDePasse(String email) throws CheckException, FinderException {
		if (email == null || email.isEmpty())
			throw new CheckException("Le mail doit etre renseigné");
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
		if (!matcher.find())
			throw new CheckException("Format mail invalide");
		Query query = getSession().getNamedQuery("HQL_FIND_ABONNE_BY_EMAIL");
		query.setString("email", email);
		Abonne abonne = (Abonne) query.uniqueResult();
		if (abonne == null) {
			throw new FinderException("Email inconnu");
		}
		return abonne;
	}

	/**
	 * Cette fonction permet de modifier les informations d'un abonné
	 * @param abonne l'abonné dont on modifie les données
	 * @param dateNaissance date naissance abonné
	 * @throws CheckException email ou mot de passe non valide
	 * @throws ParseException si la date de naissance n'est pas au bon format
	 */
	public void modifierInformationsAbonne(Abonne abonne, String dateNaissance) throws CheckException, ParseException {
		if (abonne.getNom().isEmpty() || abonne.getPrenom().isEmpty() || abonne.getDateNaissance().toString().isEmpty()
				|| abonne.getEmail().isEmpty())
			throw new CheckException("Le formulaire doit etre rempli");

		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(abonne.getEmail());
		if (!matcher.find())
			throw new CheckException("Format mail invalide");

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (dateNaissance.isEmpty()) {
			throw new CheckException("date naissance non renseignée");
		}
		abonne.setDateNaissance(dateFormat.parse(dateNaissance));

		getSession().beginTransaction();
		getSession().update(abonne);
		getSession().getTransaction().commit();

	}

	/**
	 * Cette fonction retourne la liste des abonnés
	 * @return liste des abonnés
	 */
	public List<Abonne> recupererListeAbonnes() {
		Query query = getSession().getNamedQuery("HQL_FIND_ALL_ABONNES");
		List<Abonne> abonnes = query.list();
		return abonnes;
	}

	/**
	 * Cette fonction permet de bloquer un abonné
	 * @param abonneId login de l'abonné a bloquer
	 * @return l'abonné bloqué
	 */
	public Abonne bloquerAbonne(String abonneId) {
		Abonne abonne = this.trouverAbonneParId(abonneId);
		abonne.setBloque(true);
		getSession().beginTransaction();
		getSession().save(abonne);
		getSession().getTransaction().commit();
		return abonne;
	}

	/**
	 * Cette fonction permet de débloquer un abonné
	 * @param abonneId login de l'abonné a débloquer
	 * @return l'abonné débloqué
	 */
	public Abonne debloquerAbonne(String abonneId) {
		Abonne abonne = this.trouverAbonneParId(abonneId);
		abonne.setBloque(false);
		getSession().beginTransaction();
		getSession().save(abonne);
		getSession().getTransaction().commit();
		return abonne;
	}

	/**
	 * Cette fonction permet de modifier le mot de passe d'un abonné
	 * @param abonne abonne dont on veut modifier le mot de passe
	 * @param ancienMdp ancien mot de passe
	 * @param nouveauMdp1 nouveau mot de passe
	 * @param nouveauMdp2 vérification
	 * @throws CheckException si formulaire mal rempli
	 */
	public void modifierMotDePasseAbonne(Abonne abonne, String ancienMdp, String nouveauMdp1, String nouveauMdp2) throws CheckException {
		if(ancienMdp.isEmpty() || nouveauMdp1.isEmpty() || nouveauMdp2.isEmpty())
			throw new CheckException("Le formulaire doit etre rempli");
		if(!ancienMdp.equals(abonne.getPassword()))
			throw new CheckException("L'ancien mot de passe est invalide");
		if(!nouveauMdp1.equals(nouveauMdp2))
			throw new CheckException("Les mots de passe ne correspondent pas");
		abonne.setPassword(nouveauMdp1);
		getSession().beginTransaction();
		getSession().save(abonne);
		getSession().getTransaction().commit();
		
	}
}
