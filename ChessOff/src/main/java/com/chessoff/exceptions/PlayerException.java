package com.chessoff.exceptions;

public class PlayerException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PlayerException() {}
	
	public PlayerException(String message) {
		super(message);
	}
}
