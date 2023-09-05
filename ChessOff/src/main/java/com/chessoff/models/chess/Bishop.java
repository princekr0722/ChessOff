package com.chessoff.models.chess;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.chessoff.exceptions.ChessException;

public class Bishop extends Piece {
	private final static int MAX_PIECES = 2;

	private Bishop(ChessBoard board, Color color, byte pieceIndex) {
		super(board, color, pieceIndex);
	}

	private static Set<Long> chessBoardIds = new HashSet<>();

	protected static void placeAll(ChessBoard board) {
		if (chessBoardIds.contains(board.getBoardId())) {
			throw new ChessException("Cannot set pieces of Bishop again in the same board");
		}

		for (byte i = 0; i < MAX_PIECES; i++) {
			Bishop whitePiece = new Bishop(board, Color.WHITE, i);
			whitePiece.place(board);
		}
		for (byte i = 0; i < MAX_PIECES; i++) {
			Bishop blackPiece = new Bishop(board, Color.BLACK, i);
			blackPiece.place(board);
		}
	}

	@Override
	protected PiecePosition getInitialPosition() {
		canPlace();
		int count = this.count();

		int y = 2;
		if (count > 0)
			y = 5;

		if (this.getColor() == Color.BLACK) {
			return new PiecePosition(0, y);
		} else {
			return new PiecePosition(7, y);
		}
	}

	@Override
	protected int maxPieces() {
		return 2;
	}

	@Override
	public List<PiecePosition> getNextValidPositions() {
		int x = getXAxis(), y = getYAxis();
		List<PiecePosition> validPositions = getNextValidPositionsDiagonally(x, y);
		return validPositions;
	}

}
