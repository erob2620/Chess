package Pieces;

import java.util.List;

import Exceptions.PieceBlockedException;
import Game.MoveGeneratorImpl;
import Game.ValidatorImpl;
import Interfaces.Visitor;


public class King extends Piece implements Visitor{

	public King(Point _p, String name, char color, String boardName) {
		super(_p, name, color, boardName);
	}

	@Override
	public boolean visit(ValidatorImpl validator, Point newPosition,String attack) throws PieceBlockedException {
		return validator.isValid(this, newPosition,attack);
	}
	@Override
	public List<String> availableMoves(MoveGeneratorImpl generator) {
		return generator.getMoves(this);
	}

}
