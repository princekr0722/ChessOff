package com.chessoff.models.dto;

import java.util.UUID;

import com.chessoff.exceptions.GameRoomException;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class GameRoom {
	private String roomNo;
	// WHITE
	private String playerOneIdentifier;
	//BLACK
	private String playerTwoIdentifier;
	private boolean canStart;
	private int totalSubscription;

	public GameRoom(String playerOneIdentifier) {
		if (playerOneIdentifier == null)
			throw new IllegalArgumentException("Players Identifier cannot be null");

		this.roomNo = generateNewRoomId();
		this.playerOneIdentifier = playerOneIdentifier;
		this.canStart = false;
	}

	private String generateNewRoomId() {
		String roomId = "ROOMID" + UUID.randomUUID().toString();
		return roomId;
	}

	/**
	 * @throws GameRoomException when the second player is already set
	 */
	public void setPlayerTwoIdentifier(String playerTwoIdentifier) {
		if (playerOneIdentifier == null) {
			this.playerOneIdentifier = playerTwoIdentifier;

			if(playerTwoIdentifier != null) {
				canStart = true;
			}
		}
		if (this.playerTwoIdentifier == null) {
			this.playerTwoIdentifier = playerTwoIdentifier;

			if(playerOneIdentifier != null) {
				canStart = true;
			}
		} else
			throw new GameRoomException("Game room is full");
	}
	
	public int getTotalSubscription() {
		return totalSubscription;
	}
	
	public void incrementTotalSubscription() {
		if(totalSubscription<2) {
			totalSubscription++;
		} else {
			throw new GameRoomException("Room cannot have more than 2 subscribers.");
		}
	}

	@Override
	public String toString() {
		return "GameRoom [roomNo=" + roomNo + ", playerOneIdentifier=" + playerOneIdentifier + ", playerTwoIdentifier="
				+ playerTwoIdentifier + ", canStart=" + canStart + "]";
	}
}
