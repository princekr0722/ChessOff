package com.chessoff.exceptions;

public class InvalidMoveExpection extends RuntimeException {
	public InvalidMoveExpection() {
		super("Invalid move");
	}
	
	public InvalidMoveExpection(String message) {
		super(message);
	}
}
