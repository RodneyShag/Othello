package main_components;

import java.awt.Point;

import main_components.Color;

/**
 * \brief
 * A representation of a move on a Board
 * @author Rodney Shaghoulian
 */
public class Command {
	public Board board;			///< the current Board the game is being played on
	public Color color;			///< the Color of the new Disk
	public Point destination;	///< the destination of the new Disk
	public long captures; 		///< the Disks that were captured. We save it in case a user "undoes" a move
	
	Player player;
	Player opponent;
	
	/**
	 * A representation of a "Move" command.
	 * @param board			The Board we are placing a Disk on.
	 * @param destination	The position we are placing the Disk.
	 */
	public Command(Board board, Color color, Point destination){
		this.board  	 = board;
		this.color		 = color;
		this.destination = new Point(destination);
		captures = board.getCaptures(destination, color);
		player   = board.getPlayerFromColor(color);
		opponent = board.getOpponentOfColor(color);
	}
	
	/**
	 * Execute the current Command on the given Board
	 */
	public void execute() {
		board.setDiskColor(destination, color);
		player.diskPositions |= captures;
		opponent.diskPositions &= ~captures;
		board.updateBoard();
	}
	
	/**
	 * Undo the current Command
	 */
	public void undo(){
		board.setDiskColor(destination, Color.NONE);
		player.diskPositions &= ~captures; // A \ B,  where A, B are sets
		opponent.diskPositions |= captures;
		board.updateBoard();
	}
}
