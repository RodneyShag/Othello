/** \brief The main classes of our Othello Program */
package main_components;
import java.awt.Point;
import java.util.ArrayList;

/**
 * \brief
 * This is where the 2-player game of Othello will be played.
 * @author Rodney Shaghoulian
 */
public class Board {
	/* Essential Info */
	public final int rows;		///< Number of rows on Board.
	public final int columns;	///< Number of columns on Board.
	public Disk[][] tile;		///< Board is a 2-dimensional array of Disks.
	
	/* Players */
	public Player blackPlayer;	///< Player 1 on Board. Uses BLACK Disks.
	public Player whitePlayer;	///< Player 2 on Board. Uses WHITE Disks.
	
	/* Statuses */
	public Color playerTurn;	///< Either WHITE's turn or BLACK's turn
	public boolean gameEnded;	///< True if game is over. False otherwise.
	public Color winner;		///< Either Color.BLACK or Color.WHITE when not null.
	public int turn;			///< # of moves that have happened. Value from 1 to 60.
	
	/**
	 * Constructor - Initializes a Board given number of rows and columns (usually 8x8)
	 * @param rows		Number of rows on Othello Board (usually 8)
	 * @param columns	Number of columns on Othello Board (usually 8)
	 */
	public Board(int rows, int columns){
		/* Initialize Essential Info */
		this.rows = rows;
		this.columns = columns;
		tile = new Disk[rows][columns];
		
		initializeBoard();
		
		/* Initialize Players */
		blackPlayer = new Player(this, Color.BLACK, false);
		whitePlayer = new Player(this, Color.WHITE, false);
		
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
		tile = new Disk[rows][columns];
		for (int row = 0; row < rows; row++){
			for (int col = 0; col < columns; col++){
				tile[row][col] = new Disk(otherBoard.tile[row][col]);
			}
		}
		
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
	 * Initializes the Board by creating Disks with Color.NONE for empty spots. \n
	 * Puts 2 BLACK Disks and 2 WHITE Disks in center of Board. 
	 */
	public void initializeBoard(){
		for (int row = 0; row < rows; row++){
			for (int col = 0; col < columns; col++){
				tile[row][col] = new Disk(Color.NONE);
			}
		}
		
		int centerRoundDown = rows / 2 - 1;
		int centerRoundUp = rows / 2;
		/* Place 2 Black Disks in center of Board */
		tile[centerRoundDown][centerRoundDown].changeColor(Color.BLACK);
		tile[centerRoundUp][centerRoundUp].changeColor(Color.BLACK);
		
		/* Place 2 White Disks in center of Board */
		tile[centerRoundDown][centerRoundUp].changeColor(Color.WHITE);
		tile[centerRoundUp][centerRoundDown].changeColor(Color.WHITE);
	}
	
	/**
	 * Determines if a position exists on a Board
	 * @param pos	The position we are checking x and y coordinates for
	 * @return		true/false depending on whether position is valid on Board
	 */
	public boolean validPosition(Point pos){
		return (pos.x >= 0 && pos.y >= 0 && pos.x < columns && pos.y < rows);
	}
	
	/**
	 * Determines the Color (WHITE, BLACK, NONE) of the Disk at a position on the Board
	 * @param pos	The position that a Disk may reside
	 * @return		The color of the Disk (WHITE, BLACK, NONE)
	 */
	public Color diskColor(Point pos){
		return tile[pos.y][pos.x].color;
	}
	
	/**
	 * Gets opposite color. BLACK and WHITE are opposites
	 * @param color	the color to get the opposite of.
	 * @return		BLACK if parameter is WHITE. WHITE if parameter is BLACK.
	 */
	public Color getOppositeColor(Color color){
		if (color == Color.BLACK)
			return Color.WHITE;
		else if (color == Color.WHITE)
			return Color.BLACK;
		return null;
	}
	
	/**
	 * Toggles playerTurn from WHITE to BLACK, and vice versa
	 */
	public void updateTurn(){
		if (playerTurn == Color.WHITE && blackPlayer.validMoves.size() > 0)
			playerTurn = Color.BLACK;
		else if (playerTurn == Color.BLACK && whitePlayer.validMoves.size() > 0)
			playerTurn = Color.WHITE;
		turn = blackPlayer.score + whitePlayer.score - 3;
	}
	
	/**
	 * Determines if game is over. Updates "gameEnded" and "winner" if necessary.
	 */
	public void updateGameStatus(){
		if (blackPlayer.validMoves.isEmpty() && whitePlayer.validMoves.isEmpty()){
			gameEnded = true;
			if (blackPlayer.score > whitePlayer.score)
				winner = Color.BLACK;
			else if (whitePlayer.score > blackPlayer.score)
				winner = Color.WHITE;
			else
				winner = Color.NONE;
		}
	}
	
	/**
	 * Updates Board: by updating valid moves, turn, and game status
	 */
	public void updateBoard(){
		blackPlayer.updateValidMoves(this);
		whitePlayer.updateValidMoves(this);
		updateTurn();
		updateGameStatus();
	}
	
	/**
	 * Determines if a move is valid
	 * @param pos	the desired position to place a Disk.
	 * @param color	the color of the Disk being placed.
	 * @return		true if move is valid. false otherwise.
	 */
	public boolean validMove(Point pos, Color color){
		if (pos == null || color == null || !validPosition(pos) || color == Color.NONE || tile[pos.y][pos.x].color != Color.NONE)
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
		Color opponentColor = getOppositeColor(color);
		
		/* For valid move: immediate neighbor should be opposing Color */
		pos.x += xDirection;
		pos.y += yDirection;
		if (!validPosition(pos) || diskColor(pos) != opponentColor)
			return false;
		
		/* For valid move: there should be a Disk of our own Color at the end */
		pos.x += xDirection;
		pos.y += yDirection;
		while(validPosition(pos)){
			if (diskColor(pos) == color)
				return true;
			else if (diskColor(pos) == Color.NONE)
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
		Color oldColor = tile[pos.y][pos.x].color;
		tile[pos.y][pos.x].changeColor(Color.NONE);
		
		/* Subtract from scores */
		if (oldColor == Color.BLACK)
			blackPlayer.score--;
		else if (oldColor == Color.WHITE)
			whitePlayer.score--;
	}
	
	/**
	 * Flips a Disk by changing it's color 
	 * @param pos		The position of the Disk.
	 * @param newColor	The color the Disk should be changed to.
	 */
	public void flipDisk(Point pos, Color newColor){
		Color oldColor = tile[pos.y][pos.x].color; // oldColor could be "NONE"
		tile[pos.y][pos.x].changeColor(newColor);
		
		/* Subtract from scores */
		if (oldColor == Color.BLACK)
			blackPlayer.score--;
		else if (oldColor == Color.WHITE)
			whitePlayer.score--;
		
		/* Add to scores */
		if (newColor == Color.BLACK)
			blackPlayer.score++;
		else if (newColor == Color.WHITE)
			whitePlayer.score++;
	}
	
	/**
	 * Flips (captures) opponent's disks
	 * @param pos	The position of the Disk that was placed.
	 * @param color	The color of the Disk.
	 * @return		The Points that were flipped
	 */
	public ArrayList<Point> flipCaptures(Point pos, Color color){
		ArrayList<Point> disksFlipped = new ArrayList<Point>();
		ArrayList<Point> disksFlippedInDirection = new ArrayList<Point>();
		
		if (!validPosition(pos))
			return disksFlipped;
		disksFlippedInDirection = flipInDirection(pos, color,  1,  0);	// Right
		disksFlipped.addAll(disksFlippedInDirection);
		disksFlippedInDirection = flipInDirection(pos, color, -1,  0);	// Left
		disksFlipped.addAll(disksFlippedInDirection);
		disksFlippedInDirection = flipInDirection(pos, color,  0,  1);	// Up
		disksFlipped.addAll(disksFlippedInDirection);
		disksFlippedInDirection = flipInDirection(pos, color,  0, -1);	// Down
		disksFlipped.addAll(disksFlippedInDirection);
		disksFlippedInDirection = flipInDirection(pos, color,  1,  1);	// NorthEast
		disksFlipped.addAll(disksFlippedInDirection);
		disksFlippedInDirection = flipInDirection(pos, color,  1, -1);	// NorthWest
		disksFlipped.addAll(disksFlippedInDirection);
		disksFlippedInDirection = flipInDirection(pos, color, -1,  1);	// SouthEast
		disksFlipped.addAll(disksFlippedInDirection);
		disksFlippedInDirection = flipInDirection(pos, color, -1, -1);	// SouthWest
		disksFlipped.addAll(disksFlippedInDirection);
		return disksFlipped;
	}
	
	/**
	 * Flip disks in a certain direction
	 * @param pos			the position the new Disk that was placed.
	 * @param color			the color of the Disk that was just placed.
	 * @param xDirection	the x Direction to flip Disks (-1, 0, 1).
	 * @param yDirection	the y Direction to flip Disks (-1, 0, 1).
	 * @return				the Points that were flipped
	 */
	public ArrayList<Point> flipInDirection(Point position, Color color, int xDirection, int yDirection){
		ArrayList<Point> disksFlipped = new ArrayList<Point>();
		Point pos = new Point(position);
		if (!validInDirection(pos, color, xDirection, yDirection))
			return disksFlipped;
		Color opponentColor = getOppositeColor(color);
		pos.x += xDirection;
		pos.y += yDirection;
		while (validPosition(pos) && diskColor(pos) == opponentColor){
			flipDisk(pos, color);
			disksFlipped.add(new Point(pos));
			pos.x += xDirection;
			pos.y += yDirection;
		}
		return disksFlipped;
	}
	
	/**
	 * Places a Disk on a Board. Captures opponents Disks. \n
	 * Assumption: Function assumes it's a valid move.
	 * @param pos	The position where the Disk will be placed.
	 * @param color	The color of the Disk.
	 */
	public void placeDisk(Point pos, Color color){
		flipDisk(pos, color);
		flipCaptures(pos, color);
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
	 * Calculates the number of 'X squares' that are occupied by a Disk of a certain Color. \n
	 * which are not next to a corner owned by that color.
	 * @param color		the Color of the Disks that we want to check the corners for.
	 * @return			the number of 'X squares' that are occupied by a Disk of a certain Color.
	 */
	public int xSquaresOwned(Color color){
		int badSpots = 0;
		if (diskColor(new Point(1, 1)) == color)
			badSpots++;
		if (diskColor(new Point(1, rows - 2)) == color)
			badSpots++;
		if (diskColor(new Point(columns - 2, 1)) == color)
			badSpots++;
		if (diskColor(new Point(columns - 2, rows - 2)) == color)
			badSpots++;
		return badSpots;
	}
	
	/**
	 * Calculates the number of bad 'X squares' that are occupied by a Disk of a certain Color. \n
	 * which are not next to an occupied corner
	 * @param color		the Color of the Disks that we want to check the corners for.
	 * @return			the number of lone 'X squares' that are occupied by a Disk of a certain Color.
	 */
	public int badXSquaresOwned(Color color){
		int badSpots = 0;
		if ((diskColor(new Point(1, 1)) == color) && (diskColor(new Point(0, 0)) == Color.NONE))
			badSpots++;
		if ((diskColor(new Point(1, rows - 2)) == color) && (diskColor(new Point(0, rows - 1)) == Color.NONE))
			badSpots++;
		if ((diskColor(new Point(columns - 2, 1)) == color) && (diskColor(new Point(columns - 1, 0)) == Color.NONE))
			badSpots++;
		if ((diskColor(new Point(columns - 2, rows - 2)) == color) && (diskColor(new Point(columns - 1, rows - 1)) == Color.NONE))
			badSpots++;
		return badSpots;
	}
	
	/**
	 * Calculates the number of 'C squares' are occupied by a Disk of a certain Color,
	 * @param color		the Color of the Disks that we want to check the C squares for.
	 * @return			the number of 'C squares' that are occupied by a Disk of a certain Color.
	 */
	public int cSquaresOwned(Color color){
		int cSquares = 0;
		
		if (diskColor(new Point(0, 1)) == color)
			cSquares++;
		if (diskColor(new Point(1, 0)) == color)
			cSquares++;
		
		if (diskColor(new Point(0, rows - 2)) == color)
			cSquares++;
		if (diskColor(new Point(1, rows - 1)) == color)
			cSquares++;
		
		if (diskColor(new Point(columns - 2, 0)) == color)
			cSquares++;
		if (diskColor(new Point(columns - 1, 1)) == color)
			cSquares++;
		
		if (diskColor(new Point(columns - 1, rows - 2)) == color)
			cSquares++;
		if (diskColor(new Point(columns - 2, rows - 1)) == color)
			cSquares++;
		
		return cSquares;
	}
	
	/**
	 * Calculates the number of bad 'C squares' are occupied by a Disk of a certain Color, \n
	 * which are not next to an occupied corner
	 * @param color		the Color of the Disks that we want to check the C squares for.
	 * @return			the number of lone 'C squares' that are occupied by a Disk of a certain Color.
	 */
	public int badCSquaresOwned(Color color){
		int cSquares = 0;
		
		if ((diskColor(new Point(0, 1)) == color) && (diskColor(new Point(0, 0)) == Color.NONE))
			cSquares++;
		if ((diskColor(new Point(1, 0)) == color) && (diskColor(new Point(0, 0)) == Color.NONE))
			cSquares++;
		
		if ((diskColor(new Point(0, rows - 2)) == color) && (diskColor(new Point(0, rows - 1)) == Color.NONE))
			cSquares++;
		if ((diskColor(new Point(1, rows - 1)) == color) && (diskColor(new Point(0, rows - 1)) == Color.NONE))
			cSquares++;
		
		if ((diskColor(new Point(columns - 2, 0)) == color) && (diskColor(new Point(columns - 1, 0)) == Color.NONE))
			cSquares++;
		if ((diskColor(new Point(columns - 1, 1)) == color) && (diskColor(new Point(columns - 1, 0)) == Color.NONE))
			cSquares++;
		
		if ((diskColor(new Point(columns - 1, rows - 2)) == color) && (diskColor(new Point(columns - 1, rows - 1)) == Color.NONE))
			cSquares++;
		if ((diskColor(new Point(columns - 2, rows - 1)) == color) && (diskColor(new Point(columns - 1, rows - 1)) == Color.NONE))
			cSquares++;
		
		return cSquares;
	}
	
	/**
	 * Calculates the number of corners are occupied by a Disk of a certain Color.
	 * @param color		the Color of the Disks that we want to check the corners for.
	 * @return			the number of corners that are occupied by a Disk of a certain Color.
	 */
	public int cornersOwned(Color color){
		int corners = 0;
		if (diskColor(new Point(0, 0)) == color)
			corners++;
		if (diskColor(new Point(0, rows - 1)) == color)
			corners++;
		if (diskColor(new Point(columns - 1, 0)) == color)
			corners++;
		if (diskColor(new Point(columns - 1, rows - 1)) == color)
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
		for (int row = rows - 1; row >= 0; row--){		//Notice we loop backwards through rows.
			for (int col = 0; col < columns; col++){
				if (tile[row][col].color == Color.BLACK)
					sb.append("B");
				else if (tile[row][col].color == Color.WHITE)
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
