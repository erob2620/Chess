package Pieces;


public class Point {
	private int row;
	private  int col;
	public Point(int col, int row) {
		this.col = col;
		this.row = row;
	}
	public Point() {
		
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getCol() {
		return col;
	}
	public void setCol(int col) {
		this.col = col;
	}
	
	@Override
	public String toString() {
		return (char)(col + 65) + "" + (char)(56 - row);
	}
}
