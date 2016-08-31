package tests;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Test;

import main_components.Board;
import main_components.Color;
import main_components.Command;
import main_components.Controller;
import strategies.Difficulty;

/**
 * \brief
 * Tests the Command class for executing and undoing Commands
 * @author Rodney Shaghoulian
 */
public class CommandTest {

	/**
	 * Tests Constructor for valid data
	 */
	@Test
	public void testConstructor() {
		/* Set up data */
		Controller controller = new Controller(8, 8, Difficulty.EASY);
		Board board = controller.board;
		Command command = new Command(board, Color.BLACK, new Point(2, 2));
		
		/* Test data */
		assertNotNull(command.board);
		assertNotNull(command.color);
		assertEquals(command.destination, new Point(2, 2));
		assertNotNull(command.capturedPoints);
	}
	
	/**
	 * Tests executing a Command
	 */
	@Test
	public void testExecute() {
		/* Set up data */
		Controller controller = new Controller(8, 8, Difficulty.EASY);
		Board board = controller.board;
		Command command = new Command(board, Color.BLACK, new Point(3, 5));
		
		/* Test execute */
		command.execute();
		assertEquals(board.tile[5][3].color, Color.BLACK);
	}
	
	/**
	 * Tests undoing a Command
	 */
	@Test
	public void testUndo() {
		/* Set up data */
		Controller controller = new Controller(8, 8, Difficulty.EASY);
		Board board = controller.board;
		Command command = new Command(board, Color.BLACK, new Point(3, 5));
		
		/* Test Undo */
		command.execute();
		command.undo();
		assertEquals(board.tile[3][5].color, Color.NONE);
	}
}
