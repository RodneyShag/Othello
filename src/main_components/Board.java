package main_components;

import java.awt.Point;

/**
 * \brief
 * This is where the 2-player game of Othello will be played.
 * @author Rodney Shaghoulian
 */
public class Board {
	public final byte rows;		///< Number of rows on Board.
	public final byte columns;	///< Number of columns on Board.
	
	/* Players */
	public Player blackPlayer;	///< Player 1 on Board. Uses BLACK Disks.
	public Player whitePlayer;	///< Player 2 on Board. Uses WHITE Disks.
	
	public Color playerTurn;	///< Either WHITE's turn or BLACK's turn
	public boolean gameEnded;	///< True if game is over. False otherwise.
	public Color winner;		///< Either Color.BLACK or Color.WHITE when not null.
	public byte turn;			///< # of moves that have happened. Value from 1 to 60.
	
	/**
	 * Constructor - Initializes a Board
	 */
	public Board(){
		/* Initialize Essential Info */
		rows = 8;
		columns = 8;
		
		/* Initialize Players */
		blackPlayer = new Player(this, Color.BLACK, false);
		whitePlayer = new Player(this, Color.WHITE, true);
		
		blackPlayer.updateValidMoves(this);
		whitePlayer.updateValidMoves(this);
		
		/* Initialize Statuses */
		playerTurn = Color.BLACK;
		gameEnded = false;
		winner = null;
		turn = 1;
	}
	
	/**
	 * Copy Constructor - Creates a deep copy of a Board
	 * @param otherBoard	The other Board to create a deep copy of
	 */
	public Board(Board otherBoard){
		/* Copy essential info */
		rows = otherBoard.rows;
		columns = otherBoard.columns;
		
		/* Copy Players */
		blackPlayer = new Player(otherBoard.blackPlayer);
		whitePlayer = new Player(otherBoard.whitePlayer);
		
		/* Copy statuses */
		playerTurn  = otherBoard.playerTurn;
		gameEnded   = otherBoard.gameEnded;
		winner      = otherBoard.winner;
		turn		= otherBoard.turn;
	}
	
	/**
	 * Determines if a position exists on a Board
	 * @param pos	The position we are checking x and y coordinates for
	 * @return		true/false depending on whether position is valid on Board
	 */
	public boolean validPosition(Point pos){
		return (pos.x >= 0 && pos.y >= 0 && pos.x < rows && pos.y < columns);
	}
	
	/**
	 * Determines the Color (WHITE, BLACK, NONE) of the Disk at a position on the Board
	 *  
	 * @param pos	The position that a Disk may reside
	 * @return		The color of the Disk (WHITE, BLACK, NONE)
	 */
	public Color getDiskColor(Point pos){
		if (BitFunctions.getBit(blackPlayer.diskPositions, pos))
			return Color.BLACK;
		if (BitFunctions.getBit(whitePlayer.diskPositions, pos))
			return Color.WHITE;
		return Color.NONE;
	}
	
	/**
	 * Sets the Color (WHITE, BLACK, NONE) of the Disk at a position on the Board \n
	 * by updating blackPlayer's and whitePlayer's disk positions
	 * @param pos		The position that a Disk may reside
	 * @param newColor	The color to set the disk to
	 * @return			The color of the Disk (WHITE, BLACK, NONE)
	 */
	public void setDiskColor(Point pos, Color newColor){
		/* Remove old color */
		Color oldColor = getDiskColor(pos);
		if (oldColor == Color.BLACK)
			blackPlayer.diskPositions = BitFunctions.clearBit(blackPlayer.diskPositions, pos);
		else if (oldColor == Color.WHITE)
			whitePlayer.diskPositions = BitFunctions.clearBit(whitePlayer.diskPositions, pos);
		
		/* Set new color */
		if (newColor == Color.BLACK)
			blackPlayer.diskPositions = BitFunctions.setBit(blackPlayer.diskPositions, pos);
		else if (newColor == Color.WHITE)
			whitePlayer.diskPositions = BitFunctions.setBit(whitePlayer.diskPositions, pos);
	}
	
	/**
	 * Toggles playerTurn from WHITE to BLACK, and vice versa
	 */
	public void updateTurn(){
		if (playerTurn == Color.WHITE && blackPlayer.hasMoves())
			playerTurn = Color.BLACK;
		else if (playerTurn == Color.BLACK && whitePlayer.hasMoves())
			playerTurn = Color.WHITE;
		turn = (byte) (blackPlayer.score + whitePlayer.score - 3);
	}
	
	/**
	 * Determines if game is over. Updates "gameEnded" and "winner" if necessary.
	 */
	public void updateGameStatus(){
		if (blackPlayer.hasMoves() || whitePlayer.hasMoves())
			return;
		gameEnded = true;
		if (blackPlayer.score > whitePlayer.score)
			winner = Color.BLACK;
		else if (whitePlayer.score > blackPlayer.score)
			winner = Color.WHITE;
		else
			winner = Color.NONE;
	}
	
	/**
	 * Updates Board: by updating valid moves, turn, and game status
	 */
	public void updateBoard(){
		blackPlayer.updateValidMoves(this);
		whitePlayer.updateValidMoves(this);
		blackPlayer.score = BitFunctions.cardinality(blackPlayer.diskPositions);
		whitePlayer.score = BitFunctions.cardinality(whitePlayer.diskPositions);
		updateTurn();
		updateGameStatus();
	}
	
	/**
	 * Determines if a move is valid.
	 * @param pos	the desired position to place a Disk.
	 * @param color	the color of the Disk being placed.
	 * @return		true if move is valid. false otherwise.
	 */
	public boolean validMove(Point pos, Color color){
		if (pos == null || color == null || !validPosition(pos) || color == Color.NONE || getDiskColor(pos) != Color.NONE)
			return false;
		
		if (validInDirection(pos, color, 1, 0))			// Right
			return true;
		else if (validInDirection(pos, color, -1, 0))	// Left
			return true;
		else if (validInDirection(pos, color, 0, 1))	// Up
			return true;
		else if (validInDirection(pos, color, 0, -1))	// Down
			return true;
		else if (validInDirection(pos, color, 1, 1))	// NorthEast
			return true;
		else if (validInDirection(pos, color, 1, -1))	// NorthWest
			return true;
		else if (validInDirection(pos, color, -1, 1))	// SouthEast
			return true;
		else if (validInDirection(pos, color, -1, -1))	// SouthWest
			return true;
		return false;
	}
	
	/**
	 * Determines if a a Disk placed in a position will flip Disks in a certain Direction
	 * @param pos			the desired position to place a Disk.
	 * @param color			the color of the Disk being placed.
	 * @param xDirection	the x Direction to test flipping Disks (-1, 0, 1).
	 * @param yDirection	the y Direction to test flipping Disks (-1, 0, 1).
	 * @return				true if valid. false otherwise.
	 */
	public boolean validInDirection(Point position, Color color, int xDirection, int yDirection){
		Point pos = new Point(position);
		Color opponentColor = Functions.getOppositeColor(color);
		
		/* For valid move: immediate neighbor should be opposing Color */
		pos.x += xDirection;
		pos.y += yDirection;
		if (!validPosition(pos) || getDiskColor(pos) != opponentColor)
			return false;
		
		/* For valid move: there should be a Disk of our own Color at the end */
		pos.x += xDirection;
		pos.y += yDirection;
		while(validPosition(pos)){
			if (getDiskColor(pos) == color)
				return true;
			else if (getDiskColor(pos) == Color.NONE)
				return false;
			pos.x += xDirection;
			pos.y += yDirection;
		}
		return false;
	}
	
	/**
	 * Removes a Disk by changing it's color to Color.NONE
	 * @param pos		The position of the Disk to remove
	 */
	public void removeDisk(Point pos){
		setDiskColor(pos, Color.NONE);
	}
	
	public void flipCaptures(long captures, Color colorToFlipTo){
		Player player   = getPlayerFromColor(colorToFlipTo);
		Player opponent = getOpponentOfColor(colorToFlipTo);
		
		player.diskPositions |= captures;
		opponent.diskPositions &= ~captures;
	}
	
	/**
	 * Flips (captures) opponent's disks
	 * @param pos	The position of the Disk that was placed.
	 * @param color	The color of the Disk.
	 * @return		a "long" representing the disks that were flipped
	 */
	public long getCaptures(Point pos, Color color){
		long captures = 0L;
		
		if (!validPosition(pos))
			return captures;
		captures |= toFlipInDirection(pos, color,  1,  0);	// Right
		captures |= toFlipInDirection(pos, color, -1,  0);	// Left
		captures |= toFlipInDirection(pos, color,  0,  1);	// Up
		captures |= toFlipInDirection(pos, color,  0, -1);	// Down
		captures |= toFlipInDirection(pos, color,  1,  1);	// NorthEast
		captures |= toFlipInDirection(pos, color,  1, -1);	// NorthWest
		captures |= toFlipInDirection(pos, color, -1,  1);	// SouthEast
		captures |= toFlipInDirection(pos, color, -1, -1);	// SouthWest
		
		return captures;
	}

	public long toFlipInDirection(Point position, Color color, int xDirection, int yDirection){
		Point pos = new Point(position); // this is crucial so that I don't alter "pos"
		long disksToFlip = 0L;
		if (!validInDirection(pos, color, xDirection, yDirection))
			return disksToFlip;
		Color opponentColor = Functions.getOppositeColor(color);
		pos.x += xDirection;
		pos.y += yDirection;
		while (validPosition(pos) && getDiskColor(pos) == opponentColor){
			disksToFlip = BitFunctions.setBit(disksToFlip, pos);
			pos.x += xDirection;
			pos.y += yDirection;
		}
		return disksToFlip;
	}
	
	/**
	 * Places a Disk on a Board. Captures opponents Disks. \n
	 * Assumption: Function assumes it's a valid move.
	 * @param pos	The position where the Disk will be placed.
	 * @param color	The color of the Disk.
	 */
	public void placeDisk(Point pos, Color color){
		setDiskColor(pos, color);
		long captures = getCaptures(pos, color);
		flipCaptures(captures, color);
		updateBoard();
	}

	/**
	 * returns the player whose turn it currently is
	 * @return	blackPlayer or whitePlayer
	 */
	public Player getCurrentPlayer(){
		if (playerTurn == Color.BLACK)
			return blackPlayer;
		else
			return whitePlayer;
	}
	
	/**
	 * returns the player whose turn it ISN'T
	 * @return	blackPlayer or whitePlayer
	 */
	public Player getOpponent(){
		if (playerTurn == Color.BLACK)
			return whitePlayer;
		else
			return blackPlayer;
	}
	
	/**
	 * Returns Player corresponding to Color
	 * @param color		the Color of the player to return
	 * @return			the Player corresponding to the Color
	 */
	public Player getPlayerFromColor(Color color){
		if (color == Color.BLACK)
			return blackPlayer;
		else
			return whitePlayer;
	}
	
	/**
	 * Returns Player corresponding to opponent of Color
	 * @param color		the Color of the opponent to return
	 * @return			the Player corresponding to the opponent Color
	 */
	public Player getOpponentOfColor(Color color){
		if (color == Color.BLACK)
			return whitePlayer;
		else
			return blackPlayer;
	}
	
	/**
	 * Calculates the number of 'X squares' that are occupied by a Disk of a certain Color. \n
	 * which are not next to a corner owned by that same color.
	 * @param color		the Color of the Disks that we want to check the corners for.
	 * @return			the number of lone bad spots that are occupied by a Disk of a certain Color.
	 */
	public int xSquaresOwned(Color color){
		/* Get corresponding Player */
		Player player = null;
		if (color == Color.BLACK)
			player = blackPlayer;
		else
			player = whitePlayer;
		
		int badSpots = 0;

		/* Bottom Left */
		if ((player.diskPositions & 0x8000000000000000L) == 0){
			if ((player.diskPositions & 0x0040000000000000L) != 0)
				badSpots++;
		}
		/* Bottom Right */
		if ((player.diskPositions & 0x0100000000000000L) == 0){
			if ((player.diskPositions & 0x0002000000000000L) != 0)
				badSpots++;
		}
		/* Top Left */
		if ((player.diskPositions & 0x0000000000000080L) == 0){
			if ((player.diskPositions & 0x0000000000004000L) != 0)
				badSpots++;
		}
		/* Top Right */
		if ((player.diskPositions & 0x0000000000000010L) == 0){
			if ((player.diskPositions & 0x0000000000000200L) != 0)
				badSpots++;
		}
		return badSpots;
	}
	
	/**
	 * Calculates the number of 'C squares' that are occupied by a Disk of a certain Color, \n
	 * which are not next to a corner owned by that same color.
	 * @param color		the Color of the Disks that we want to check the C squares for.
	 * @return			the number of lone C squares that are occupied by a Disk of a certain Color.
	 */
	public int cSquaresOwned(Color color){
		/* Get corresponding Player */
		Player player = null;
		if (color == Color.BLACK)
			player = blackPlayer;
		else
			player = whitePlayer;
		
		int cSquares = 0;

		/* Bottom Left */
		if ((player.diskPositions & 0x8000000000000000L) == 0){
			if ((player.diskPositions & 0x4000000000000000L) != 0)
				cSquares++;
			if ((player.diskPositions & 0x0080000000000000L) != 0)
				cSquares++;
		}
		/* Bottom Right */
		if ((player.diskPositions & 0x0100000000000000L) == 0){
			if ((player.diskPositions & 0x0200000000000000L) != 0)
				cSquares++;
			if ((player.diskPositions & 0x0001000000000000L) != 0)
				cSquares++;
		}
		/* Top Left */
		if ((player.diskPositions & 0x0000000000000080L) == 0){
			if ((player.diskPositions & 0x0000000000008000L) != 0)
				cSquares++;
			if ((player.diskPositions & 0x0000000000000040L) != 0)
				cSquares++;
		}
		/* Top Right */
		if ((player.diskPositions & 0x0000000000000010L) == 0){
			if ((player.diskPositions & 0x0000000000000100L) != 0)
				cSquares++;
			if ((player.diskPositions & 0x0000000000000002L) != 0)
				cSquares++;
		}
		return cSquares;
	}
	
	/**
	 * Calculates the number of corners are occupied by a Disk of a certain Color.
	 * @param color		the Color of the Disks that we want to check the corners for.
	 * @return			the number of corners that are occupied by a Disk of a certain Color.
	 */
	public int cornersOwned(Color color){
		/* Get corresponding Player */
		Player player = null;
		if (color == Color.BLACK)
			player = blackPlayer;
		else
			player = whitePlayer;
		
		int corners = 0;
		
		if ((player.diskPositions & 0x8000000000000000L) != 0)
			corners++;
		if ((player.diskPositions & 0x0100000000000000L) != 0)
			corners++;
		if ((player.diskPositions & 0x0000000000000080L) != 0)
			corners++;
		if ((player.diskPositions & 0x0000000000000001L) != 0)
			corners++;
		return corners;
	}
	
	/**
	 * Enables printing of a Board to the console using System.out.println()
	 */
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		/* Append who owns which squares */
		for (byte row = (byte) (rows - 1); row >= 0; row--){	//Notice we loop backwards through rows.
			for (byte col = 0; col < columns; col++){
				Color color = getDiskColor(new Point(col, row));
				if (color == Color.BLACK)
					sb.append("B");
				else if (color == Color.WHITE)
					sb.append("W");
				else
					sb.append("-");
				sb.append("\t");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}
