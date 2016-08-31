package strategies;

import java.awt.Point;
import java.util.ArrayList;

import main_components.Board;
import main_components.Color;
import main_components.Command;
import main_components.Controller;

/**
 * \brief
 * Decides a computer move by selecting a valid move at random
 * @author Rodney Shaghoulian
 */
public class RandomStrategy extends Strategy {

	/**
	 * Constructor - Calls subclasses constructor
	 * @param controller	The Othello simulation that we should run Minimax on.
	 */
	public RandomStrategy(Controller controller){
		super(controller);
	}
	
	/**
	 * Does a "move" on a Board, which is decided randomly.
	 * @param board		The Board that the computer A.I. will do a move on.
	 * @return			The updated Board after the "move" is performed.
	 */
	public Board move(Board board){
		ArrayList<Point> validMoves;
		if (board.playerTurn == Color.BLACK)
			validMoves = board.blackPlayer.validMoves;
		else
			validMoves = board.whitePlayer.validMoves;
		if (validMoves.isEmpty())
			return null;
		Point move = chooseRandomMove(validMoves);
		Command command = new Command(board, controller.board.playerTurn, move);
		commandManager.executeCommand(command);
		view.updateView();
		return board;
	}
	
	/**
	 * Returns one of the validMoves at random. A "move" corresponds to a "Point".
	 * @param validMoves	The list of valid moves
	 * @return				One of the moves, chosen randomly.
	 */
	public Point chooseRandomMove(ArrayList<Point> validMoves){
		int numMoves = validMoves.size();
		int num = (int) (Math.random() * numMoves);
		return validMoves.get(num);
	}
}
