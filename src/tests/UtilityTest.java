package tests;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Test;

import main_components.Board;
import main_components.Color;
import strategies.Utility;

/**
 * \brief
 * Tests the Utility class
 * @author Rodney Shaghoulian
 */
public class UtilityTest {

	/**
	 * Tests constructor has appropriate variables.
	 */
	@Test
	public void testUtility() {
		/* Set up data */
		Board board = new Board(8, 8);
		Utility utility = new Utility(board);
		
		/* Test data was set up correctly */
		assertNotNull(utility.board);
		assertEquals(utility.value, 0);
	}

	/**
	 * Tests the utility value at the beginning of the game for score differentials
	 */
	@Test
	public void testUtilityScoreDiff() {
		/* Set up data */
		Board board = new Board(8, 8);
		Utility utility = new Utility(board);
		
		/* Test utility at beginning of game */
		utility.utilityScoreDiff();
		assertEquals(utility.value, 0);
	}

	/**
	 * Tests the utility value at the beginning of the game for number of corners owned
	 */
	@Test
	public void testUtilityCorners() {
		/* Set up data */
		Board board = new Board(8, 8);
		Utility utility = new Utility(board);
		
		/* Test utility at beginning of game */
		utility.utilityCorners();
		assertEquals(utility.value, 0);
		
		/* Put a Disk in corner and test utility again */
		board.placeDisk(new Point(0,0), Color.WHITE);
		utility.utilityCorners();
		assertEquals(utility.value, -1);
	}

	/**
	 * Tests the utility value at the beginning of the game for difference if # of valid moves for Players
	 */
	@Test
	public void testUtilityValidMoves() {
		/* Set up data */
		Board board = new Board(8, 8);
		Utility utility = new Utility(board);
		
		/* Test utility at beginning of game */
		utility.utilityValidMoves();
		assertEquals(utility.value, 0);
	}
	
	/**
	 * Tests game ended utility to be proper value
	 */
	@Test
	public void testGameEndedUtility() {
		/* Set up data */
		Board board = new Board(8, 8);
		Utility utility = new Utility(board);
		
		/* Test utility at beginning of game */
		board.gameEnded = true;
		board.winner = Color.BLACK;
		utility.gameEndedUtility();
		assertEquals(utility.value, 10000);

	}
}
