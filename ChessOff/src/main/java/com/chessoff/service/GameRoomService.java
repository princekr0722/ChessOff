package com.chessoff.service;

import com.chessoff.models.dto.GameRoom;
import com.chessoff.models.dto.PlayerAndRoomInfo;

public interface GameRoomService {

	GameRoom findGameRoom(String roomNo);

	PlayerAndRoomInfo assignRoomToPlayer();

	void closeGameRoom(GameRoom room);

	void closeGameRoom(String roomNo);

}
