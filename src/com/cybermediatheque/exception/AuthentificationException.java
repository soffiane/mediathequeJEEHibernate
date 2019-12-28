package com.cybermediatheque.exception;

/**
 * Exception lors de l'authentification
 * 
 * @author sylar
 * @version 1.0
 */
public class AuthentificationException extends Exception {

	private static final long serialVersionUID = 1L;
	private final String message;

	public AuthentificationException(String message) {
		super(message);
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}
}
