package com.cybermediatheque.control;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cybermediatheque.domain.Abonne;
import com.cybermediatheque.exception.AuthentificationException;
import com.cybermediatheque.service.AbonneService;
import com.cybermediatheque.utilities.EmailSender;

/**
 * Cette Servlet permet la gestion des fonctionnalités liés à l'abonné création,
 * suppression, mise a jour, bloquer/debloquer, demander mot de passe, mettre a
 * jour profil
 * 
 * @author sylar
 * @version 1.0
 */
@WebServlet("/abonne")
public class AbonneServlet extends HttpServlet {

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");
		AbonneService abonneService = new AbonneService();
		Abonne abonne = (Abonne) request.getSession().getAttribute("abonne");
		String maVue = "/index.jsp";

		try {

			if (action == null) {
				// Creation du compte
			} else if (action.equals("creerAbonne")) {
				String customerId = request.getParameter("id_abonne");
				String password = request.getParameter("password");
				String password2 = request.getParameter("password2");
				String nom = request.getParameter("nom");
				String prenom = request.getParameter("prenom");
				String dateNaissance = request.getParameter("dateNaissance");
				String email = request.getParameter("email");

				Abonne newAbonne = new Abonne();
				newAbonne.setId(customerId);
				newAbonne.setPassword(password);
				newAbonne.setNom(nom);
				newAbonne.setPrenom(prenom);
				newAbonne.setEmail(email);

				abonneService.creerAbonne(newAbonne, password2, dateNaissance);
				EmailSender.envoyerMail(newAbonne.getEmail(), "Bienvenue sur la cybermediatheque",
						"Vous avez maintenant un compte abonné actif chez nous !");
				maVue = "/confirm.jsp?message=Votre compte a été crée";

			} else if (action.equals("retrievePassword")) {
				String email = request.getParameter("emailMdpOublie");
				Abonne returnAbonne = abonneService.recupererMotDePasse(email);
				EmailSender.envoyerMail(returnAbonne.getEmail(), "Recuperation du mot de passe",
						"Votre mot de passe est " + returnAbonne.getPassword());
				maVue = "/confirm.jsp?message=Votre mot de passe vous a ete envoyé par mail";

			} else if (action.equals("updateAbonne")) {
				if (abonne == null)
					throw new AuthentificationException("Vous devez etre connecté pour acceder à cette fonctionnalité");
				String nom = request.getParameter("nom");
				String prenom = request.getParameter("prenom");
				String dateNaissance = request.getParameter("dateNaissance");
				String email = request.getParameter("email");
				abonne.setNom(nom);
				abonne.setPrenom(prenom);
				abonne.setEmail(email);
				abonneService.modifierInformationsAbonne(abonne, dateNaissance);
				EmailSender.envoyerMail(abonne.getEmail(), "Mise a jour compte utilisateur",
						"Les informations de votre compte ont étés mise à jour");
				maVue = "/confirm.jsp?message=Vos informations ont ete mise à jour";
			} else if (action.equals("updatePassword")) {
				if (abonne == null)
					throw new AuthentificationException("Vous devez etre connecté pour acceder à cette fonctionnalité");
				String ancienMdp = request.getParameter("ancienMdp");
				String nouveauMdp1 = request.getParameter("nouveauMdp1");
				String nouveauMdp2 = request.getParameter("nouveauMdp2");
				abonneService.modifierMotDePasseAbonne(abonne, ancienMdp, nouveauMdp1, nouveauMdp2);
				EmailSender.envoyerMail(abonne.getEmail(), "Mise a jour compte utilisateur",
						"Votre mot de passe à été modifié");
				maVue = "/confirm.jsp?message=Vos informations ont ete mise à jour";
			} else if (action.equals("afficherAbonnes")) {
				if (abonne == null)
					throw new AuthentificationException("Vous devez etre connecté pour acceder à cette fonctionnalité");
				if (!"biblio".equals(abonne.getRole()) && !"admin".equals(abonne.getRole()))
					throw new AuthentificationException("Vous n'êtes pas habilité pour cette fonctionnalité");
				List<Abonne> abonnes = abonneService.recupererListeAbonnes();
				request.setAttribute("abonnes", abonnes);
				maVue = "/abonnes.jsp";
			} else if (action.equals("afficherDetailAbonne")) {
				if (abonne == null)
					throw new AuthentificationException("Vous devez etre connecté pour acceder à cette fonctionnalité");
				String abonneId = request.getParameter("id");
				Abonne returnAbonne = abonneService.trouverAbonneParId(abonneId);
				request.setAttribute("abonne", returnAbonne);
				maVue = "/account.jsp";
			} else if (action.equals("bloquerAbonne")) {
				if (abonne == null)
					throw new AuthentificationException("Vous devez etre connecté pour acceder à cette fonctionnalité");
				if (!"admin".equals(abonne.getRole()) && !"biblio".equals(abonne.getRole()))
					throw new AuthentificationException("Vous n'etes pas habilité a acceder à cette fonctionnalité");
				String abonneId = request.getParameter("abonneId");
				Abonne returnAbonne = abonneService.bloquerAbonne(abonneId);
				request.setAttribute("abonne", returnAbonne);
				maVue = "/account.jsp";
			} else if (action.equals("debloquerAbonne")) {
				if (abonne == null)
					throw new AuthentificationException("Vous devez etre connecté pour acceder à cette fonctionnalité");
				if (!"admin".equals(abonne.getRole()) && !"biblio".equals(abonne.getRole()))
					throw new AuthentificationException("Vous n'etes pas habilité a acceder à cette fonctionnalité");
				String abonneId = request.getParameter("abonneId");
				Abonne returnAbonne = abonneService.debloquerAbonne(abonneId);
				request.setAttribute("abonne", returnAbonne);
				maVue = "/account.jsp";
			}
		} catch (Exception e) {
			maVue = "/error.jsp?exception=" + e.getMessage();
			request.setAttribute("message", e.getMessage());
		}

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(maVue);
		dispatcher.forward(request, response);
	}

}
