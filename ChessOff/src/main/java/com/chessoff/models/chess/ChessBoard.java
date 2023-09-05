package com.chessoff.models.chess;

import com.chessoff.exceptions.ChessException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ChessBoard {
	@JsonIgnore
	private static long id = 0;

	private long boardId;
	private static int dimensions = 8;
	private Piece[][] board = new Piece[dimensions][dimensions];

	private List<Piece> capturedWhitePieces = new ArrayList<>();
	private List<Piece> capturedBlackPieces = new ArrayList<>();

	private Color turn;

	private ChessBoard(Color firstTurn) {
		this.boardId = ++ChessBoard.id;
		this.turn = firstTurn;

		King.placeAll(this);
		Queen.placeAll(this);
		Bishop.placeAll(this);
		Knight.placeAll(this);
		Rook.placeAll(this);
		Pawn.placeAll(this);
	}

	public static ChessBoard get(Color firstTurn) {
		return new ChessBoard(firstTurn);
	}

	public void addPiece(int x, int y, Piece piece) {
		board[x][y] = piece;

		Integer prevCount = pieceTypes.get(piece.getColor() + "" + piece.getClass().getSimpleName());
		if (prevCount == null)
			prevCount = 0;

		pieceTypes.put(piece.getColor() + "" + piece.getClass().getSimpleName(), prevCount + 1);
	}

	public Piece getPiece(int x, int y) throws ArrayIndexOutOfBoundsException {
		return board[x][y];
	}

	public long getBoardId() {
		return boardId;
	}

	private Map<String, Integer> pieceTypes = new HashMap<>();

	{
		pieceTypes.put("BLACKKing", 0);
		pieceTypes.put("WHITEKing", 0);
		pieceTypes.put("BLACKQueen", 0);
		pieceTypes.put("WHITEQueen", 0);
		pieceTypes.put("BLACKBishop", 0);
		pieceTypes.put("WHITEBishop", 0);
		pieceTypes.put("BLACKKnight", 0);
		pieceTypes.put("WHITEKnight", 0);
		pieceTypes.put("BLACKRook", 0);
		pieceTypes.put("WHITERook", 0);
		pieceTypes.put("BLACKPawn", 0);
		pieceTypes.put("WHITEPawn", 0);
	}

	/**
	 * @param pieceType must be in this format: COLORPiece
	 * @return Number of times that piece of mentioned color is present.</br>
	 *         if parameter is invalid String then it will return null.
	 */
	public Integer getCountOf(String pieceType) {
		return pieceTypes.get(pieceType);
	}

	public int getDimensions() {
		return dimensions;
	}

	public void movePieceTo(int currX, int currY, int newX, int newY) throws ArrayIndexOutOfBoundsException {
		Piece piece = board[currX][currY];
		if (turn != piece.getColor()) {
			throw new ChessException("It's Cheating! Current turn is of " + turn.toString().toLowerCase() + " but "
					+ piece.getColor().toString().toLowerCase() + " is trying to move.");
		}

		piece.moveTo(newX, newY);

		if (board[newX][newY] != null) {
			// attack move
			Piece attackedPiece = board[newX][newY];
			if (attackedPiece.getColor() == Color.WHITE) {
				capturedWhitePieces.add(attackedPiece);
			} else {
				capturedBlackPieces.add(attackedPiece);
			}
		}

		board[currX][currY] = null;
		board[newX][newY] = piece;

		if (turn == Color.WHITE)
			turn = Color.BLACK;
		else
			turn = Color.WHITE;
	}

	public Color getTurn() {
		return this.turn;
	}

	public List<Piece> getCapturedWhitePieces() {
		return capturedWhitePieces;
	}

	public List<Piece> getCapturedBlackPieces() {
		return capturedBlackPieces;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\nChess Board:" + boardId + "\t Turn: " + turn + "\n");
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board[i][j] != null) {
					sb.append(board[i][j].toString());
				} else {
					sb.append("|        \t|");
				}
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}
