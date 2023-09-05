package com.chessoff.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chessoff.models.dto.PlayerAndRoomInfo;
import com.chessoff.service.ChessService;
import com.chessoff.service.GameRoomService;

@RestController
@RequestMapping("/chess")
public class ChessController {

	@Autowired
	private ChessService chessService;

	@Autowired
	private GameRoomService gameRoomService;

	@GetMapping("/find-game-room")
	public ResponseEntity<PlayerAndRoomInfo> findGameRoom() {
		PlayerAndRoomInfo newGameInfo = gameRoomService.assignRoomToPlayer();
		return new ResponseEntity<>(newGameInfo, HttpStatus.OK);
	}

}
