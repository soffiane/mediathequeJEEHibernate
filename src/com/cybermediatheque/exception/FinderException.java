package com.cybermediatheque.exception;

/**
 * Exception lors de la recherche d'une entité
 * 
 * @author sylar
 * @version 1.0
 */
public class FinderException extends Exception {

	/** Serial UID */
	private static final long serialVersionUID = -8946432907258868587L;

	private final String message;

	public FinderException(String message) {
		super(message);
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}

}
