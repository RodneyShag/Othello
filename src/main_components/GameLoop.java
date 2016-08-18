package main_components;

import strategies.Difficulty;

/** 
 * \brief The main game engine/logic
 * @author Rodney Shaghoulian
 */
public class GameLoop {
	/**
	 * \brief This is the main game loop. It creates a Controller for an Othello game.
	 * @param args	Command Line arguments (which we won't use)
	 */
	public static void main(String[] args) {
		new Controller(8, 8, Difficulty.HARD);
	}
}