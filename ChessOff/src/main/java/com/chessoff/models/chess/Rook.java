package com.chessoff.models.chess;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.chessoff.exceptions.ChessException;

public final class Rook extends Piece {

	private final static int MAX_PIECES = 2;

	private Rook(ChessBoard board, Color color, byte pieceIndex) {
		super(board, color, pieceIndex);
	}

	private static Set<Long> chessBoardIds = new HashSet<>();

	public static void placeAll(ChessBoard board) {
		if (chessBoardIds.contains(board.getBoardId())) {
			throw new ChessException("Cannot set pieces of Rook again in the same board");
		}

		for (byte i = 0; i < MAX_PIECES; i++) {
			Rook whitePiece = new Rook(board, Color.WHITE, i);
			whitePiece.place(board);
		}
		for (byte i = 0; i < MAX_PIECES; i++) {
			Rook blackPiece = new Rook(board, Color.BLACK, i);
			blackPiece.place(board);
		}
	}

	@Override
	protected int maxPieces() {
		return 2;
	}

	@Override
	protected PiecePosition getInitialPosition() {
		canPlace();
		int count = this.count();
		int y = count * 7;

		if (this.getColor() == Color.BLACK) {
			return new PiecePosition(0, y);
		} else {
			return new PiecePosition(7, y);
		}
	}

	@Override
	public List<PiecePosition> getNextValidPositions() {
		List<PiecePosition> validPositions = getNextValidPositionsAll4Sides(getXAxis(), getYAxis());
		return validPositions;
	}

}
