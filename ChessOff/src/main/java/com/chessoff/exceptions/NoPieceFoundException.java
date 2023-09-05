package com.chessoff.exceptions;

public class NoPieceFoundException extends RuntimeException{
	
	public NoPieceFoundException() {
		super();
	}
	public NoPieceFoundException(String message) {
		super(message);
	}

}
