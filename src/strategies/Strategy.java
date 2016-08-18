package strategies;

import java.awt.Point;
import java.util.ArrayList;

import main_components.BitFunctions;
import main_components.Board;
import main_components.Command;
import main_components.CommandManager;
import main_components.Controller;
import main_components.View;
import main_components.Color;

/**
 * \brief
 * An abstract class. Each Strategy should decide a "move" for a computer A.I.
 * @author Rodney Shaghoulian
 */
public abstract class Strategy {
	public Controller controller;				///< The Controller in the MVC structure
	public View view;							///< The View in the MVC structure
	public CommandManager commandManager;		///< Keeps track of Commands for "undos" and "redos"
	
	/**
	 * Constructor - Initializes Variables
	 * @param controller	Corresponds to the current Othello simulation
	 */
	public Strategy(Controller controller){
		this.controller 	= controller;
		this.view 			= controller.view;
		this.commandManager = controller.commandManager;
	}
	
	/**
	 * Does a "move" on a Board. Which "move" is done depends on implemented strategy
	 * @param board		The Board that the computer A.I. will do a move on.
	 * @return			The updated Board after the "move" is performed.
	 */
	public abstract Board move(Board board);
	
	/**
	 * Get all Boards that can result from all possible moves on this turn.
	 * @param board		The current Board before the "move" is performed
	 * @return			The resulting Board after the "move" is performed.
	 */
	public ArrayList<Board> getAdjacentBoards(Board board){
		ArrayList<Board> boards = new ArrayList<>();
		long validMoves = board.getCurrentPlayer().validMoves;
		for (byte tileNum = 0; tileNum < 64; tileNum++, validMoves >>= 1){ //assumes 64-bit long
			if ((validMoves & 1) == 1){
				Board resultingBoard = new Board(board);
				Command command = new Command(resultingBoard, resultingBoard.playerTurn, BitFunctions.getPoint(tileNum));
				command.execute();
				boards.add(resultingBoard);
			}
		}
		return boards;
	}
	
	/**
	 * Gets Command that converts board into successorBoard
	 * @param board				The original Board that we will execute a command on.
	 * @param successorBoard	The resulting Board after executing a move.
	 * @return					The Command to execute.
	 */
	public Command getCommand(Board board, Board successorBoard){
		for (int row = 0; row < board.rows; row++){
			for (int col = 0; col < board.columns; col++){
				Color originalBoardColor  = board.getDiskColor(new Point(col, row));
				Color successorBoardColor = successorBoard.getDiskColor(new Point(col, row));
				if (originalBoardColor == Color.NONE && successorBoardColor != Color.NONE)
					return new Command(board, successorBoardColor, new Point(col, row));
			}
		}
		return null; // should never execute
	}
}
