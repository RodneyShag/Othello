package tests;

import static org.junit.Assert.*;
import org.junit.Test;

import main_components.Color;
import main_components.Controller;
import strategies.Difficulty;

/**
 * \brief Tests the Controller class for initialization
 * @author Rodney Shaghoulian
 */
public class ControllerTest {

	/**
	 * Tests the Constructor for initial variables
	 */
	@Test
	public void testConstructor() {
		/* Set up data */
		Controller controller = new Controller(8, 8, Difficulty.EASY);
		assertNotNull(controller.view);
		assertNotNull(controller.board);
		assertNotNull(controller.commandManager);
		assertEquals(controller.whiteGamesWon, 0);
		assertEquals(controller.blackGamesWon, 0);
		assertEquals(controller.rows, 8);
		assertEquals(controller.columns, 8);
	}

	/**
	 * Tests reseting the Controller and still having MVC structure intact
	 */
	@Test
	public void testReset() {
		/* Set up data */
		Controller controller = new Controller(8, 8, Difficulty.EASY);
		controller.reset(Difficulty.EASY);
		assertNotNull(controller.view);
		assertNotNull(controller.board);
		assertNotNull(controller.commandManager);
		assertEquals(controller.whiteGamesWon, 0);
		assertEquals(controller.blackGamesWon, 0);
		assertEquals(controller.rows, 8);
		assertEquals(controller.columns, 8);
	}

	/**
	 * Tests all variable initializations
	 */
	@Test
	public void testInitializeVariables() {
		/* Set up data */
		Controller controller = new Controller(8, 8, Difficulty.EASY);
		assertNotNull(controller.view);
		assertNotNull(controller.board);
		assertNotNull(controller.commandManager);
		assertEquals(controller.whiteGamesWon, 0);
		assertEquals(controller.blackGamesWon, 0);
		assertEquals(controller.rows, 8);
		assertEquals(controller.columns, 8);
	}
	
	/**
	 * Tests that computer turn is on correct difficulty
	 */
	@Test
	public void testComputerTurn(){
		Controller controller = new Controller(8, 8, Difficulty.EASY);
		assertEquals(controller.difficulty, Difficulty.EASY);
	}
	
	/**
	 * Tests that computer A.I. does not make a move on wrong turn.
	 */
	@Test
	public void testComputerEasy(){
		Controller controller = new Controller(8, 8, Difficulty.EASY);
		controller.computerEasy();
		assertEquals(controller.board.playerTurn, Color.BLACK);
	}
	
	/**
	 * Tests that computer A.I. does not make a move on wrong turn.
	 */
	@Test
	public void testComputerMedium(){
		Controller controller = new Controller(8, 8, Difficulty.MEDIUM);
		controller.computerEasy();
		assertEquals(controller.board.playerTurn, Color.BLACK);
	}
}
