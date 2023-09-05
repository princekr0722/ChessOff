package com.chessoff.service.impl;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.chessoff.exceptions.GameRoomException;
import com.chessoff.models.chess.Color;
import com.chessoff.models.dto.GameRoom;
import com.chessoff.models.dto.PlayerAndRoomInfo;
import com.chessoff.service.GameRoomService;

@Service
public class GameRoomServiceImpl implements GameRoomService {

	private Map<String, GameRoom> openGameRooms = new HashMap<>();
	private LinkedHashSet<String> waitingGameRoomsNo = new LinkedHashSet<>();

	@Override
	public GameRoom findGameRoom(String roomNo) {
		GameRoom room = openGameRooms.get(roomNo);
		if (room == null)
			throw new GameRoomException("No game room found with the given Id.");
		return room;
	}

	@Override
	public PlayerAndRoomInfo assignRoomToPlayer() {
		String playerIdentifier = "Player" + UUID.randomUUID().toString();

		GameRoom assignableGameRoom = getAvailableRoom();
		PlayerAndRoomInfo playerAndRoomInfo;
		if (assignableGameRoom != null) {
			assignableGameRoom.setPlayerTwoIdentifier(playerIdentifier);
			
			String roomNo = assignableGameRoom.getRoomNo();
			playerAndRoomInfo = new PlayerAndRoomInfo(Color.BLACK, playerIdentifier, roomNo);
		} else {
			assignableGameRoom = generateNewGameRoom(playerIdentifier);
			
			String roomNo = assignableGameRoom.getRoomNo();
			playerAndRoomInfo = new PlayerAndRoomInfo(Color.WHITE, playerIdentifier, roomNo);
		}


		return playerAndRoomInfo;
	}

	private GameRoom getAvailableRoom() {
		String availableRoomNo = null;
		for (String waitingGameRoomNo : waitingGameRoomsNo) {
			if (true) {
				availableRoomNo = waitingGameRoomNo;
				break;
			}
		}

		GameRoom availableRoom = null;
		if (availableRoomNo != null) {
			availableRoom = findGameRoom(availableRoomNo);
			waitingGameRoomsNo.remove(availableRoomNo);
		}
		return availableRoom;
	}

	private GameRoom generateNewGameRoom(String playerOneIdentifier) {
		GameRoom room = new GameRoom(playerOneIdentifier);
		String roomNo = room.getRoomNo();

		openGameRooms.put(roomNo, room);
		waitingGameRoomsNo.add(roomNo);
		return room;
	}

	@Override
	public void closeGameRoom(GameRoom room) {
		String roomId = room.getRoomNo();
		closeGameRoom(roomId);
	}

	@Override
	public void closeGameRoom(String roomNo) {
		findGameRoom(roomNo);
		openGameRooms.remove(roomNo);
		waitingGameRoomsNo.remove(roomNo);
	}

}
