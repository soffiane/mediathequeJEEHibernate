package com.cybermediatheque.service;

import java.util.List;

import org.hibernate.Query;

import com.cybermediatheque.domain.Genre;
import com.cybermediatheque.exception.GenreException;

/**
 * Ce composant permet de gerer les services liés aux genres de livre
 * 
 * @author sylar
 * @version 1.0
 */
public class GenreService extends HibernateService {

	/**
	 * Cette methode permet d'ajouter un genre de livre
	 * 
	 * @param genre le genre a créer
	 * @throws GenreException si le genre existe deja
	 */
	public void ajouterGenreLivre(String genre) throws GenreException {
		getSession().beginTransaction();
		Query q = getSession().getNamedQuery("HQL_ADD_GENRE_LIVRE");
		q.setString("code", genre);
		Genre existant = (Genre) q.uniqueResult();
		if (existant == null) {
			Genre nouveauGenre = new Genre();
			nouveauGenre.setCode(genre);
			getSession().save(nouveauGenre);
		} else {
			throw new GenreException("genre deja existant");
		}
		getSession().getTransaction().commit();
	}

	/**
	 * Cette fonction permet de lister les genres disponibles
	 * 
	 * @return la liste des genres disponibles pour un livre
	 */
	public List<Genre> listerGenre() {
		Query q = getSession().getNamedQuery("HQL_GET_ALL_GENRE");
		List<Genre> genres = q.list();
		return genres;
	}

}
