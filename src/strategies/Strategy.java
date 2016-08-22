/** \brief The strategies for our Othello Program */
package strategies;

import java.awt.Point;
import java.util.ArrayList;

import main_components.Board;
import main_components.Color;
import main_components.Command;

/**
 * \brief
 * An abstract class. Each Strategy should decide a "move" for a computer A.I.
 * @author Rodney Shaghoulian
 */
public abstract class Strategy {
	
	/**
	 * Constructor
	 * @param controller	Corresponds to the current Othello simulation
	 */
	public Strategy(){
		; // Empty Constructor
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
		ArrayList<Point> validMoves;
		if (board.playerTurn == Color.BLACK)
			validMoves = board.blackPlayer.validMoves;
		else
			validMoves = board.whitePlayer.validMoves;
		for (Point move : validMoves){
			Board resultingBoard = new Board(board);
			Command command = new Command(resultingBoard, resultingBoard.playerTurn, move);
			command.execute();
			boards.add(resultingBoard);
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
				if (board.tile[row][col].color == Color.NONE && successorBoard.tile[row][col].color != Color.NONE)
					return new Command(board, successorBoard.tile[row][col].color, new Point(col, row));
			}
		}
		return null; // should never execute
	}
}
