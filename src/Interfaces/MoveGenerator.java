package Interfaces;

import java.util.List;

import Pieces.*;

public interface MoveGenerator {
	public List<String> getMoves(King p);
	public List<String> getMoves(Queen p);
	public List<String> getMoves(Bishop b);
	public List<String> getMoves(Knight n);
	public List<String> getMoves(Rook r);
	public List<String> getMoves(Pawn p);
}
