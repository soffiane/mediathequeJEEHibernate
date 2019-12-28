package com.cybermediatheque.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entité qui represente l'abonné
 * @author sylar
 * @version 1.0
 */
@Entity
public class Abonne {

	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_abonne", nullable = false, unique = true)
	private String id;

	@Column
	private String nom;

	@Column
	private String prenom;

	@Temporal( TemporalType.DATE)
	@Column
	private Date dateNaissance;
	
	@Column
	private String email;
	
	@Column
	private String password;
	
	@Column
	private boolean isBloque;
	
	@Column
	private String role = "user";
	
	/**
	 * @return le mail de l'abonné
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email le mail de l'abonné
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public boolean getIsBloque() {
		return isBloque;
	}

	public void setBloque(boolean isBloque) {
		this.isBloque = isBloque;
	}

	public Date getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(Date annee_naissance) {
		this.dateNaissance = annee_naissance;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	// Chemin vers abonné via la clé abonné de la table Emprunt
	@OneToMany(fetch=FetchType.LAZY,mappedBy = "pk.abonne")
	private Set<Emprunt> emprunts = new HashSet<Emprunt>();

	public Set<Emprunt> getEmprunts() {
		return this.emprunts;
	}

	public void setEmprunts(Set<Emprunt> emprunts) {
		this.emprunts = emprunts;
	}
	

}
