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
	 * Calculates utility based on valid moves remaining for each player. More moves equals greater utility
	 */
	public void utilityValidMoves(){
		if (board.gameEnded)
			gameEndedUtility();
		else
			value = board.blackPlayer.validMoves.size() - board.whitePlayer.validMoves.size();
	}
	
	/**
	 * Calculates utility based on valid moves remaining for each player. More moves equals greater utility \n
	 * Also takes into account X squares, C squares, and corners.
	 */
	public void utilityStatic(){
		if (board.gameEnded)
			gameEndedUtility();
		else{
			int utilityBlack = board.blackPlayer.validMoves.size()
					           - 10 * board.badCSquaresOwned(Color.BLACK) 
				               - 20 * board.badXSquaresOwned(Color.BLACK)
				               + 50 * board.cornersOwned(Color.BLACK);
			int utilityWhite = board.whitePlayer.validMoves.size()
					           - 10 * board.badCSquaresOwned(Color.WHITE) 
					           - 20 * board.badXSquaresOwned(Color.WHITE)
					           + 50 * board.cornersOwned(Color.WHITE);
			value = utilityBlack - utilityWhite;
		}
	}
	
	/**
	 * Calculates utility based on valid moves remaining for each player. More moves equals greater utility \n
	 * Also takes into account X squares, C squares, and corners.
	 */
	public void utilityDynamic(){
		if (board.gameEnded)
			gameEndedUtility();
		else{
			int utilityBlack = board.blackPlayer.validMoves.size()
					           + (-65 + board.turn) * board.badCSquaresOwned(Color.BLACK) 
					           + (-60 + board.turn) * board.badXSquaresOwned(Color.BLACK)
				               + ( 70 - board.turn) * board.cornersOwned(Color.BLACK);
			int utilityWhite = board.whitePlayer.validMoves.size()
			           			+ (-65 + board.turn) * board.badCSquaresOwned(Color.WHITE) 
			           			+ (-60 + board.turn) * board.badXSquaresOwned(Color.WHITE)
			           			+ ( 70 - board.turn) * board.cornersOwned(Color.WHITE);
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
