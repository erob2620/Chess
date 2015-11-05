package Game;

import java.util.ArrayList;
import java.util.List;

import Pieces.King;
import Pieces.Piece;
import Pieces.Point;

public class Player {
	private List<Piece> pieces;
	private static MoveGeneratorImpl generator;
	private boolean inCheck;
	private String name;
	private ChessBoard chessBoard;
	private Piece[][] board;
	public Player(ChessBoard chessBoard, String name) {
		pieces = new ArrayList<>();
		this.name = name;
		this.chessBoard = chessBoard;
		this.board = chessBoard.getBoard();
		generator = new MoveGeneratorImpl(chessBoard.getBoard());
	}
	public void addPiece(Piece p) {
		pieces.add(p);
	}
	public void removePiece(Piece p) {
		pieces.remove(p);
	}
	public List<String> getAvailableMoves() {
		List<String> availableMoves = new ArrayList<>();
		for(Piece p : pieces) {
			availableMoves.addAll(p.availableMoves(generator));
		}
		return availableMoves;
	}
	public boolean isInCheck(List<String> otherTeamsMoves) {
		inCheck = false;
		King king = null;
		for(Piece p : pieces) {
			if(p instanceof King) {
				king = (King)p;
				break;
			}
		}
		for(String s : otherTeamsMoves) {
			if(s.contains(king.getPoint().toString())) {
				inCheck = true;
				break;
			}
		}
//		if(otherTeamsMoves.contains(king.getPoint().toString())) {
//			inCheck = true;
//		}
		return inCheck;
	}
	@Override
	public String toString() {
		return name + " Player";
	}
	public boolean isInMate(Player otherPlayer) {
		boolean isMate = true;
		Piece capturedPiece = null;

		List<String> availableMoves = getAvailableMoves();
		for(String s : availableMoves) {
			Point[] moves = moveToPoint(s);
			if(chessBoard.isPieceMovable(moves[1])) {
				capturedPiece = board[moves[1].getCol()][moves[1].getRow()];
				
			}
			Piece p = board[moves[0].getCol()][moves[0].getRow()];
			board[moves[1].getCol()][moves[1].getRow()] = p;
			board[moves[0].getCol()][moves[0].getRow()] = null;
			p.setPoint(moves[1]);
			if(capturedPiece != null) otherPlayer.removePiece(capturedPiece);
			if(isInCheck(otherPlayer.getAvailableMoves())) {
				board[moves[1].getCol()][moves[1].getRow()] = null;
				board[moves[0].getCol()][moves[0].getRow()] = p;
				p.setPoint(moves[0]);
				if(capturedPiece != null) {
					board[moves[1].getCol()][moves[1].getRow()] = capturedPiece;
					otherPlayer.addPiece(capturedPiece);
					capturedPiece = null;
				}
			} else {
				board[moves[1].getCol()][moves[1].getRow()] = null;
				board[moves[0].getCol()][moves[0].getRow()] = p;
				p.setPoint(moves[0]);
				if(capturedPiece != null) {
					board[moves[1].getCol()][moves[1].getRow()] = capturedPiece;
					otherPlayer.addPiece(capturedPiece);
					capturedPiece = null;
				}
				isMate = false;
				break;
			}
		}
		return isMate;
	}
	private Point[] moveToPoint(String s) {
		String firstPoint = s.substring(0,2);
		String secondPoint = s.substring(2);
		Point firstMove = stringToPoint(firstPoint);
		Point secondMove = stringToPoint(secondPoint);
		return new Point[] {firstMove, secondMove}; 
		
	}
	private Point stringToPoint(String move) {
		Point p = new Point();
		p.setCol(move.charAt(0) - 65);
		p.setRow(56 - move.charAt(1));
		return p;
	}
}
