package com.cybermediatheque.exception;

/**
 * Exception lors de la gestion des genres
 * 
 * @author sylar
 * @version 1.0
 */
public class GenreException extends Exception {

	private static final long serialVersionUID = 1L;

	private final String message;

	public GenreException(String message) {
		super(message);
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}

}
