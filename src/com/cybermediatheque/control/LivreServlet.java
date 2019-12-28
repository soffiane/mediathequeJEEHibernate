package com.cybermediatheque.control;

import java.io.IOException;
import java.sql.Blob;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cybermediatheque.domain.Abonne;
import com.cybermediatheque.domain.Alerte;
import com.cybermediatheque.domain.Emprunt;
import com.cybermediatheque.domain.Genre;
import com.cybermediatheque.domain.Livre;
import com.cybermediatheque.exception.AuthentificationException;
import com.cybermediatheque.exception.CheckException;
import com.cybermediatheque.service.AlerteService;
import com.cybermediatheque.service.EmpruntService;
import com.cybermediatheque.service.LivreService;

import sun.misc.BASE64Encoder;

/**
 * Ce controleur gere les requetes HTTP concernant la gestion des livres
 * @author sylar
 * @version 1.0
 */
@WebServlet("/livres")
public class LivreServlet extends HttpServlet{

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = (String) request.getParameter("action");
		String maVue = "/index.jsp";
		LivreService livreService = new LivreService();
		EmpruntService empruntService = new EmpruntService();
		AlerteService alerteService = new AlerteService();
		Abonne abonne = (Abonne) request.getSession().getAttribute("abonne");
		
		try {
			if (action == null) {
			} else if (action.equals("afficherLivres")) {
				List<Livre> allLivres = livreService.recupererLivres();
				request.setAttribute("livres", allLivres);
				maVue = "/books.jsp";
			} else if (action.equals("afficherLivresEmpruntes")) {
				List<Emprunt> emprunts = livreService.recupererLivresEmpruntes();
				request.setAttribute("emprunts", emprunts);
				maVue = "/emprunts.jsp";
			} 
			else if (action.equals("afficherLivresParAuteur")) {
				String author = request.getParameter("auteur");
				List<Livre> allLivres = livreService.recupererLivresParAuteur(author);
				request.setAttribute("livres", allLivres);
				maVue = "/books.jsp";
			} 
			else if (action.equals("afficherLivresParISBN")) {
				if(abonne == null)
					throw new AuthentificationException("Vous devez etre connecté pour acceder à cette fonctionnalité");
				if(!"biblio".equals(abonne.getRole()) && !"admin".equals(abonne.getRole()))
					throw new AuthentificationException("Vous n'êtes pas habilité pour cette fonctionnalité");
				String isbn = request.getParameter("isbn");
				List<Livre> allLivres = livreService.recupererLivreParISBN(isbn);
				request.setAttribute("livres", allLivres);
				maVue = "/books.jsp";
			}else if (action.equals("afficherDetailLivre")) {
				String id = request.getParameter("id");
				Livre livre = livreService.recupererLivreParId(id);
				if(abonne != null){
					Alerte alerte = alerteService.trouverAlerte(livre.getId(),abonne.getId());
					request.setAttribute("alerte", alerte);
					List<Emprunt> emprunts = empruntService.recupererLivresEmpruntesParIdAbonne(abonne.getId());
					request.setAttribute("emprunts", emprunts);
				}
				if(livre.getDocument() != null){
					request.setAttribute("blob", livre.getDocument().length());
				}else{
					request.setAttribute("blob", 0);
				}
				if(livre.getImage().length() > 0){
					Blob blob = livre.getImage();
		            byte data[] = blob.getBytes(1, (int) blob.length());
		            // encoding the byte image string
		            BASE64Encoder base64Encoder = new BASE64Encoder();
		            StringBuilder imageString = new StringBuilder();
		            imageString.append("data:image/png;base64,");
		            imageString.append(base64Encoder.encode(data));
		            String image = imageString.toString();
		            
					request.setAttribute("image", image);
				}
				
				request.setAttribute("livre", livre);
				maVue = "/book.jsp";
			} else if (action.equals("telechargerLivre")) {
				if(abonne == null)
					throw new AuthentificationException("Vous devez etre connecté pour acceder à cette fonctionnalité");
				String isbn = request.getParameter("livreId");
				Livre livre = livreService.recupererLivreParId(isbn);
				ServletOutputStream out = response.getOutputStream();
				Blob blob = livre.getDocument();
				response.setContentType("application/pdf");
				response.setHeader("Content-Disposition",
						"attachment; filename=" + livre.getTitre()+".pdf");
				out.write(blob.getBytes(1, (int)blob.length()));
				out.flush();
				out.close();
			}else if (action.equals("modifierQuantite")) {
				if(abonne == null)
					throw new AuthentificationException("Vous devez etre connecté pour acceder à cette fonctionnalité");
				if(!"biblio".equals(abonne.getRole()) && !"admin".equals(abonne.getRole()))
					throw new AuthentificationException("Vous n'êtes pas habilité pour cette fonctionnalité");
				String quantité = request.getParameter("quantite");
				String livreId = request.getParameter("livreId");
				Livre livre = livreService.modifierQuantité(livreId,quantité);
				request.setAttribute("livre", livre);
				maVue = "/book.jsp";
			}
			else if (action.equals("ajouterLivre")) {
				if(abonne == null)
					throw new AuthentificationException("Vous devez etre connecté pour acceder à cette fonctionnalité");
				if(!"biblio".equals(abonne.getRole()) && !"admin".equals(abonne.getRole()))
					throw new AuthentificationException("Vous n'êtes pas habilité pour cette fonctionnalité");
				String titre = request.getParameter("titre");
				String auteur = request.getParameter("auteur");
				String genre = request.getParameter("genre");
				String annee = request.getParameter("annee");
				String editeur = request.getParameter("editeur");
				String description = request.getParameter("description");
				String isbn = request.getParameter("isbn");
				String document = request.getParameter("document");
				String image = request.getParameter("image");
				
				Livre livre = new Livre();
				livre.setTitre(titre);
				livre.setAuteur(auteur);
				Genre newGenre = new Genre();
				newGenre.setCode(genre);
				livre.setGenre(newGenre);
				livre.setAnnee(Integer.valueOf(annee));
				livre.setEditeur(editeur);
				livre.setDescription(description);
				livre.setIsbn(isbn);
				
				livreService.creerLivre(livre, document, image);
				
				if(livre.getImage().length() > 0){
					Blob blob = livre.getImage();
		            byte data[] = blob.getBytes(1, (int) blob.length());
		            // encoding the byte image string
		            BASE64Encoder base64Encoder = new BASE64Encoder();
		            StringBuilder imageString = new StringBuilder();
		            imageString.append("data:image/png;base64,");
		            imageString.append(base64Encoder.encode(data));
		            image = imageString.toString();
		            
					request.setAttribute("image", image);
				}
				
				request.setAttribute("livre", livre);
				maVue = "/book.jsp";
				
			}
			else if (action.equals("modifierLivre")) {
				if(abonne == null)
					throw new AuthentificationException("Vous devez etre connecté pour acceder à cette fonctionnalité");
				if(!"biblio".equals(abonne.getRole()) && !"admin".equals(abonne.getRole()))
					throw new AuthentificationException("Vous n'êtes pas habilité pour cette fonctionnalité");
				String livreId = request.getParameter("livreId");
				Livre livre = livreService.recupererLivreParId(livreId);
				request.setAttribute("livre", livre);
				maVue = "/modifyBook.jsp";
			}
			else if (action.equals("validerModifierLivre")) {
				if(abonne == null)
					throw new AuthentificationException("Vous devez etre connecté pour acceder à cette fonctionnalité");
				if(!"biblio".equals(abonne.getRole()) && !"admin".equals(abonne.getRole()))
					throw new AuthentificationException("Vous n'êtes pas habilité pour cette fonctionnalité");
				String idLivre = request.getParameter("idLivre");
				String titre = request.getParameter("titre");
				String auteur = request.getParameter("auteur");
				String genre = request.getParameter("genre");
				String annee = request.getParameter("annee");
				String editeur = request.getParameter("editeur");
				String description = request.getParameter("description");
				String isbn = request.getParameter("isbn");
				String document = request.getParameter("document");
				String image = request.getParameter("image");
				
				Livre livre = livreService.recupererLivreParId(idLivre);
				livre.setTitre(titre);
				livre.setAuteur(auteur);
				Genre newGenre = new Genre();
				newGenre.setCode(genre);
				livre.setGenre(newGenre);
				livre.setAnnee(Integer.valueOf(annee));
				livre.setEditeur(auteur);
				livre.setDescription(description);
				livre.setIsbn(isbn);
				livre.setEditeur(editeur);
				
				livreService.modifierLivre(livre, document, image);
				
				if(livre.getImage().length() > 0){
					Blob blob = livre.getImage();
		            byte data[] = blob.getBytes(1, (int) blob.length());
		            // encoding the byte image string
		            BASE64Encoder base64Encoder = new BASE64Encoder();
		            StringBuilder imageString = new StringBuilder();
		            imageString.append("data:image/png;base64,");
		            imageString.append(base64Encoder.encode(data));
		            image = imageString.toString();
		            
					request.setAttribute("image", image);
				}
				
				request.setAttribute("livre", livre);
				maVue = "/book.jsp";
			}else if (action.equals("supprimerLivre")) {
				if(abonne == null)
					throw new AuthentificationException("Vous devez etre connecté pour acceder à cette fonctionnalité");
				if(!"biblio".equals(abonne.getRole()) && !"admin".equals(abonne.getRole()))
					throw new AuthentificationException("Vous n'êtes pas habilité pour cette fonctionnalité");
				String livreId = request.getParameter("livreId");
				livreService.supprimerLivre(livreId);
				maVue = "/confirm.jsp?message=Le livre a été supprimé";
			}
		}catch (CheckException e) {
			getServletContext().getRequestDispatcher("/error.jsp?exception="+e.getMessage()).forward(request,
					response);
		} 
		catch (Exception e) {
			maVue = "/error.jsp";
			request.setAttribute("message", e.getMessage());
		}
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(maVue);
		dispatcher.forward(request, response);
	}

}
