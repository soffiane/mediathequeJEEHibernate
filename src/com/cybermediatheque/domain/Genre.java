package com.cybermediatheque.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Entité qui represente le genre d'un livre
 * 
 * @author sylar
 * @version 1.0
 */
@Entity
public class Genre {

	@Id
	String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
