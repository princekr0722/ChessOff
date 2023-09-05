package com.chessoff.exceptions;

public class GameRoomException extends RuntimeException {
	public GameRoomException() {}
	
	public GameRoomException(String message) {
		super(message);
	}
}
