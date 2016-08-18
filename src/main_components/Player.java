package main_components;

import java.awt.Point;

/**
 * \brief
 * A representation of a Player. May be Human or Computer.
 * @author Rodney Shaghoulian
 *
 */
public class Player {
	public Color color;			///< An enumerated type. "WHITE, BLACK, or NONE"
	public boolean isComputer;	///< false for Human Player. True for Computer Player
	public byte score;			///< Equal to the number of Disks corresponding to color
	public long validMoves;		///< Potential Moves a Player can do
	public long diskPositions;  ///< The positions of all of this Player's disks, represented as a "long"
	
	/** 
	 * Constructor. Simply initializes variables. validMoves must be updated outside of constructor (when both players exist)
	 * @param board		the Othello Board the Player is playing on.
	 * @param color		"WHITE" or "BLACK". "NONE" will not used.
	 * @param computer	boolean to determine if computer or human Player.
	 */
	public Player(Board board, Color color, boolean computer){
		this.color = color;
		this.isComputer = computer;
		score = 2; // since we start off with 2 Disks in center
		
		if (color == Color.BLACK)
			diskPositions = 0x0000001008000000L; // think of as 0x(bottom row, which is row 0)...(top row)
		else 
			diskPositions = 0x0000000810000000L;
		
		validMoves = 0L;
	}
	
	/**
	 * Copy Constructor - Deep Copy
	 * @param otherPlayer	the other Player to create a deep copy of
	 */
	public Player(Player otherPlayer){
		color      = otherPlayer.color;
		isComputer = otherPlayer.isComputer;
		score      = otherPlayer.score;
		validMoves = otherPlayer.validMoves;
		
		diskPositions = otherPlayer.diskPositions;
	}
	
	/**
	 * Updates valid (potential) moves for a Player. Player could possibly have 0 valid moves.
	 * @param board	The Board that the Player is playing Othello on
	 */
	public void updateValidMoves(Board board){ //can possibly make this more efficient.
		validMoves = 0L;
		for (byte row = 0; row < board.rows; row++){
			for (byte col = 0; col < board.columns; col++){
				Point movePoint = new Point(col, row);
				if (board.validMove(movePoint, color))
					validMoves = BitFunctions.setBit(validMoves, movePoint);
			}
		}
	}
	
	/**
	 * Determines if a Player has any potential moves
	 * @return	true if moves exist. false otherwise.
	 */
	public boolean hasMoves(){
		return validMoves != 0;
	}
}