package tests;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Test;

import main_components.Board;
import main_components.Color;
import main_components.Player;

/**
 * \brief
 * Tests the simple Player Class
 * @author Rodney Shaghoulian
 */
public class PlayerTest {

	/**
	 * This is a simple test to make sure the constructor works properly, meaning
 	 * 1) Not Null  2) Correct Fields
	 */
	@Test
	public void testConstructor() {
		/* Set up data */
		Board board = new Board(8, 8);
		Player whitePlayer = new Player(board, Color.WHITE, false);
		
		/* Test data was set up correctly */
		assertNotNull(whitePlayer);
		assertEquals(whitePlayer.color, Color.WHITE);
		assertFalse(whitePlayer.isComputer);
		assertEquals(whitePlayer.score, 2);
		assertEquals(whitePlayer.validMoves.size(), 4);

	}
	
	/**
	 * Tests the copy constructor. \n
	 * Creates a WHITE Player. Makes a copy of it using copy constructor. Verifies its data.
	 */
	@Test
	public void testCopyConstructor() {
		/* Set up data */
		Board board = new Board(8, 8);
		Player whitePlayer1 = new Player(board, Color.WHITE, false);
		Player whitePlayer2 = new Player(whitePlayer1);
		
		/* Test data was set up correctly */
		assertNotNull(whitePlayer2);
		assertEquals(whitePlayer2.color, Color.WHITE);
		assertFalse(whitePlayer2.isComputer);
		assertEquals(whitePlayer2.score, 2);
		assertEquals(whitePlayer2.validMoves.size(), 4);
	}
	
	/**
	 * Tests WHITE's valid moves: BLACK makes a move, and WHITE should only have 3 valid moves.
	 */
	@Test
	public void testUpdateValidMoves() {
		/* Set up data */
		Board board = new Board(8, 8);
		Player whitePlayer = new Player(board, Color.WHITE, false);
		
		/* Test only 3 valid moves remaining for WHITE */
		board.placeDisk(new Point(5, 3), Color.BLACK);
		whitePlayer.updateValidMoves(board);
		assertEquals(whitePlayer.validMoves.size(), 3);
	}
}
