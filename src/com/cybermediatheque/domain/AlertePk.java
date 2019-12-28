package com.cybermediatheque.domain;

import java.io.Serializable;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

public class AlertePk implements Serializable{
	
	public AlertePk(){
		
	}
	
	public AlertePk(Document document, Abonne abonne){
		this.document=document;
		this.abonne=abonne;
	}
	private static final long serialVersionUID = 1L;

	@OneToOne
	@JoinColumn(name = "id_document")
	private Document document;
	
	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
		
	}
	
	@ManyToOne
	@JoinColumn(name = "id_abonne")
	private Abonne abonne;

	public Abonne getAbonne() {
		return abonne;
	}

	public void setAbonne(Abonne abonne) {
		this.abonne = abonne;
	}
}
