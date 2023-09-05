package com.chessoff.models.chess;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PieceMove {
	@JsonProperty(access = Access.WRITE_ONLY)
	private String uniquePlayerIdentifier;
	private PiecePosition currentPositionOfPiece;
	private PiecePosition newPositionOfPiece;
}
