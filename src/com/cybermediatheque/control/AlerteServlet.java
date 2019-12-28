package com.cybermediatheque.control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cybermediatheque.domain.Abonne;
import com.cybermediatheque.domain.Alerte;
import com.cybermediatheque.domain.Livre;
import com.cybermediatheque.exception.AlerteException;
import com.cybermediatheque.exception.AuthentificationException;
import com.cybermediatheque.service.AlerteService;

/**
 * Ce controleur gere les requetes HTTP concernant les alertes
 * 
 * @author sylar
 * @version 1.0
 */
@WebServlet("/alertes")
public class AlerteServlet extends HttpServlet {

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = (String) request.getParameter("action");
		String maVue = "/index.jsp";
		AlerteService alerteService = new AlerteService();
		Abonne abonne = (Abonne) request.getSession().getAttribute("abonne");

		try {
			if (action == null) {
			} else if (action.equals("creerAlerte")) {
				Livre livre = new Livre();
				String livreId = request.getParameter("livreId");
				if (abonne != null) {
					Alerte alerte = alerteService.trouverAlerte(livreId, abonne.getId());
					if (alerte == null) {
						livre = alerteService.creerAlerte(livreId, abonne.getId());
					} else {
						throw new AlerteException("Vous avez deja créer une alerte sur ce livre");
					}
					maVue = "/confirm.jsp?message=Une alerte a été crée sur ce livre";
				} else {
					throw new AuthentificationException("Vous devez etre connecté pour acceder à ce service");
				}
			} else if (action.equals("notifier")) {
				String livreId = request.getParameter("livreId");
				alerteService.notifier(livreId);
				maVue = "/livres?action=afficherLivresEmpruntes";
			}
		} catch (Exception e) {
			maVue = "/error.jsp";
			request.setAttribute("message", e.getMessage());
		}

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(maVue);
		dispatcher.forward(request, response);
	}

}
