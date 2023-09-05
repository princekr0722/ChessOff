package com.chessoff;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.chessoff.models.chess.ChessBoard;
import com.chessoff.models.chess.Color;

@SpringBootApplication
public class ChessOffApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChessOffApplication.class, args);
		//
		// ChessBoard board = ChessBoard.get(Color.BLACK);
		// System.out.println(board);
		//
		// board = ChessBoard.get(Color.BLACK);
		// System.out.println(board);
		//
		// System.out.println(board.getPiece(1, 0).getNextValidPositions());
		// board.movePieceTo(1, 0, 3, 0);
		//
		// System.out.println(board);
		//
		// System.out.println(board.getPiece(6, 2).getNextValidPositions());
		// board.movePieceTo(6, 2, 4, 2);
		//
		// System.out.println(board);
		//
		// System.out.println(board.getPiece(1, 3).getNextValidPositions());
		// board.movePieceTo(1, 3, 3, 3);
		//
		// System.out.println(board);
		//
		// System.out.println(board.getPiece(4, 2).getNextValidPositions());
		// System.out.println(board.getCapturedBlackPieces());
		// board.movePieceTo(4, 2, 3, 3);
		// System.out.println(board);
		// System.out.println(board.getCapturedBlackPieces());
	}
}
