package com.chessoff.models.chess;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.chessoff.exceptions.ChessException;

public final class Queen extends Piece {

	private Queen(ChessBoard board, Color color) {
		super(board, color, (byte) 0);
	}

	private static Set<Long> chessBoardIds = new HashSet<>();

	protected static void placeAll(ChessBoard board) {
		if (chessBoardIds.contains(board.getBoardId())) {
			throw new ChessException("Cannot set pieces of Queen again in the same board");
		}

		Queen whiteQueen = new Queen(board, Color.WHITE);
		Queen blackQueen = new Queen(board, Color.BLACK);

		whiteQueen.place(board);
		blackQueen.place(board);
	}

	@Override
	protected PiecePosition getInitialPosition() {
		canPlace();
		if (this.getColor() == Color.BLACK) {
			return new PiecePosition(0, 3);
		} else {
			return new PiecePosition(7, 3);
		}
	}

	@Override
	protected int maxPieces() {
		return 1;
	}

	@Override
	public List<PiecePosition> getNextValidPositions() {
		int x = getXAxis(), y = getYAxis();
		List<PiecePosition> validPositions = getNextValidPositionsAll4Sides(x, y);
		validPositions.addAll(getNextValidPositionsDiagonally(x, y));
		return validPositions;
	}

}
