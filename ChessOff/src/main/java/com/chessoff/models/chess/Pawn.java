package com.chessoff.models.chess;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.chessoff.exceptions.ChessException;

public class Pawn extends Piece {

	private final static int MAX_PIECES = 8;

	private Pawn(ChessBoard board, Color color, byte pieceIndex) {
		super(board, color, pieceIndex);
	}

	private static Set<Long> chessBoardIds = new HashSet<>();

	protected static void placeAll(ChessBoard board) {
		if (chessBoardIds.contains(board.getBoardId())) {
			throw new ChessException("Cannot set pieces of Pawn again in the same board");
		}

		for (byte i = 0; i < MAX_PIECES; i++) {
			Pawn whitePiece = new Pawn(board, Color.WHITE, i);
			whitePiece.place(board);
		}
		for (byte i = 0; i < MAX_PIECES; i++) {
			Pawn blackPiece = new Pawn(board, Color.BLACK, i);
			blackPiece.place(board);
		}
	}

	@Override
	protected PiecePosition getInitialPosition() {
		canPlace();
		int count = this.count();
		int y = count;

		if (this.getColor() == Color.BLACK) {
			return new PiecePosition(1, y);
		} else {
			return new PiecePosition(6, y);
		}
	}

	@Override
	protected int maxPieces() {
		return 8;
	}

	@Override
	public List<PiecePosition> getNextValidPositions() {
		ChessBoard board = getBoard();
		int x = getXAxis(), y = getYAxis();

		List<PiecePosition> validPositions = new ArrayList<>();

		if (this.getColor() == Color.WHITE) {
			// one step straight
			if (x - 1 >= 0 && board.getPiece(x - 1, y) == null) {
				validPositions.add(new PiecePosition(x - 1, y));
			}

			// two step straight
			if (x == 6 && x - 2 >= 0 && board.getPiece(x - 2, y) == null) {
				validPositions.add(new PiecePosition(x - 2, y));
			}

			// left attack
			if (x - 1 >= 0 && y - 1 >= 0 && board.getPiece(x - 1, y - 1) != null
					&& board.getPiece(x - 1, y - 1).getColor() == Color.BLACK) {
				validPositions.add(new PiecePosition(x - 1, y - 1));
			}

			// right attack
			if (x - 1 >= 0 && y + 1 < 8 && board.getPiece(x - 1, y + 1) != null
					&& board.getPiece(x - 1, y + 1).getColor() == Color.BLACK) {
				validPositions.add(new PiecePosition(x - 1, y + 1));
			}
		} else {
			// one step straight
			if (x + 1 < 8 && board.getPiece(x + 1, y) == null) {
				validPositions.add(new PiecePosition(x + 1, y));
			}

			// two step straight
			if (x == 1 && x + 2 < 8 && board.getPiece(x + 2, y) == null) {
				validPositions.add(new PiecePosition(x + 2, y));
			}

			// left attack
			if (x + 1 < 8 && y + 1 < 8 && board.getPiece(x + 1, y + 1) != null
					&& board.getPiece(x + 1, y + 1).getColor() == Color.WHITE) {
				validPositions.add(new PiecePosition(x + 1, y + 1));
			}

			// right attack
			if (x + 1 < 8 && y - 1 >= 0 && board.getPiece(x + 1, y - 1) != null
					&& board.getPiece(x + 1, y - 1).getColor() == Color.WHITE) {
				validPositions.add(new PiecePosition(x + 1, y - 1));
			}
		}

		return validPositions;
	}

}
