package com.cybermediatheque.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Entité de gestion des id des livres
 * 
 * @author sylar
 * @version 1.0
 */
@Entity
public class Counter implements Serializable {
	// ======================================
	// = Attributes =
	// ======================================
	@Id
	@Column(name = "name", nullable = false, length = 50)
	private String id;
	@Column(name = "value")
	private int nextId;

	// ======================================
	// = Constructors =
	// ======================================
	public Counter() {
	}

	public Counter(final String id, final int nextId) {
		setId(id);
		setNextId(nextId);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getNextId() {
		return nextId;
	}

	public void setNextId(int nextId) {
		this.nextId = nextId;
	}
}
