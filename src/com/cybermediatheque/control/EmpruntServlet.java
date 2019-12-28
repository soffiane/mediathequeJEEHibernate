package com.cybermediatheque.control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cybermediatheque.domain.Abonne;
import com.cybermediatheque.domain.Livre;
import com.cybermediatheque.exception.AuthentificationException;
import com.cybermediatheque.service.EmpruntService;
import com.cybermediatheque.utilities.EmailSender;

/**
 * Servlet qui gere les requetes liées aux emprunts
 * @author sylar
 * @version 1.0
 */
@WebServlet("/emprunts")
public class EmpruntServlet extends HttpServlet {

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = (String) request.getParameter("action");
		String maVue = "/index.jsp";
		EmpruntService empruntService = new EmpruntService();
		Abonne abonne = (Abonne) request.getSession().getAttribute("abonne");

		try {
			if (action == null) {
			} else if (action.equals("reserverLivre")) {
				if(abonne == null)
					throw new AuthentificationException("Vous devez etre connecté pour acceder à cette fonctionnalité");
				String livreId = request.getParameter("livreId");
				Livre livre = (Livre) empruntService.reserverLivre(livreId,abonne.getId());
				request.setAttribute("livre", livre);
				EmailSender.envoyerMail(abonne.getEmail(),"Réservation d'un livre","Vous avez reserver le livre "+livre.getTitre());
				maVue = "/livres?action=afficherDetailLivre&id="+livre.getId();
			}else if (action.equals("rendreLivre")) {
				if(abonne == null)
					throw new AuthentificationException("Vous devez etre connecté pour acceder à cette fonctionnalité");
				String livreId = request.getParameter("livreId");
				String abonneId = request.getParameter("abonneId");
				Livre livre = empruntService.rendreLivre(livreId,abonneId);
				request.setAttribute("livre", livre);
				maVue = "/alertes?action=notifier&livreId="+livreId;
			}
		} catch (Exception e) {
			maVue = "/error.jsp";
			request.setAttribute("message", e.getMessage());
		}

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(maVue);
		dispatcher.forward(request, response);
	}

}
