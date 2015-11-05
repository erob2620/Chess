package Pieces;

import java.util.ArrayList;
import java.util.List;

import Game.MoveGeneratorImpl;
import Game.ValidatorImpl;
import Interfaces.Movable;
import Interfaces.Visitor;


public class Piece implements Visitor, Movable {
	private Point p;
	private String name;
	private String color;
	private String boardName;
	private boolean hasMoved;
	
	public Piece(Point _p, String name, char color, String boardName) {
		this.p = _p;
		this.name = name;
		this.color = (color == 'l')? "Light" : "Dark";
		this.boardName = boardName;
		hasMoved = false;
	}
	@Override
	public String toString() {
		return new StringBuilder(color).append(" ").append(name).toString();
	}
	public String getBoardName() {
		return (color.equals("Light")) ? boardName : boardName.toUpperCase();
	}
	public Point getPoint() {
		return p;
	}
	public void setPoint(Point p) {
		this.p = p;
	}
	@Override
	public boolean visit(ValidatorImpl validator, Point newPosition,String attack) {
		return false;
	}
	public boolean isWhitePiece() {
		return color.equals("Light");
	}
	public String getColor() {
		return color;
	}
	@Override
	public List<String> availableMoves(MoveGeneratorImpl generator) {
		return new ArrayList<String>();
	}
	public boolean getMoved() {
		return hasMoved;
	}
	public void pieceMoved(boolean moved) {
		hasMoved = moved;
	}
}
