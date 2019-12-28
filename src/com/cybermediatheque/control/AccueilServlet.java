package com.cybermediatheque.control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Cette Servlet est la servlet qui permet de gerer les requetes vers les pages d'accueil de l'application
 * @author sylar
 * @version 1.0
 */
@WebServlet("/accueil")
public class AccueilServlet extends HttpServlet {

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");
		String maVue = "/index.jsp";

		try {

			if (action == null) {
			} else if (action.equals("connection")) {
				maVue = "/login.jsp";
			} else if (action.equals("createAccount")) {
				maVue = "/createAccount.jsp";
			}else if (action.equals("account")) {
				maVue = "/account.jsp";
			} 
		} catch (Exception e) {
			maVue = "/erreur.jsp";
			request.setAttribute("message", e.getMessage());
		}

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(maVue);
		dispatcher.forward(request, response);
	}
}
