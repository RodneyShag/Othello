package listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import main_components.Board;
import main_components.Color;
import main_components.CommandManager;
import main_components.Controller;
import main_components.View;

/**
 * \brief
 * Enables a Player to "undo" a move
 * @author Rodney Shaghoulian
 */
public class UndoListener extends MouseAdapter {
	public CommandManager commandManager;	///< Keeps track of undos and redos
	public Board board;						///< The Board we are playing Othello on.
	public View view;						///< The View of the Board				
	
	/**
	 * Constructor that saves CommandManager information
	 * @param controller	The Controller that has access to CommandManager
	 */
	public UndoListener(Controller controller){
		this.commandManager = controller.commandManager;
		this.board = controller.board;
		this.view = controller.view;
	}
	
	/**
	 * Undos a move if an undo is available. Beeps otherwise
	 */
	public void mouseClicked(MouseEvent event){
		if (commandManager.undoAvailable()){
			do{
				commandManager.undo();
				view.updateView();
			}while(board.playerTurn == Color.WHITE);
		}
		else
			java.awt.Toolkit.getDefaultToolkit().beep();
	}
}
