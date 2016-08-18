package listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import main_components.Controller;
import strategies.Difficulty;

/**
 * \brief
 * Enables a Player to start a new game.
 * @author Rodney Shaghoulian
 */
public class NewGameListener extends MouseAdapter {
	public Controller controller;	///< The Controller in the MVC structure
	public Difficulty difficulty;	///< The difficulty level of the A.I.
	
	/**
	 * Constructor: Saves the Controller information
	 * @param controller	The Controller in the MVC structure
	 * @param difficulty	EASY, MEDIUM, or HARD mode for the computer A.I.
	 */
	public NewGameListener(Controller controller, Difficulty difficulty){
		this.controller	= controller;
		this.difficulty = difficulty;
	}
	
	/**
	 * Starts a new game
	 * @param event		the corresponding MouseEvent
	 */
	public void mouseClicked(MouseEvent event){
		javax.swing.JOptionPane.showMessageDialog(null, "Starting New Game");
		controller.reset(difficulty);
	}
}