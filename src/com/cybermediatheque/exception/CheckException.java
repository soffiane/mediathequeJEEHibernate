package com.cybermediatheque.exception;

/**
 * Exception generique de vérification des formulaires
 * 
 * @author sylar
 * @version 1.0
 */
public class CheckException extends Exception {

	private static final long serialVersionUID = 7845873670742052903L;
	private final String message;

	public CheckException(String message) {
		super(message);
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}
}
