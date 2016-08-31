package strategies;

import main_components.Board;
import main_components.Color;

/**
 * \brief
 * Calculates the "utility" of a Board based on different "evaluation functions"
 * @author Rodney Shaghoulian
 */
public class Utility {
	
	public Board board;	///< The Board that we are calculating the "utility" for.
	public int value;	///< The utility value of the Board
	
	/**
	 * Constructor - Initializes variables
	 * @param board		The Board we are calculating the utility for.
	 */
	public Utility(Board board){
		this.board = board;
		this.value = 0;
	}
	
	
	
	
	
	
	
	
	
	/**
	 * Calculates utility based on the difference of player scores.
	 */
	public void utilityScoreDiff(){
		if (board.gameEnded)
			gameEndedUtility();
		else
			value = board.blackPlayer.score - board.whitePlayer.score;
	}
	
	/**
	 * Calculates utility based on the number of corners owned by each Player.
	 */
	public void utilityCorners(){
		if (board.gameEnded)
			gameEndedUtility();
		else
			value = board.cornersOwned(Color.BLACK) - board.cornersOwned(Color.WHITE);
	}
	
	/**
	 * Calculates utility based on valid moves remaining for each player. More moves equals greater utility
	 */
	public void utilityValidMoves(){
		if (board.gameEnded)
			gameEndedUtility();
		else
			value = board.blackPlayer.validMoves.size() - board.whitePlayer.validMoves.size();
	}
	
	/**
	 * Calculates utility based on valid moves remaining for each player. More moves equals greater utility. \n
	 * Also takes into account X squares, C squares, and corners.
	 */
	public void utilityFinal(){
		if (board.gameEnded)
			gameEndedUtility();
		else{
			int utilityBlack = board.blackPlayer.validMoves.size()
					           + (-63 + board.turn) * board.badCSquaresOwned(Color.BLACK) 
					           + (-60 + board.turn) * board.badXSquaresOwned(Color.BLACK)
				               + ( 66 - board.turn) * board.cornersOwned(Color.BLACK);
			int utilityWhite = board.whitePlayer.validMoves.size()
			           			+ (-63 + board.turn) * board.badCSquaresOwned(Color.WHITE) 
			           			+ (-60 + board.turn) * board.badXSquaresOwned(Color.WHITE)
			           			+ ( 66 - board.turn) * board.cornersOwned(Color.WHITE);
			if (board.turn >= 44){
				utilityBlack += board.blackPlayer.score;
				utilityWhite += board.whitePlayer.score;
			}
			value = utilityBlack - utilityWhite;
		}
	}
	
	/**
	 * Assigns a utility of 1000 for BLACK win and -1000 for WHITE win.
	 */
	public void gameEndedUtility(){
		if (board.gameEnded){
			if (board.winner == Color.BLACK)
				value = 10000; //pretty arbitrary. Just has to be bigger than Absolute Value of inner nodes
			else if (board.winner == Color.WHITE)
				value = -10000;  //pretty arbitrary. Just has to be bigger than Absolute Value of inner nodes
			else
				value = 0;
		}
	}
}
