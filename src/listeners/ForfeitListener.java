/** \brief The mouse listeners for our Othello Program */
package listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import main_components.Board;
import main_components.Color;
import main_components.Controller;
import strategies.Difficulty;

/**
 * \brief
 * Enables a player to forfeit a game.
 * @author Rodney Shaghoulian
 */
public class ForfeitListener extends MouseAdapter {
	public Controller controller;	///< The Controller in the MVC structure
	public Board board;				///< The Model in the MVC structure
	
	/**
	 * Constructor. Saves the Controller and Board information
	 * @param controller	The Controller in the MVC structure
	 */
	public ForfeitListener(Controller controller){
		this.controller = controller;
		this.board 		= controller.board;
	}
	
	/**
	 * Let's a Player instantly forfeit a game
	 * @param event		the event corresponding to the mouse click
	 */
	public void mouseClicked(MouseEvent event){
		if (board.playerTurn == Color.BLACK){
			javax.swing.JOptionPane.showMessageDialog(null, "Black Forfeits. White Wins!");
			controller.whiteGamesWon++;
		}
		else{
			javax.swing.JOptionPane.showMessageDialog(null, "White Forfeits. Black Wins!");
			controller.blackGamesWon++;
		}
		controller.reset(Difficulty.EASY);
	}
}
