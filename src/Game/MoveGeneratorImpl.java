package Game;

import java.util.*;
import Interfaces.MoveGenerator;
import Pieces.*;


public class MoveGeneratorImpl implements MoveGenerator{
	private List<String> moves;
	private ValidatorImpl validator;
	private Point pieceLocation;
	private Piece[][] board;
	public MoveGeneratorImpl(Piece[][] board) {
		this.board = board;
		validator = new ValidatorImpl(board);
	}
	@Override
	public List<String> getMoves(King p) {
		moves = new ArrayList<>();
		pieceLocation = p.getPoint();
		for(int i = pieceLocation.getRow() - 1; i <= pieceLocation.getRow() + 1; i++) {
			for(int j = pieceLocation.getCol() - 1; j <= pieceLocation.getCol() + 1; j++) {
				String attack = (boundsCheck(i,j) && board[j][i] != null) ? "*" : "";
				if(boundsCheck(i,j) && p.visit(validator, new Point(j,i), attack)) {
					moves.add(pieceLocation.toString() + new Point(j,i).toString());
				}
			}
		}
		return moves;
	}

	@Override
	public List<String> getMoves(Queen p) {
		moves.addAll(getHorizontalMoves(p.getPoint(),1));
		moves.addAll(getHorizontalMoves(p.getPoint(),-1));
		moves.addAll(getVerticalMoves(p.getPoint(),1));
		moves.addAll(getVerticalMoves(p.getPoint(),-1));
		moves.addAll(getDiagonalMoves(p.getPoint(),1,1));
		moves.addAll(getDiagonalMoves(p.getPoint(),1,-1));
		moves.addAll(getDiagonalMoves(p.getPoint(),-1,1));
		moves.addAll(getDiagonalMoves(p.getPoint(),-1,-1));
		return moves;
	}

	@Override
	public List<String> getMoves(Bishop b) {
		moves = new ArrayList<>();
		moves.addAll(getDiagonalMoves(b.getPoint(),1,1));
		moves.addAll(getDiagonalMoves(b.getPoint(),1,-1));
		moves.addAll(getDiagonalMoves(b.getPoint(),-1,1));
		moves.addAll(getDiagonalMoves(b.getPoint(),-1,-1));
		return moves;
	}

	@Override
	public List<String> getMoves(Knight n) {
		moves = new ArrayList<>();
		String attack;
		Point p = n.getPoint();
		int col = p.getCol();
		int row = p.getRow();
		Point[] possibleMoves = new Point[] { new Point(col + 2, row + 1),new Point(col + 2, row - 1), new Point(col - 2, row + 1),
				new Point(col - 2, row - 1), new Point(col + 1, row + 2),new Point(col + 1, row - 2),new Point(col - 1, row - 2),new Point(col - 1, row + 2)};
		for(Point move : possibleMoves) {
			if(boundsCheck(move.getRow(),move.getCol())) {
				attack = ((board[move.getCol()][move.getRow()] != null) && (!board[move.getCol()][move.getRow()].getColor().equals(n.getColor()))) ? "*" : "";
				if(n.visit(validator, move, attack)) {
					moves.add(p.toString() + move.toString());
				
				}
			}
			
		}
		return moves;
	}

	@Override
	public List<String> getMoves(Rook r) {
		moves = new ArrayList<>();
		moves.addAll(getHorizontalMoves(r.getPoint(),1));
		moves.addAll(getHorizontalMoves(r.getPoint(),-1));
		moves.addAll(getVerticalMoves(r.getPoint(),1));
		moves.addAll(getVerticalMoves(r.getPoint(),-1));
		return moves;
	}

	@Override
	public List<String> getMoves(Pawn p) {
		moves = new ArrayList<>();
		Point position = p.getPoint();
		int direction = (p.isWhitePiece())? -1: 1;
		int end = (p.isWhitePiece())? 4: 3;
		if(!p.getMoved()) {
			for(int i = position.getRow() + direction; i != end + direction; i+= direction) {
				if(boundsCheck(i,position.getCol()) && board[position.getCol()][i] == null) {
					moves.add(position.toString() + new Point(position.getCol(),i).toString());
				}
			}
		}else {
			if(boundsCheck(position.getRow() + direction,position.getCol()) && board[position.getCol()][position.getRow() + direction] == null) {
				moves.add(position.toString() + new Point(position.getCol(),position.getRow() + direction).toString());
			}
		}
		moves.addAll(getPossibleAttack(p, direction));
		return moves;
	}
	private boolean boundsCheck(int row, int col) {
		return ((col < 8 && col >= 0) && (row < 8 && row >= 0));
	}
	private List<String> getPossibleAttack(Pawn p, int direction) {
		List<String> attackMoves = new ArrayList<>();
		Point position = p.getPoint();
		String attack = "";
		int col = position.getCol();
		int row = position.getRow();
		Point[] possibleAttacks = new Point[] {new Point(col + 1, (row + direction)), new Point(col - 1,(row + direction))};
		for(Point point : possibleAttacks) {
			if(boundsCheck(point.getRow(),point.getCol()) && board[point.getCol()][point.getRow()] != null) {
				attack = "*";
			} else {
				attack = "";
			}
			if(p.visit(validator,point,attack)) {
				attackMoves.add(position.toString() + point.toString());
			}
		}
		
		return attackMoves;
	}
	private List<String> getHorizontalMoves(Point p, int direction) {
		List<String> horizontalMoves = new ArrayList<>();
		boolean collision = false;
		String position = "";
		
		for(int i = p.getCol() + direction; boundsCheck(i,p.getRow()); i += direction) {
			if(!collision) {	
				if(board[i][p.getRow()] != null) {
					if((board[i][p.getRow()]).getColor().equals(board[p.getCol()][p.getRow()].getColor())) {
						collision = true;
					}else {
						position = new Point(i, p.getRow()).toString();
						horizontalMoves.add(p.toString() + position);
						collision = true;
					}
				}else {
					position = new Point(i,p.getRow()).toString();
					horizontalMoves.add(p.toString() + position);
				}
			}
		}
		return horizontalMoves;
	}
	private List<String> getVerticalMoves(Point p, int direction) {
		List<String> verticalMoves = new ArrayList<>();
		boolean collision = false;
		String position = "";
		
		for(int i = p.getRow() + direction; (i < board.length && i >= 0); i += direction) {
			if(!collision) {	
				if(board[p.getCol()][i] != null) {
					if((board[p.getCol()][i]).getColor().equals(board[p.getCol()][p.getRow()].getColor())) {
						collision = true;
					}else {
						position = new Point(p.getCol(), i).toString();
						verticalMoves.add(p.toString() + position);
					
						collision = true;
					}
				}else {
					position = new Point(p.getCol(), i).toString();
					verticalMoves.add(p.toString() + position);
				}
			}
		}
		return verticalMoves;
	}
	private List<String> getDiagonalMoves(Point p, int xDirection, int yDirection) {
		List<String> diagonalMoves = new ArrayList<>();
		boolean hasCollided = false;
		String move = "";
		int row = p.getRow();
		row += yDirection;
		for (int i = p.getCol() + xDirection; boundsCheck(row, i); i += xDirection) {
			
			if (!hasCollided) {
				if(board[i][row] == null) {
					move = new Point(i,row).toString();
					diagonalMoves.add(p.toString() + move);
				}else {
					if((board[i][row]).getColor().equals(board[p.getCol()][p.getRow()].getColor())) {
						hasCollided = true;
					}else {
						hasCollided = true;
						
						move = new Point(i, row).toString();
						diagonalMoves.add(p.toString() + move);
					}
				}
			}
			row += yDirection;
		}
		return diagonalMoves;
	}
}
