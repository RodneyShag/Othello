package main_components;

import java.util.Stack;

/**
 * \brief
 * Keeps Commands in Stacks so that we can "redo" and "undo" moves
 * @author Rodney Shaghoulian
 */
public class CommandManager {
	public Stack<Command> undos = new Stack<Command>();	///< Commands saved in a Stack in the appropriate order to "undo"
	public Stack<Command> redos = new Stack<Command>();	///< Commands saved in a Stack in the appropriate order to "redo"
		
	/**
	 * Executes a Command on the current Board
	 * @param command	The Command to execute
	 */
	public void executeCommand(Command command) {
		command.execute();
		undos.push(command);
		redos.clear();
	}

	/**
	 * Checks to see if an "undo" is available
	 * @return		true if available. false otherwise
	 */
	public boolean undoAvailable() {
		return !undos.empty();
	}

	/**
	 * Undoes the last move (including the computer A.I.'s move)
	 */
	public void undo() {
		if (undoAvailable()){
			Command command = undos.pop();
			command.undo();
			redos.push(command);
		}
	}

	/**
	 * Checks to see if a "redo" is available
	 * @return		true if available. false otherwise
	 */
	public boolean redoAvailable() {
		return !redos.empty();
	}

	/**
	 * Redoes the last move
	 */
	public void redo() {
		if (redoAvailable()){
			Command command = redos.pop();
			command.execute();
			undos.push(command);
		}
	}
}
