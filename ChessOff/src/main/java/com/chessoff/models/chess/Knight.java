package com.chessoff.models.chess;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.chessoff.exceptions.ChessException;

public final class Knight extends Piece {
	private final static int MAX_PIECES = 2;

	private Knight(ChessBoard board, Color color, byte pieceIndex) {
		super(board, color, pieceIndex);
	}

	private static Set<Long> chessBoardIds = new HashSet<>();

	protected static void placeAll(ChessBoard board) {
		if (chessBoardIds.contains(board.getBoardId())) {
			throw new ChessException("Cannot set pieces of Knight again in the same board");
		}

		for (byte i = 0; i < MAX_PIECES; i++) {
			Knight whitePiece = new Knight(board, Color.WHITE, i);
			whitePiece.place(board);
		}
		for (byte i = 0; i < MAX_PIECES; i++) {
			Knight blackPiece = new Knight(board, Color.BLACK, i);
			blackPiece.place(board);
		}
	}

	@Override
	protected PiecePosition getInitialPosition() {
		canPlace();
		int count = this.count();
		int y = 1;
		if (count > 0)
			y = 6;

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
		ChessBoard board = getBoard();
		int x = getXAxis(), y = getYAxis();

		List<PiecePosition> validPositions = new ArrayList<>();
		if (this.getColor() == Color.WHITE) {
			// 2.5 front right
			if ((x - 2) >= 0 && (y + 1) < 8 && (board.getPiece(x - 2, y + 1) == null
					|| board.getPiece(x - 2, y + 1).getColor() != Color.WHITE)) {
				validPositions.add(new PiecePosition(x - 2, y + 1));
			}

			// 2.5 front left
			if ((x - 2) >= 0 && (y - 1) >= 0 && (board.getPiece(x - 2, y - 1) == null
					|| board.getPiece(x - 2, y - 1).getColor() != Color.WHITE)) {
				validPositions.add(new PiecePosition(x - 2, y - 1));
			}

			// 2.5 back right
			if ((x + 2) < 8 && (y + 1) < 8 && (board.getPiece(x + 2, y + 1) == null
					|| board.getPiece(x + 2, y + 1).getColor() != Color.WHITE)) {
				validPositions.add(new PiecePosition(x + 2, y + 1));
			}

			// 2.5 back left
			if ((x + 2) < 8 && (y - 1) >= 0 && (board.getPiece(x + 2, y - 1) == null
					|| board.getPiece(x + 2, y - 1).getColor() != Color.WHITE)) {
				validPositions.add(new PiecePosition(x + 2, y - 1));
			}

			// 2.5 left up
			if ((x - 1) >= 0 && (y - 2) >= 0 && (board.getPiece(x - 1, y - 2) == null
					|| board.getPiece(x - 1, y - 2).getColor() != Color.WHITE)) {
				validPositions.add(new PiecePosition(x - 1, y - 2));
			}

			// 2.5 left down
			if ((x + 1) < 8 && (y - 2) >= 0 && (board.getPiece(x + 1, y - 2) == null
					|| board.getPiece(x + 1, y - 2).getColor() != Color.WHITE)) {
				validPositions.add(new PiecePosition(x + 1, y - 2));
			}

			// 2.5 right up
			if ((x - 1) >= 0 && (y + 2) < 8 && (board.getPiece(x - 1, y + 2) == null
					|| board.getPiece(x - 1, y + 2).getColor() != Color.WHITE)) {
				validPositions.add(new PiecePosition(x - 1, y + 2));
			}

			// 2.5 right down
			if ((x + 1) < 8 && (y + 2) < 8 && (board.getPiece(x + 1, y + 2) == null
					|| board.getPiece(x + 1, y + 2).getColor() != Color.WHITE)) {
				validPositions.add(new PiecePosition(x + 1, y + 2));
			}
		} else {
			// 2.5 front right
			if ((x + 2) < 8 && (y - 1) >= 0 && (board.getPiece(x + 2, y - 1) == null
					|| board.getPiece(x + 2, y - 1).getColor() != Color.BLACK)) {
				validPositions.add(new PiecePosition(x + 2, y - 1));
			}

			// 2.5 front left
			if ((x + 2) < 8 && (y + 1) < 8 && (board.getPiece(x + 2, y + 1) == null
					|| board.getPiece(x + 2, y + 1).getColor() != Color.BLACK)) {
				validPositions.add(new PiecePosition(x + 2, y + 1));
			}

			// 2.5 back right
			if ((x - 2) >= 0 && (y - 1) > 0 && (board.getPiece(x - 2, y - 1) == null
					|| board.getPiece(x - 2, y - 1).getColor() != Color.BLACK)) {
				validPositions.add(new PiecePosition(x - 2, y - 1));
			}

			// 2.5 back left
			if ((x - 2) >= 0 && (y + 1) < 8 && (board.getPiece(x - 2, y + 1) == null
					|| board.getPiece(x - 2, y + 1).getColor() != Color.BLACK)) {
				validPositions.add(new PiecePosition(x - 2, y + 1));
			}

			// 2.5 left up
			if ((x + 1) < 8 && (y + 2) < 8 && (board.getPiece(x + 1, y + 2) == null
					|| board.getPiece(x + 1, y + 2).getColor() != Color.BLACK)) {
				validPositions.add(new PiecePosition(x + 1, y + 2));
			}

			// 2.5 left down
			if ((x - 1) >= 0 && (y + 2) < 8 && (board.getPiece(x - 1, y + 2) == null
					|| board.getPiece(x - 1, y + 2).getColor() != Color.BLACK)) {
				validPositions.add(new PiecePosition(x - 1, y + 2));
			}

			// 2.5 right up
			if ((x + 1) < 8 && (y - 2) >= 0 && (board.getPiece(x + 1, y - 2) == null
					|| board.getPiece(x + 1, y - 2).getColor() != Color.BLACK)) {
				validPositions.add(new PiecePosition(x + 1, y - 2));
			}

			// 2.5 right down
			if ((x - 1) >= 0 && (y - 2) >= 0 && (board.getPiece(x - 1, y - 2) == null
					|| board.getPiece(x - 1, y - 2).getColor() != Color.BLACK)) {
				validPositions.add(new PiecePosition(x - 1, y - 2));
			}
		}
		return validPositions;
	}
}
