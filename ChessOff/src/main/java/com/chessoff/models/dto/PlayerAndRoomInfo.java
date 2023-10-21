package com.chessoff.models.dto;

import com.chessoff.models.chess.Color;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerAndRoomInfo {
	private Color playerColor;
	private String playerIdentifier;
	private String roomNo;
}
