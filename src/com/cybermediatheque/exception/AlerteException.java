package com.cybermediatheque.exception;

/**
 * Exception sur les alertes
 * 
 * @author sylar
 * @version 1.0
 */
public class AlerteException extends Exception {

	private static final long serialVersionUID = 1L;
	private final String message;

	public AlerteException(String message) {
		super(message);
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}
}
