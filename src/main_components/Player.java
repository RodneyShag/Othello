package main_components;

import java.awt.Point;
import java.util.ArrayList;

/**
 * \brief
 * A representation of a Player. May be Human or Computer.
 * @author Rodney Shaghoulian
 *
 */
public class Player {
	public Color color;					///< An enumerated type. "WHITE, BLACK, or NONE"
	public boolean isComputer;			///< false for Human Player. True for Computer Player
	public int score;					///< Equal to the number of Disks corresponding to color
	public ArrayList<Point> validMoves;	///< Potential Moves a Player can do
	
	/** 
	 * Constructor. Simply initializes variables.
	 * @param board		the Othello Board the Player is playing on.
	 * @param color		"WHITE" or "BLACK". "NONE" will not used.
	 * @param computer	boolean to determine if computer or human Player.
	 */
	public Player(Board board, Color color, boolean computer){
		this.color = color;
		this.isComputer = computer;
		score = 2; // since we start off with 2 Disks in center
		validMoves = new ArrayList<Point>();
		updateValidMoves(board);
	}
	
	/**
	 * Copy Constructor - Deep Copy
	 * @param otherPlayer	the other Player to create a deep copy of
	 */
	public Player(Player otherPlayer){
		color = otherPlayer.color;
		isComputer = otherPlayer.isComputer;
		score = otherPlayer.score;
		validMoves = new ArrayList<Point>(otherPlayer.validMoves);
	}
	
	/**
	 * Updates valid (potential) moves for a Player. Player could possibly have 0 valid moves.
	 * @param board	The Board that the Player is playing Othello on
	 */
	public void updateValidMoves(Board board){ //can possibly make this more efficient.
		validMoves.clear();
		for (int row = 0; row < board.rows; row++){
			for (int col = 0; col < board.columns; col++){
				Point movePoint = new Point(col, row);
				if (board.validMove(movePoint, color))
					validMoves.add(movePoint);
			}
		}
	}
}