package Interfaces;

import Exceptions.PieceBlockedException;
import Game.ValidatorImpl;
import Pieces.Point;

public interface Visitor {
	public boolean visit(ValidatorImpl validator,Point newPosition,String attack) throws PieceBlockedException;
}
