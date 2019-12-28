package com.cybermediatheque.control;

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * Servlet de chargement des images statiques
 * 
 * @author soffiane boudissa
 * @version 1.0
 */
@WebServlet("/ImageServlet")
public class ImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Logger logger = Logger.getLogger(ImageServlet.class.getName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ImageServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {

			DataOutput output = new DataOutputStream(response.getOutputStream());
			response.setContentType("image/jpeg");

			String nomImage = request.getParameter("imageName");

			File file = null;

			FileInputStream in = null;

			String filePath = "images/" + nomImage;

			file = new File(filePath);

			/*
			 * Dans le cas ou l'image n'est pas présente dans le répertoire On affiche une
			 * image par defaut 'Image Introuuvable'
			 */
			if (!file.exists()) {
				String path = request.getSession().getServletContext().getRealPath("") + "/images/" + nomImage;
				file = new File(path);
			}

			in = new FileInputStream(file);

			response.setContentLength((int) file.length());

			byte buffer[] = new byte[4096];
			int nbLecture;

			while ((nbLecture = in.read(buffer)) != -1) {
				output.write(buffer, 0, nbLecture);
			}

			in.close();

		} catch (IOException e) {
			logger.error("erreur lors du renvoi du fichier jpg", e);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
