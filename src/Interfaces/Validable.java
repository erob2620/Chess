package Interfaces;

import Pieces.*;

public interface Validable {
	
	public boolean isValid(Rook r,Point newPosition,String attack);
	public boolean isValid(Bishop b, Point newPosition,String attack);
	public boolean isValid(King k, Point newPosition,String attack);
	public boolean isValid(Queen q,Point newPosition,String attack);
	public boolean isValid(Knight n,Point newPosition,String attack);
	public boolean isValid(Pawn p, Point newPosition,String attack);
}
