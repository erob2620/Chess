package Game;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Exceptions.*;
import Pieces.Point;


public class FileIO {
	//public static final Pattern PLACEMENT_PATTERN = Pattern.compile("(?<piece>[kqbnrp])(?<color>[ld])(?<column>[a-g])(?<row>[1-8])");
	public static final Pattern MOVEMENT_PATTERN = Pattern.compile("(?<firstCol>[a-h])(?<firstRow>[1-8])(?<secCol>[a-h])(?<secRow>[1-8])(?<attack>\\*?)");
	public static final Pattern MULTI_PATTERN = Pattern.compile("(?<firstCol>[a-h])(?<firstRow>[1-8])(?<secCol>[a-h])(?<secRow>[1-8])(?<thirdCol>[a-h])(?<thirdRow>[1-8])(?<fourthCol>[a-h])(?<fourthRow>[1-8])");
	public static Map<String, String> pieceMap = new HashMap<String, String>();
	public ChessBoard board;
	
	static {
		pieceMap.put("k", "King");
		pieceMap.put("q", "Queen");
		pieceMap.put("b", "Bishop");
		pieceMap.put("n", "Knight");
		pieceMap.put("r", "Rook");
		pieceMap.put("p", "Pawn");
	}
	public FileIO(ChessBoard board) {
		this.board = board;
	}
	public String parse(String input) throws WrongTurnException, InvalidMoveException, PieceBlockedException, CheckMateException, CheckException {
		String toReturn = "";
		//Matcher placementMatcher = PLACEMENT_PATTERN.matcher(input);
		Matcher movementMatcher = MOVEMENT_PATTERN.matcher(input);
		Matcher multiMatcher = MULTI_PATTERN.matcher(input);
		
//		if(placementMatcher.find()) {
//			String piece = placementMatcher.group("piece");
//			String color = placementMatcher.group("color");
//			Point point = findPoint(placementMatcher.group("column").toUpperCase(), placementMatcher.group("row"));
//			
//			toReturn = placement(piece,color,point);
//		} else
		if(multiMatcher.find()) {
			Point firstPoint = findPoint(multiMatcher.group("firstCol").toUpperCase(),multiMatcher.group("firstRow"));
			Point secondPoint = findPoint(multiMatcher.group("secCol").toUpperCase(), multiMatcher.group("secRow"));
			Point thirdPoint = findPoint(multiMatcher.group("thirdCol").toUpperCase(), multiMatcher.group("thirdRow"));
			Point fourthPoint = findPoint(multiMatcher.group("fourthCol").toUpperCase(), multiMatcher.group("fourthRow"));
		
			toReturn = multiMovement(firstPoint,secondPoint,thirdPoint,fourthPoint);
		} else if(movementMatcher.find()) {
			
			Point firstPoint = findPoint(movementMatcher.group("firstCol").toUpperCase(), movementMatcher.group("firstRow"));
			Point secondPoint = findPoint(movementMatcher.group("secCol").toUpperCase(), movementMatcher.group("secRow"));
			String attack = movementMatcher.group("attack");
			
			toReturn = movement(firstPoint,secondPoint,attack);
		}  else {
			toReturn = "That is an invalid command: " + input;
		}
		
		return toReturn;
	}
	
//	public String placement(String piece, String color, Point point) {
//		Piece p = new Piece(point,pieceMap.get(piece),color.charAt(0),piece);
//		board.placePiece(p);
//		return new StringBuilder("Place a ").append(pieceColor(color)).append(" ").append(pieceMap.get(piece)).append(" on ").append(point).toString();
//	}
	
	public String movement(Point firstPoint, Point secondPoint,String attack) throws WrongTurnException, InvalidMoveException, PieceBlockedException, CheckMateException, CheckException {
		StringBuilder sb = new StringBuilder(firstPoint.toString()).append(secondPoint.toString()).append(": ");

		if(board.isPieceMovable(firstPoint)) {
			if(attack.isEmpty()) {
				if(!board.isPieceMovable(secondPoint)) {
					try {
						if(board.isMoveValid(firstPoint, secondPoint,attack)) {
							sb.append("Move the ").append(board.getPiece(secondPoint)).append(" at location ").append(firstPoint).append(" to ").append(secondPoint);
						}
					} catch (WrongTurnException | InvalidMoveException | PieceBlockedException | CheckMateException | CheckException aBottleOfRum) {
						throw aBottleOfRum;
					}
				}
				else {
					sb.append("That was an invalid command, you must use an * to indicate an attack.");
				}
			}else {
				if(board.isPieceMovable(secondPoint)) {
					String p = board.getPiece(secondPoint);
					try {
						board.isMoveValid(firstPoint, secondPoint,attack);
						sb.append("Move the ").append(board.getPiece(secondPoint)).append(" at location ").append(firstPoint).append(" to ").append(secondPoint);
						sb.append(" and capture the ").append(p).append(" at ").append(secondPoint);
					} catch (WrongTurnException | InvalidMoveException | PieceBlockedException | CheckException | CheckMateException e) {
						throw e;
					}
				}
				else {
					sb.append("There was no piece to capture at that point: ").append(secondPoint);
				}
			}
		}
		else {
			sb.append("There was no piece to move at that location: ").append(firstPoint);
		}
		
		return sb.toString();
	}
	public String multiMovement(Point firstPoint, Point secondPoint, Point thirdPoint, Point fourthPoint) throws CheckException {
		StringBuilder sb = new StringBuilder(firstPoint.toString()).append(secondPoint.toString()).append(thirdPoint.toString()).append(fourthPoint.toString()).append(": ");
		try {
			if(board.castle(firstPoint,secondPoint,thirdPoint,fourthPoint)) {
				sb.append("Move the ").append(board.getPiece(secondPoint)).append(" at location ").append(firstPoint).append(" to ").append(secondPoint)
						.append(" and the ").append(board.getPiece(fourthPoint)).append(" at location ").append(thirdPoint).append(" to ").append(fourthPoint).toString();
			} else {
				sb.append("That was an invalid castle attempt.").toString();
			}
		} catch (CheckException e) {
			throw e;
		}
		return sb.toString();
	}
//	public String pieceColor(String color) {
//		return (color.equals("l")) ? "Light" : "Dark";
//	}
	public static Point findPoint(String col, String row) {
		Point p = new Point();
		p.setCol(col.charAt(0) - 65);
		p.setRow(56 - row.charAt(0));
		return p;
	}
}
