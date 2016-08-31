package strategies;

import java.util.ArrayList;
import java.util.Collections;

import main_components.Board;
import main_components.Color;
import main_components.Command;
import main_components.Controller;

/**
 * \brief
 * Decides a computer move based on "AlphaBeta" strategy.
 * @author Rodney Shaghoulian
 */
public class AlphaBetaStrategy extends Strategy{

	public int depth = 7;			///< The depth to search the game tree. Values 1 to 8 are practical.
	
	public int nodesExpanded = 0;	///< Number of nodes expanded during search.
	
	/**
	 * Constructor - Calls subclasses constructor
	 * @param controller	The Othello simulation that we should run AlphaBeta on.
	 */
	public AlphaBetaStrategy(Controller controller){
		super(controller);
	}

	/**
	 * Does a "move" on a Board. Which "move" is done depends on AlphaBeta strategy
	 * @param board		The Board that the computer A.I. will do a move on.
	 * @return			The updated Board after the "move" is performed.
	 */
	public Board move(Board board){
		if (board.turn >= 48)
			depth = 60;
		
		int alpha = Integer.MIN_VALUE;
		int beta = Integer.MAX_VALUE;
		
		long startTime = System.currentTimeMillis();
		Board successorBoard = alphaBeta(board, 0, depth, alpha, beta);
		long endTime = System.currentTimeMillis();
		
		double elapsedTime = (endTime - startTime) / 1000.0;
		System.out.println("Turn " + board.turn + "\n------\nA.I. Move Time = " + elapsedTime + " seconds");
		System.out.println("Game Boards expanded = " + nodesExpanded);
		
		/* Execute the command */
		Command command = getCommand(board, successorBoard);
		commandManager.executeCommand(command);
		
		view.updateView();
		
		/* Display utility to console */
		Utility utility = new Utility(successorBoard);
		utility.utilityFinal();
		System.out.println("utility complex = " + utility.value + "\n");
		
		return board;
	}
	
	/**
	 * Recursive function. Uses AlphaBeta strategy to determine the next move
	 * @param board			The Board to do a "move" on.
	 * @param currLevel		The current depth we've searched to in game tree (where 0 corresponds to current state of Board)
	 * @param maxDepth		The number of levels deep we should search the game tree
	 * @param alpha			"value of the best choice of the max player" - CS 440 lecture slides
	 * @param beta			"lowest utility choice found so far for the min player" - CS 440 lecture slides
	 * @return				The Board after the "move" is performed.
	 */
	public Board alphaBeta(Board board, int currLevel, int maxDepth, int alpha, int beta){
		if (board.gameEnded || (currLevel == maxDepth))
			return board;
		Utility utility;
		
		ArrayList<Board> successorBoards = getAdjacentBoards(board);
		Board bestBoard = null;
		
		if (board.playerTurn == Color.BLACK){
			/* Sort Boards corresponding to stronger moves first */
			Collections.sort(successorBoards, Collections.reverseOrder(new BoardComparatorWhite()));
			
			int max = Integer.MIN_VALUE;
			for (int i = 0; i < successorBoards.size(); i++){
				nodesExpanded++;
				Board successor = successorBoards.get(i);
				Board lookaheadBoard = alphaBeta(successor, currLevel + 1, maxDepth, alpha, beta);
				utility = new Utility(lookaheadBoard);
				utility.utilityFinal();
				if (utility.value > max){
					max = utility.value;
					bestBoard = successor;
				}
				alpha = Math.max(alpha, utility.value);
				if (utility.value >= beta){
					return lookaheadBoard; // this is where we "prune", since MIN player will not allow this branch
				}
			}
		}
		else{
			/* Sort Boards corresponding to stronger moves first */
			Collections.sort(successorBoards, new BoardComparatorWhite());
			
			int min = Integer.MAX_VALUE;
			for (int i = 0; i < successorBoards.size(); i++){
				nodesExpanded++;
				Board successor = successorBoards.get(i);
				Board lookaheadBoard = alphaBeta(successor, currLevel + 1, maxDepth, alpha, beta);
				utility = new Utility(lookaheadBoard);
				utility.utilityFinal();
				if (utility.value < min){
					min = utility.value;
					bestBoard = successor;
				}
				beta = Math.min(beta, utility.value);
				if (utility.value <= alpha){
					return lookaheadBoard; // this is where we "prune", since MAX player will not allow this branch
				}
			}
		}
		return bestBoard;
	}
}

