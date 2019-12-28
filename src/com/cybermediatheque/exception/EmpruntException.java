package com.cybermediatheque.exception;

/**
 * Exception lors de la gestion d'un emprunt
 * 
 * @author sylar
 * @version 1.0
 */
public class EmpruntException extends Exception {

	/** Serial UID */
	private static final long serialVersionUID = -8946432907258868587L;

	private final String message;

	public EmpruntException(String message) {
		super(message);
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}

}