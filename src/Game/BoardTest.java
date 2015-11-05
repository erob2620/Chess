//package Game;
//import static org.junit.Assert.*;
//
//import org.junit.Test;
//
//import Pieces.Piece;
//import Pieces.Point;
//
//
//public class BoardTest {
//	private ChessBoard board;
//	@Test
//	public void placementTest() {
//		board = new ChessBoard();
//		Piece p = new Piece(new Point(3,3),"King",'l',"k");
//		Piece p2 = new Piece(new Point(5,5),"Knight",'d',"n");
//		board.placePiece(p2);
//		board.placePiece(p);
//		assertEquals(board.getBoard()[3][3],p);
//		assertEquals(board.getBoard()[5][5],p2);
//	}
//	
//	@Test
//	public void movementTest() {
//		board = new ChessBoard();
//		
//		Piece p = new Piece(new Point(3,3),"King",'l',"k");
//		
//		board.placePiece(p);
//		board.movePiece(p, new Point(5,5));
//		
//		
//		assertEquals(board.getBoard()[5][5], p);
//		assertEquals(board.isPieceMovable(new Point(3,3)), false);
//	}
//
//}
