package main_components;

import strategies.RandomStrategy;
import strategies.AlphaBetaStrategy;

/** 
 * \brief The main game engine/logic
 * @author Rodney Shaghoulian
 */
public class GameLoop {
	
	/**
	 * \brief This is the main game loop.
	 * @param args	Command Line arguments (which we won't use)
	 */
	public static void main(String[] args) {
			while(true){
		int numWhiteWins = 0;
		int numBlackWins = 0;
		for (int d = 1; d <= 6; d++){
			for (int i = 0; i < 2000; i++){
				Color winner = simulateGame(d, false);
				if (winner == Color.BLACK)
					numBlackWins++;
				else
					numWhiteWins++;
			}
			System.out.println("Depth = " + d + "   Static");
			System.out.println("Num White Wins = " + numWhiteWins);
			System.out.println("Num Black Wins = " + numBlackWins + "\n");
			numWhiteWins = 0;
			numBlackWins = 0;
		}
		
		for (int d = 1; d <= 6; d++){
			for (int i = 0; i < 2000; i++){
				Color winner = simulateGame(d, true);
				if (winner == Color.BLACK)
					numBlackWins++;
				else
					numWhiteWins++;
			}
			System.out.println("Depth = " + d + "   Dynamic");
			System.out.println("Num White Wins = " + numWhiteWins);
			System.out.println("Num Black Wins = " + numBlackWins + "\n");
			numWhiteWins = 0;
			numBlackWins = 0;
		}

			}
	}
	
	public static Color simulateGame(int depth, boolean dynamic){
		Board board = new Board(8, 8);
		RandomStrategy whiteStrategy    = new RandomStrategy();
		AlphaBetaStrategy blackStrategy = new AlphaBetaStrategy(depth, dynamic);
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