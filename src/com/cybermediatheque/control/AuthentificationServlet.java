package com.cybermediatheque.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cybermediatheque.domain.Abonne;
import com.cybermediatheque.exception.CheckException;
import com.cybermediatheque.exception.FinderException;
import com.cybermediatheque.service.AbonneService;

/**
 * Servlet qui gere les requetes de connexion à l'application
 * @author sylar
 * @version 1.0
 */
@WebServlet("/connection")
public class AuthentificationServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = (String) request.getParameter("abonneId");
		String password = (String) request.getParameter("password");
		AbonneService abonneService;
		Abonne abonne;
		try {
			abonneService = new AbonneService();
			abonne = abonneService.authenticate(id, password);
			// Stocke dans la HTTPSession
		    request.getSession().setAttribute("abonne", abonne);

			getServletContext().getRequestDispatcher("/accueil.jsp").forward(request, response);
		} catch (FinderException e) {
			getServletContext().getRequestDispatcher("/error.jsp?exception="+e.getMessage()).forward(request,
					response);
		} catch (CheckException e) {
			getServletContext().getRequestDispatcher("/error.jsp?exception="+e.getMessage()).forward(request,
					response);
		}
	}
	
	

}
