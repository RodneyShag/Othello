package main_components;

import strategies.RandomStrategy;
import strategies.MinimaxStrategy;
import strategies.AlphaBetaStrategy;

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
		int numWhiteWins = 0;
		int numBlackWins = 0;
		for (int i = 0; i < 100; i++){
			Color winner = simulateGame();
			if (winner == Color.BLACK)
				numBlackWins++;
			else
				numWhiteWins++;
		}
		System.out.println("Num White Wins = " + numWhiteWins);
		System.out.println("Num Black Wins = " + numBlackWins);
		
	}
	
	public static Color simulateGame(){
		Board board = new Board(8, 8);
		RandomStrategy whiteStrategy = new RandomStrategy();
		AlphaBetaStrategy blackStrategy = new AlphaBetaStrategy(6);
		while(true){
			if (!board.gameEnded){
				if (board.playerTurn == Color.BLACK)
					blackStrategy.move(board);
				else
					whiteStrategy.move(board);
			}
			else
				return board.winner;
		}
	}
}