package Interfaces;

import java.util.List;

import Game.MoveGeneratorImpl;

public interface Movable {
	public List<String> availableMoves(MoveGeneratorImpl generator);
}
