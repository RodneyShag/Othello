package tests;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Test;

import main_components.Board;
import piece_properties.Color;
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
	 * Tests the utility value at the beginning of the game for difference if # of valid moves for Players \n
	 * and corners owned.
	 */
	@Test
	public void testUtilityValidMovesAndCorners() {
		/* Set up data */
		Board board = new Board(8, 8);
		Utility utility = new Utility(board);
		
		/* Test utility at beginning of game */
		utility.utilityValidMovesAndCorners();
		assertEquals(utility.value, 0);
		
		/* Put a BLACK Disk in corner and test utility again */
		board.placeDisk(new Point(0,0), Color.BLACK);
		utility.utilityCorners();
		assertEquals(utility.value, 1);
	}
	
	/**
	 * Tests the utility value at the beginning of the game for difference if # of valid moves for Players
	 */
	@Test
	public void testUtilityValidMovesAndCSquares() {
		/* Set up data */
		Board board = new Board(8, 8);
		Utility utility = new Utility(board);
		
		/* Test utility at beginning of game */
		utility.utilityValidMovesAndCSquares();
		assertEquals(utility.value, 0);
		
		/* Put a BLACK Disk in 'C square' and test utility again */
		board.placeDisk(new Point(0,1), Color.BLACK);
		utility.utilityValidMovesAndCSquares();
		assertEquals(utility.value, -5);
	}
	
	/**
	 * Tests the utility value at the beginning of the game for difference if # of valid moves for Players
	 */
	@Test
	public void testUtilityValidMovesAndXSquares() {
		/* Set up data */
		Board board = new Board(8, 8);
		Utility utility = new Utility(board);
		
		/* Test utility at beginning of game */
		utility.utilityValidMovesAndXSquares();
		assertEquals(utility.value, 0);
		
		/* Put a BLACK Disk in 'X square' and test utility again */
		board.placeDisk(new Point(1,1), Color.BLACK);
		utility.utilityValidMovesAndXSquares();
		assertEquals(utility.value, -20);
	}
	
	/**
	 * Tests the utility value at the beginning of the game for difference if # of valid moves for Players
	 */
	@Test
	public void testUtilityComplex() {
		/* Set up data */
		Board board = new Board(8, 8);
		Utility utility = new Utility(board);
		
		/* Test utility at beginning of game */
		utility.utilityComplex();
		assertEquals(utility.value, 0);
		
		/* Put a WHITE Disk in each of 'X square', 'C square', and corner and test utility again */
		board.placeDisk(new Point(0,0), Color.WHITE);
		board.placeDisk(new Point(0,1), Color.WHITE); // should be good for WHITE since WHITE owns corner
		board.placeDisk(new Point(1,1), Color.WHITE); // should be good for WHITE since WHITE owns corner
		utility.utilityComplex();
		assertEquals(utility.value, -50);
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
