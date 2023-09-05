package com.chessoff.models.chess;

import java.util.ArrayList;
import java.util.List;

import com.chessoff.exceptions.ChessException;
import com.chessoff.exceptions.InvalidMoveExpection;
import com.chessoff.exceptions.NoPieceFoundException;
import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class Piece {
	@JsonIgnore
	private ChessBoard board;
	private Color color;
	private int xAxis;
	private int yAxis;
	private byte pieceNumber;

	protected Piece(ChessBoard board, Color color, byte pieceNumber) {
		this.board = board;
		this.color = color;
		this.pieceNumber = pieceNumber;
	}

	public Color getColor() {
		return this.color;
	}

	protected byte getPieceNumber() {
		return this.pieceNumber;
	}

	protected ChessBoard getBoard() {
		return this.board;
	}

	protected PiecePosition getPosition() {
		return new PiecePosition(xAxis, yAxis);
	}

	protected int getXAxis() {
		return xAxis;
	}

	protected int getYAxis() {
		return yAxis;
	}

//	protected abstract void autoMove();

	protected final Piece get() {
		return this;
	}

	protected final int count() {
		Integer count = this.getBoard().getCountOf(getColor() + "" + getClass().getSimpleName());
		return count;
	}

	protected abstract PiecePosition getInitialPosition();

	protected abstract int maxPieces();

	public abstract List<PiecePosition> getNextValidPositions();

	public boolean isValidMove(int x, int y) {
		List<PiecePosition> validPlaces = getNextValidPositions();
		return validPlaces.contains(new PiecePosition(x, y));
	}

	protected final void moveTo(int x, int y) {
		boolean isValid = isValidMove(x, y);

		if (isValid) {
			this.xAxis = x;
			this.yAxis = y;

		} else {
			throw new InvalidMoveExpection();
		}
	}

	@Override
	public String toString() {
		return "[" + this.getColor().toString().substring(0, 1) + " " + this.getClass().getSimpleName()
				+ this.pieceNumber + "]\t";
	}

	protected void place(ChessBoard board) {
		PiecePosition initialPiecePosition = getInitialPosition();

		this.xAxis = initialPiecePosition.getX();
		this.yAxis = initialPiecePosition.getY();

		board.addPiece(xAxis, yAxis, this);
	}

	protected void canPlace() {
		int count = this.count() - 1;
		int maxPieces = this.maxPieces();
		boolean canPlace = count < maxPieces;
		if (!canPlace) {
			throw new ChessException(
					"Chess board cannot have more than " + this.maxPieces() + " " + this.getClass().toString());
		}
	}

	protected List<PiecePosition> getNextValidPositionsAll4Sides(int x, int y) {

		ChessBoard board = getBoard();
		if (board.getPiece(x, y) == null) {
			throw new NoPieceFoundException();
		}

		List<PiecePosition> validPositions = new ArrayList<>();

		int row = x - 1;
		Color pieceColor = this.getColor();

		while (row >= 0) {
			Piece piece = board.getPiece(row, y);
			if (piece == null) {
				validPositions.add(new PiecePosition(row, y));
			} else if (piece.getColor() != pieceColor) {
				validPositions.add(new PiecePosition(row, y));
				break;
			} else {
				break;
			}
			row--;
		}

		row = x + 1;
		while (row < 8) {
			Piece piece = board.getPiece(row, y);
			if (piece == null) {
				validPositions.add(new PiecePosition(row, y));
			} else if (piece.getColor() != pieceColor) {
				validPositions.add(new PiecePosition(row, y));
				break;
			} else {
				break;
			}
			row++;
		}

		int col = y - 1;
		while (col >= 0) {
			Piece piece = board.getPiece(x, col);
			if (piece == null) {
				validPositions.add(new PiecePosition(x, col));
			} else if (piece.getColor() != pieceColor) {
				validPositions.add(new PiecePosition(x, col));
				break;
			} else {
				break;
			}
			col--;
		}

		col = y + 1;

		while (col < 8) {
			Piece piece = board.getPiece(x, col);
			if (piece == null) {
				validPositions.add(new PiecePosition(x, col));
			} else if (piece.getColor() != pieceColor) {
				validPositions.add(new PiecePosition(x, col));
				break;
			} else {
				break;
			}
			col++;
		}

		return validPositions;
	}

	protected List<PiecePosition> getNextValidPositionsDiagonally(int x, int y) {
		ChessBoard board = getBoard();

		if (board.getPiece(x, y) == null) {
			throw new NoPieceFoundException();
		}

		List<PiecePosition> validPositions = new ArrayList<>();

		int row = x - 1, col = y - 1;
		while (row >= 0 && col >= 0) {
			Piece piece = board.getPiece(row, col);
			if (piece != null) {
				if (this.getColor() == Color.WHITE) {
					if (piece.getColor() == Color.WHITE) {
						break;
					} else {
						validPositions.add(new PiecePosition(row, col));
						break;
					}
				} else {
					if (piece.getColor() == Color.BLACK) {
						break;
					} else {
						validPositions.add(new PiecePosition(row, col));
						break;
					}
				}
			} else {
				validPositions.add(new PiecePosition(row, col));
			}
			row--;
			col--;
		}

		row = x + 1;
		col = y + 1;
		while (row < 8 && col < 8) {
			Piece piece = board.getPiece(row, col);
			if (piece != null) {
				if (this.getColor() == Color.WHITE) {
					if (piece.getColor() == Color.WHITE) {
						break;
					} else {
						validPositions.add(new PiecePosition(row, col));
						break;
					}
				} else {
					if (piece.getColor() == Color.BLACK) {
						break;
					} else {
						validPositions.add(new PiecePosition(row, col));
						break;
					}
				}
			} else {
				validPositions.add(new PiecePosition(row, col));
			}
			row++;
			col++;
		}

		row = x + 1;
		col = y - 1;
		while (row < 8 && col >= 0) {
			Piece piece = board.getPiece(row, col);
			if (piece != null) {
				if (this.getColor() == Color.WHITE) {
					if (piece.getColor() == Color.WHITE) {
						break;
					} else {
						validPositions.add(new PiecePosition(row, col));
						break;
					}
				} else {
					if (piece.getColor() == Color.BLACK) {
						break;
					} else {
						validPositions.add(new PiecePosition(row, col));
						break;
					}
				}
			} else {
				validPositions.add(new PiecePosition(row, col));
			}
			row++;
			col--;
		}

		row = x - 1;
		col = y + 1;
		while (row >= 0 && col < 8) {
			Piece piece = board.getPiece(row, col);
			if (piece != null) {
				if (this.getColor() == Color.WHITE) {
					if (piece.getColor() == Color.WHITE) {
						break;
					} else {
						validPositions.add(new PiecePosition(row, col));
						break;
					}
				} else {
					if (piece.getColor() == Color.BLACK) {
						break;
					} else {
						validPositions.add(new PiecePosition(row, col));
						break;
					}
				}
			} else {
				validPositions.add(new PiecePosition(row, col));
			}
			row--;
			col++;
		}

		return validPositions;
	}

}
