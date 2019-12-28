package com.cybermediatheque.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet qui gere les requetes de déconnexion à l'application
 * @author sylar
 * @version 1.0
 */
@WebServlet("/deconnexion")
public class DeconnexionServlet extends HttpServlet {

    protected void service(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        // Invalidates the HTTPSession
        request.getSession().invalidate();

        // Goes to the index page passing the request
        getServletContext().getRequestDispatcher("/confirm.jsp?message=Vous etes deconnecté").forward(request, response);
    }
}
