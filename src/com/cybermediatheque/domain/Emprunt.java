package com.cybermediatheque.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entité qui represente un emprunt, c'est a dire un couple abonné-livre
 * 
 * @author sylar
 * @version 1.0
 */
@Entity
public class Emprunt {

	// Privilegier ID "normaux"
	@EmbeddedId
	EmpruntPk pk;

	public EmpruntPk getPk() {
		return pk;
	}

	public void setPk(EmpruntPk pk) {
		this.pk = pk;
	}

	@Temporal(TemporalType.DATE)
	@Column
	private Date dateEmprunt;

	public Date getDateEmprunt() {
		return dateEmprunt;
	}

	public void setDateEmprunt(Date dateEmprunt) {
		this.dateEmprunt = dateEmprunt;
	}

	@Temporal(TemporalType.DATE)
	@Column
	private Date dateRetour;

	public Date getDateRetour() {
		return dateRetour;
	}

	public void setDateRetour(Date dateRetour) {
		this.dateRetour = dateRetour;
	}

	public Document getDocument() {
		return getPk().getDocument();
	}

	public void setDocument(Document document) {
		getPk().setDocument(document);
	}

	public Abonne getAbonne() {
		return getPk().getAbonne();
	}

	public void setAbonne(Abonne abonne) {
		getPk().setAbonne(abonne);
	}

}
