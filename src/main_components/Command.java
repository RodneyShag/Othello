package main_components;

import java.awt.Point;
import java.util.ArrayList;

/**
 * \brief
 * A representation of a move on a Board
 * @author Rodney Shaghoulian
 */
public class Command {
	public Board board;						///< the current Board the game is being played on
	public Color color;						///< the Color of the new Disk
	public Point destination;				///< the destination of the new Disk
	public ArrayList<Point> capturedPoints; ///< the Points that were captured. We save it in case a user "undoes" a move
	
	/**
	 * A representation of a "Move" command.
	 * @param board			The Board we are placing a Disk on.
	 * @param position		The position we are placing the Disk.
	 */
	public Command(Board board, Color color, Point position){
		this.board  	 = board;
		this.color		 = color;
		this.destination = new Point(position);
		
		capturedPoints = new ArrayList<Point>();
	}
	
	/**
	 * Execute the current Command on the given Board
	 */
	public void execute() {
		/* Update Model */
		board.flipDisk(destination, color);
		capturedPoints = board.flipCaptures(destination, color);
		board.updateBoard();
	}
	
	/**
	 * Undo the current Command
	 */
	public void undo(){
		/* Update Model */
		Color oppositeColor = board.getOppositeColor(color);
		board.removeDisk(destination);
		for (Point point : capturedPoints){
			board.flipDisk(point, oppositeColor);
		}
		board.updateBoard();
	}
}
