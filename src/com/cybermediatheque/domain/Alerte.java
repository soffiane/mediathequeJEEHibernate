package com.cybermediatheque.domain;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

/**
 * Entité qui represente une alerte qui est un couple livre-mail
 * @author sylar
 * @version 1.0
 */
@Entity
public class Alerte {
	@EmbeddedId
	AlertePk pk;
	
	public AlertePk getPk() {
		return pk;
	}

	public void setPk(AlertePk pk) {
		this.pk = pk;
	}
	
	@Column
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public Document getDocument() {
		return getPk().getDocument();
	}

	public void setDocument(Document document) {
		getPk().setDocument(document);
	}

	public Abonne getAbonné() {
		return getPk().getAbonne();
	}
	
	public void setAbonné(Abonne abonne){
		getPk().setAbonne(abonne);
	}
	
}
