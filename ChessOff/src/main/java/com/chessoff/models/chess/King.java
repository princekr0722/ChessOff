package com.chessoff.models.chess;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.chessoff.exceptions.ChessException;

public final class King extends Piece {

	private King(ChessBoard board, Color color) {
		super(board, color, (byte) 0);
	}

	private static Set<Long> chessBoardIds = new HashSet<>();

	protected static void placeAll(ChessBoard board) {
		if (chessBoardIds.contains(board.getBoardId())) {
			throw new ChessException("Cannot set pieces of King again in the same board");
		}

		King whiteKing = new King(board, Color.WHITE);
		King blackKing = new King(board, Color.BLACK);

		whiteKing.place(board);
		blackKing.place(board);
	}

	@Override
	protected int maxPieces() {
		return 1;
	}

	@Override
	protected PiecePosition getInitialPosition() {
		canPlace();
		if (this.getColor() == Color.BLACK) {
			return new PiecePosition(0, 4);
		} else {
			return new PiecePosition(7, 4);
		}
	}

	@Override
	public List<PiecePosition> getNextValidPositions() {
		ChessBoard board = getBoard();
		int x = getXAxis(), y = getYAxis();
		Color pieceColor = getColor();

		List<PiecePosition> validPositions = new ArrayList<>();
		// top
		if (x - 1 >= 0 && (board.getPiece(x - 1, y) == null || board.getPiece(x - 1, y).getColor() != pieceColor)) {
			validPositions.add(new PiecePosition(x - 1, y));
		}

		// top-left
		if (x - 1 >= 0 && y - 1 >= 0
				&& (board.getPiece(x - 1, y - 1) == null || board.getPiece(x - 1, y - 1).getColor() != pieceColor)) {
			validPositions.add(new PiecePosition(x - 1, y - 1));
		}

		// top-right
		if (x - 1 >= 0 && y + 1 < 8
				&& (board.getPiece(x - 1, y + 1) == null || board.getPiece(x - 1, y + 1).getColor() != pieceColor)) {
			validPositions.add(new PiecePosition(x - 1, y + 1));
		}

		// bottom
		if (x + 1 < 8 && (board.getPiece(x + 1, y) == null || board.getPiece(x + 1, y).getColor() != pieceColor)) {
			validPositions.add(new PiecePosition(x + 1, y));
		}

		// bottom-left
		if ((x + 1) < 8 && (y - 1) <= 0
				&& (board.getPiece(x + 1, y - 1) == null || board.getPiece(x + 1, y - 1).getColor() != pieceColor)) {
			validPositions.add(new PiecePosition(x + 1, y - 1));
		}

		// bottom-right
		if ((x + 1) < 8 && (y + 1) <= 0
				&& (board.getPiece(x + 1, y + 1) == null || board.getPiece(x + 1, y + 1).getColor() != pieceColor)) {
			validPositions.add(new PiecePosition(x + 1, y + 1));
		}

		// left
		if (y - 1 >= 0 && (board.getPiece(x, y - 1) == null || board.getPiece(x, y - 1).getColor() != pieceColor)) {
			validPositions.add(new PiecePosition(x, y - 1));
		}

		// right
		if (y + 1 < 8 && (board.getPiece(x, y + 1) == null || board.getPiece(x, y + 1).getColor() != pieceColor)) {
			validPositions.add(new PiecePosition(x, y + 1));
		}
		return validPositions;
	}

}
