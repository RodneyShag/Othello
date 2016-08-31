package tests;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Test;

import main_components.Board;
import main_components.Color;
import main_components.Command;
import main_components.CommandManager;
import main_components.Controller;
import strategies.Difficulty;

/**
 * \brief
 * Tests the CommandManager for execute, undo, redo commands
 * @author Rodney Shaghoulian
 */
public class CommandManagerTest {

	/**
	 * Tests Command Manager. Places 2 DiskS on the board using the Command Manager. \n
	 * Tests "Undo" and "Redo" of these moves.
	 */
	@Test
	public void testCommandManager() {
		/* Set up data */
		Controller controller = new Controller(8, 8, Difficulty.EASY);
		Board board = controller.board;
		CommandManager commandManager = controller.commandManager;
		Command command = new Command(board, Color.BLACK, new Point(3, 5));
		Command command2 = new Command(board, Color.WHITE, new Point(2, 3));
		
		/* Test Undo, Redo stack sizes */
		assertEquals(commandManager.undos.size(), 0);
		assertEquals(commandManager.redos.size(), 0);
		
		/* Test executeCommand */
		commandManager.executeCommand(command);
		assertEquals(board.tile[5][3].color, Color.BLACK);
		assertEquals(commandManager.undos.size(), 1);
		assertEquals(commandManager.redos.size(), 0);
		
		/* Test Undo */
		commandManager.executeCommand(command2);
		commandManager.undo();
		commandManager.undo();
		assertEquals(board.tile[5][3].color, Color.NONE);
		assertEquals(commandManager.undos.size(), 0);
		assertEquals(commandManager.redos.size(), 2);
		
		/* Test Redo */
		commandManager.redo();
		commandManager.redo();
		assertEquals(board.tile[5][3].color, Color.BLACK);
		assertEquals(commandManager.undos.size(), 2);
		assertEquals(commandManager.redos.size(), 0);
	}
}
