package com.cybermediatheque.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;

/**
 * Superclasse Document permettant de pouvoir ajouter par la suite d'autres type
 * de documents dans la cybermédiathèque
 * 
 * @author sylar
 * @version 1.0
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Document {

	public Document() {
	}

	@Id
	// @GeneratedValue
	@Column(name = "id_document", nullable = false, unique = true)
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column
	private String titre;

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	// Chemin vers document via la clé document de la table Emprunt
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "pk.document")
	private Emprunt emprunt = new Emprunt();

	public Emprunt getEmprunts() {
		return this.emprunt;
	}

	public void setEmprunts(Emprunt emprunts) {
		this.emprunt = emprunts;
	}

}
