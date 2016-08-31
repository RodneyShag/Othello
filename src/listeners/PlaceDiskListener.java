package listeners;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import main_components.Button;
import main_components.Color;
import main_components.Board;
import main_components.Command;
import main_components.CommandManager;
import main_components.Controller;
import main_components.View;

/**
 * \brief
 * Enables a player to place a Disk on a Board using their mouse
 * @author Rodney Shaghoulian
 */
public class PlaceDiskListener extends MouseAdapter {
	public Controller controller;				///< The Controller in the MVC structure
	public Board board;							///< The Model in the MVC structure
	public View view;							///< The View in the MVC structure
	public CommandManager commandManager;		///< Keeps track of Commands for "undos" and "redos"
	
	/*
	 * A constructor that saves Model, View, Controller in the MVC structure, along with the CommandManager
	 */
	public PlaceDiskListener(Controller controller){
		this.controller 	= controller;
		this.board 			= controller.board;
		this.view 			= controller.view;
		this.commandManager = controller.commandManager;
	}
	
	/**
	 * Let's us place a Disk on a Board with 1 mouse click.
	 * @param event		the event corresponding to the mouse click
	 */
	public void mouseClicked(MouseEvent event){
		if (board.playerTurn != Color.BLACK)
			return;
		Button currentButton = (Button) event.getSource();
		if (board.getCurrentPlayer().validMoves.contains(currentButton.createPoint())){
			Point destinationPoint = currentButton.createPoint();
			Command command = new Command(board, controller.board.playerTurn, destinationPoint);
			commandManager.executeCommand(command);
			
			view.enableButtons(false);
			view.updateView();
		    try{
		        SwingUtilities.invokeLater(new Runnable(){
		            public void run(){
		            	if (board.whitePlayer.validMoves.size() > 0)
		    				controller.computerTurn();
		            	view.enableButtons(true);
		            }
		        });
		    }
		    catch (final Throwable e){
		    	System.out.println("Invoke Later Failed");
		    }
		}
		else
			javax.swing.JOptionPane.showMessageDialog(null, "Illegal Move. Try again!");
	}
}
