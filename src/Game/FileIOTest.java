//package Game;
//import static org.junit.Assert.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//import org.junit.Test;
//
//
//public class FileIOTest {
//	private ChessBoard board;
//	@Test
//	public void placementTest() {
//		String[] placements = new String[] {"kld8","kde2","qla3","bdb4","nlc4","rdg2","pld5"};
//		String[] testAnswers = new String[] {"Light King on D8","Dark King on E2","Light Queen on A3","Dark Bishop on B4","Light Knight on C4","Dark Rook on G2","Light Pawn on D5"};
//		board = new ChessBoard();
//		FileIO fio = new FileIO(board);
//		for(int i = 0; i < placements.length; i++) {
//			assertEquals(fio.parse(placements[i]), "Place a " + testAnswers[i]);
//		}
//		
//	}
//	@Test
//	public void movementTest() {
//		board = new ChessBoard();
//		FileIO fio = new FileIO(board);
//		
//		assertEquals(fio.parse("d1e7"),"Move the piece at location D1 to E7");
//		assertEquals(fio.parse("c2g5"),"Move the piece at location C2 to G5");
//		assertEquals(fio.parse("e4g2"),"Move the piece at location E4 to G2");
//		assertEquals(fio.parse("a1g8"),"Move the piece at location A1 to G8");
//		assertEquals(fio.parse("f2b7*"),"Move the piece at location F2 to B7 and capture the piece at B7");
//		assertEquals(fio.parse("g4e8"),"Move the piece at location G4 to E8");
//	}
//	
//
//}
