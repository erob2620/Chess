package Game;

import Exceptions.*;
import Pieces.*;

public class ChessBoard {
	private Player white, black, current, next;
	private Piece[][] board;
	private ValidatorImpl validator;
	private boolean isWhiteTurn;
	public ChessBoard() {
		isWhiteTurn = true;
		board = new Piece[8][8];
		white = new Player(this, "White");
		black = new Player(this, "Black");
		validator = new ValidatorImpl(board);
		initBoard();
	}

	public Piece[][] getBoard() {
		return board;
	}

	public void placePiece(Piece p) {
		board[p.getPoint().getCol()][p.getPoint().getRow()] = p;
	}

	public boolean isPieceMovable(Point p) {
		return (board[p.getCol()][p.getRow()] != null);
	}

	public boolean isMoveValid(Point p1, Point p2, String attack)throws WrongTurnException, InvalidMoveException,PieceBlockedException, CheckException, CheckMateException {
		Piece temp = board[p1.getCol()][p1.getRow()];
		current = (isWhiteTurn) ? white : black;
		next = (isWhiteTurn) ? black : white;
		if (temp.isWhitePiece() == isWhiteTurn) {
			try {
				if (temp.visit(validator, p2, attack)) {
					try {
						movePiece(temp, p2);
						if(!current.isInCheck(next.getAvailableMoves())) {
							isWhiteTurn = !isWhiteTurn;
							temp.pieceMoved(true);
						} else {
							movePiece(temp,p1);
							temp.pieceMoved(false);
							throw new CheckException(current.toString() + " is in check!");
							
						}
						if(next.isInCheck(current.getAvailableMoves())) {
							throw new CheckException("Move the " + temp.toString() + " from " + p1.toString() + " to " + p2.toString() + "\nCHECK!");
						}


					} catch(CheckException e) {
						if(next.isInMate(current)) {
							throw new CheckMateException("Move the " + temp.toString() + " from " + p1.toString() + " to " + p2.toString() + "\nCheckMate!" + "\n" + current.toString() + " wins");
						} else {
							throw e;
						}
						
					}
				} else {
					throw new InvalidMoveException("The " + temp.toString()
							+ " cannot be moved like that");
				}

			} catch (PieceBlockedException p) {
				throw p;
			}

		} else {
			throw new WrongTurnException("The " + temp.toString()
					+ " cannot move because it is not " + temp.getColor()
					+ "'s turn");
		}
		return true;
	}

	public void movePiece(Piece p, Point p2) throws CheckException {
		current = (isWhiteTurn) ? white : black;
		next = (isWhiteTurn) ? black : white;
		Piece temp = board[p.getPoint().getCol()][p.getPoint().getRow()];
		Piece capturedPiece = board[p2.getCol()][p2.getRow()];
		board[p.getPoint().getCol()][p.getPoint().getRow()] = null;
		board[p2.getCol()][p2.getRow()] = temp;
		temp.setPoint(p2);
		if(capturedPiece != null) {
			capturedPiece.setPoint(new Point(-5,-5));
			if(capturedPiece.isWhitePiece()) {
				white.removePiece(capturedPiece);
			} else {
				black.removePiece(capturedPiece); 
			}
		}
	}

	public boolean castle(Point p1, Point p2, Point p3,Point p4) throws CheckException {
		boolean isValid = false;
		current = (isWhiteTurn) ? white : black;
		next = (isWhiteTurn) ? black : white;
		Piece tempKing = board[p1.getCol()][p1.getRow()];
		Piece tempRook = board[p3.getCol()][p3.getRow()];
		if(tempKing != null && tempKing instanceof King) {
			if(tempRook != null && tempRook instanceof Rook) {
				if(!tempKing.getMoved() && !tempRook.getMoved()) {
					if(!current.isInCheck(next.getAvailableMoves()) && validator.isValidCastle(tempKing, p2,tempRook, p4)) {
						movePiece(tempKing,p2);
						tempKing.pieceMoved(true);
						movePiece(tempRook,p4);
						tempRook.pieceMoved(true);
						if(!current.isInCheck(next.getAvailableMoves())) {
							isWhiteTurn = !isWhiteTurn;
						} else {
							movePiece(tempKing,p1);
							tempKing.pieceMoved(false);
							movePiece(tempRook,p3);
							tempRook.pieceMoved(false);
							throw new CheckException("You can't make that move it would put you in check.");
							// check for mate
						}
						isWhiteTurn = !isWhiteTurn;
						isValid = true;
					} else {
						throw new CheckException(current.toString() + " can't castle because you are in check.");
					}
				}
			}
		}
		return isValid;
	}
	public void printBoard() {
		StringBuilder sb = new StringBuilder("    ");
		for (int row = -1; row < board.length; row++) {
			for (int col = 0; col < board.length; col++) {
				if (row == -1) {
					sb.append((char) (col + 65)).append("   ");
				} else {
					sb.append(" | ");
					if (board[col][row] == null) {
						sb.append("-");
					} else {
						sb.append(board[col][row].getBoardName());
					}
				}
			}
			if (row != -1) {
				sb.append(" |");
			}
			sb.append("\n  ---------------------------------").append("\n");
			if (row != 7) {
				sb.append(7 - row);
			}
		}
		System.out.println(sb.toString());
	}

	public void initBoard() {
		 initPieces('l');
		 initPawns('l');
		 initPieces('d');
		 initPawns('d');
	}

	public void initPieces(char color) {
		Piece p = null;
		int col = (color == 'l') ? 7 : 0;
		Player player = (color == 'l') ? white : black;
		for (int i = 0; i < board.length; i++) {
			switch (i) {
			case 0:
			case 7:
				p = new Rook(new Point(i, col), "Rook", color, "r");
				break;
			case 1:
			case 6:
				p = new Knight(new Point(i, col), "Knight", color, "n");

				break;
			case 2:
			case 5:
				p = new Bishop(new Point(i, col), "Bishop", color, "b");
				break;
			case 4:
				p = new King(new Point(i, col), "King", color, "k");
				break;
			case 3:
				p = new Queen(new Point(i, col), "Queen", color, "q");
				break;
			}
			player.addPiece(p);
			placePiece(p);
		}
	}

	public void initPawns(char color) {
		Piece p = null;
		int row = (color == 'l') ? 6 : 1;
		Player player = (color == 'l') ? white : black;
		for (int i = 0; i < board.length; i++) {
			p = new Pawn(new Point(i, row), "Pawn", color, "p");
			player.addPiece(p);
			placePiece(p);
		}
	}

	public String getPiece(Point p) {
		return board[p.getCol()][p.getRow()].toString();
	}
}
