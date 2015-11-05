package Game;
import Exceptions.PieceBlockedException;
import Interfaces.Validable;
import Pieces.*;

public class ValidatorImpl implements Validable{
	Piece[][] board;
	public ValidatorImpl(Piece[][] board) {
		this.board = board;
	}
	@Override
	public boolean isValid(Rook r,Point newPosition,String attack) throws PieceBlockedException {
		double d = findSlope(r.getPoint(),newPosition);
		
		if (isBlocked(r,newPosition) != null) {
			throw new PieceBlockedException(isBlocked(r,newPosition).toString());
		} else {
			return (d == 0 || d == 100);
		}
	}

	@Override
	public boolean isValid(Bishop b,Point newPosition,String attack) throws PieceBlockedException {
		double d = findSlope(b.getPoint(),newPosition);
		if (isBlocked(b,newPosition) != null && attack.isEmpty()) {
			throw new PieceBlockedException(isBlocked(b,newPosition).toString());
		} else {
			return (d == 1 || d == -1);
		}
	}

	@Override
	public boolean isValid(King k,Point newPosition,String attack) throws PieceBlockedException {
		Point current = k.getPoint();
		if (board[newPosition.getCol()][newPosition.getRow()] != null && !attack.isEmpty()) {
			return false;
//			throw new PieceBlockedException(isBlocked(k,newPosition).toString());
		} else {
			return(Math.abs(newPosition.getCol() - current.getCol()) < 2 && Math.abs(newPosition.getRow() - current.getRow()) < 2);
		}
	}

	@Override
	public boolean isValid(Queen q,Point newPosition,String attack) throws PieceBlockedException {
		double d = findSlope(q.getPoint(),newPosition);
		if (isBlocked(q,newPosition) != null) {
			throw new PieceBlockedException(isBlocked(q,newPosition).toString());
		} else {
			return ( d == -1 || d == 1 || d == 0 || d == 100);
		}
	}

	@Override
	public boolean isValid(Knight n,Point newPosition,String attack) {
		double slope = Math.abs(findSlope(n.getPoint(),newPosition));
		double dx = Math.abs(newPosition.getCol() - n.getPoint().getCol());
		double dy = Math.abs(newPosition.getRow() - n.getPoint().getRow());
		if(board[newPosition.getCol()][newPosition.getRow()] != null && !attack.isEmpty()) {
			return ((slope == 2 || slope == (1/2d)) && (dx < 3 && dy < 3));
		} else if(board[newPosition.getCol()][newPosition.getRow()] == null && attack.isEmpty()){
			return ((slope == 2 || slope == (1/2d)) && (dx < 3 && dy < 3));
		} else {
			return false;
		}
	}
	public double findSlope(Point first, Point second) {
		double y = second.getRow() - first.getRow();
		double x = second.getCol() - first.getCol();
		double slope = 0;
		if(x != 0) {
			slope = y / x;
		} else {
			slope = 100;
		}
		return slope;
	}

	@Override
	public boolean isValid(Pawn p, Point newPosition,String attack) throws PieceBlockedException {
		boolean isValid = false;
		int dy = newPosition.getRow() - p.getPoint().getRow();
		double slope = findSlope(p.getPoint(),newPosition);
		if(slope == 100) {
			if(Math.abs(p.getPoint().getRow() - newPosition.getRow()) == 2 && !p.getMoved()) {
				isValid = true;
				p.pieceMoved(true);
			} else if(Math.abs(p.getPoint().getRow() - newPosition.getRow()) < 2) {
				isValid = true;
			} else {
				
			}
		} else if((dy == -1 && p.isWhitePiece() || dy == 1 && !p.isWhitePiece()) && !attack.isEmpty()) {
			if(Math.abs(p.getPoint().getCol() - newPosition.getCol()) < 2) {
				isValid = true;
			}
		}
		if (isBlocked(p,newPosition) != null) {
			throw new PieceBlockedException(isBlocked(p,newPosition).toString());
		} else {
			return isValid;
		}
	}
	public boolean isValidCastle(Piece king, Point p2, Piece rook, Point p4) {
		boolean isValid = true;
		int kingDX = p2.getCol() - king.getPoint().getCol();
		int rookDX = p4.getCol() - rook.getPoint().getCol();
		if(findSlope(king.getPoint(),p2) == 0 && findSlope(rook.getPoint(),p4) == 0) {
			if(isBlocked(king,p2) == null && ((kingDX == -3 && rookDX == 2) || (kingDX == 2 && rookDX == -2))) {
				
			} else {
				isValid = false;
			}
		} else {
			isValid = false;
		}
		
		return isValid;
	}
	public Piece isBlocked(Piece p, Point p2) {
		Piece result = null;
		double slope = findSlope(p.getPoint(), p2);	
		
		if(Math.abs(slope) == 1) {
			int xDirection = (p2.getCol() - p.getPoint().getCol() > 0) ? 1 : -1;
			int yDirection = (p2.getRow() - p.getPoint().getRow() > 0)? 1 : -1;
			result = checkDiagonal(p.getPoint(),p2,xDirection,yDirection);
		} else if(Math.abs(slope) == 0) {
			int direction = (p2.getCol() - p.getPoint().getCol() > 0) ? 1 : -1;
			result = checkHorizontal(p.getPoint(),p2,direction);
		} else if(slope == 100) {
			int direction = (p2.getRow() - p.getPoint().getRow() > 0)? 1 : -1;
			result = checkVertical(p.getPoint(),p2,direction);
		} else {
			//do nothing
		}

		return result;
	}
	private boolean boundsCheck(int row, int col) {
		return ((col < 8 && col >= 0) && (row < 8 && row >= 0));
	}
	public Piece checkHorizontal(Point p1, Point p2, int direction) {
		Piece p = null;
		for(int i = p1.getCol() + direction; i != p2.getCol(); i += direction) {
			p = board[i][p1.getRow()];
			if(p != null) break;
			
		}
		return p;
	}
	public Piece checkVertical(Point p1, Point p2, int direction) {
		Piece p = null;
		for(int i = p1.getRow() + direction; i != p2.getRow(); i += direction) {
			p = board[p1.getCol()][i];
			if(p != null) break;
		}
		return p;
	}
	public Piece checkDiagonal(Point p1, Point p2, int xDirection, int yDirection) {
		Piece p = null;
		int row = p1.getRow();
		for(int col = p1.getCol() + xDirection; col != p2.getCol(); col += xDirection) {
			row += yDirection;
			p = board[col][row];
			if(p != null) break;
		}
		
		return p;
	}
}
