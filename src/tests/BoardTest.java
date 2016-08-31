package tests;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Test;

import main_components.Board;
import main_components.Color;
import main_components.Player;

/**
 * \brief
 * Tests the Board class thoroughly
 * @author Rodney Shaghoulian
 */
public class BoardTest {

	/**
	 * Tests the Board Constructor by verifying data is created correctly.
	 */
	@Test
	public void testBoardConstructor() {
		/* Set up Board */
		Board board = new Board(8, 8);
		
		/* Test data */
		assertEquals(board.rows, 8);
		assertEquals(board.columns, 8);
		assertNotNull(board.tile);
		assertEquals(board.playerTurn, Color.BLACK);
		assertFalse(board.gameEnded);
		assertNull(board.winner);
		assertNotNull(board.blackPlayer);
		assertNotNull(board.whitePlayer);
	}

	/**
	 * Tests Board's Copy Constructor: Creates a Board, copies it, and verifies data is correct.
	 */
	@Test
	public void testBoardCopyConstructor() {
		/* Set up Boards */
		Board board1 = new Board(8, 8);
		Board board2 = new Board(board1);
		
		/* Test data */
		assertEquals(board2.rows, 8);
		assertEquals(board2.columns, 8);
		assertNotNull(board2.tile);
		assertEquals(board2.playerTurn, Color.BLACK);
		assertFalse(board2.gameEnded);
		assertNull(board2.winner);
		assertNotNull(board2.blackPlayer);
		assertNotNull(board2.whitePlayer);
	}

	/**
	 * Tests Board initialization. Tests 2 BLACK and 2 WHITE Disks in center
	 */
	@Test
	public void testInitializeBoard() {
		/* Set up Board */
		Board board = new Board(8, 8);
		
		/* Test data */
		assertEquals(board.tile[3][3].color, Color.BLACK);
		assertEquals(board.tile[4][4].color, Color.BLACK);
		assertEquals(board.tile[3][4].color, Color.WHITE);
		assertEquals(board.tile[4][3].color, Color.WHITE);
	}

	/**
	 * Tests out of range positions on Board (should be false). Also tests true condition.
	 */
	@Test
	public void testValidPosition() {
		/* Set up Board */
		Board board = new Board(8, 8);
		
		/* Test data */
		assertFalse(board.validPosition(new Point(0, 8)));
		assertFalse(board.validPosition(new Point(-1, -1)));
		assertTrue(board.validPosition(new Point(0, 0)));
		assertTrue(board.validPosition(new Point(7, 7)));
	}

	/**
	 * Tests Disk Color of 4 center Disks.
	 */
	@Test
	public void testDiskColor() {
		/* Set up Board */
		Board board = new Board(8, 8);
		
		/* Test data */
		Color color = board.diskColor(new Point(3,3));
		assertEquals(color, Color.BLACK);
		color = board.diskColor(new Point(3,4));
		assertEquals(color, Color.WHITE);
		color = board.diskColor(new Point(4,3));
		assertEquals(color, Color.WHITE);
		color = board.diskColor(new Point(4,4));
		assertEquals(color, Color.BLACK);
		
	}

	/**
	 * Tests that opponents color is opposite of our color.
	 */
	@Test
	public void testGetOpponentColor() {
		/* Set up Board */
		Board board = new Board(8, 8);
		
		/* Test data */
		assertEquals(board.getOppositeColor(Color.BLACK), Color.WHITE);
		assertEquals(board.getOppositeColor(Color.WHITE), Color.BLACK);
	}
	
	/**
	 * Tests that turns alternate. BLACK should get 1st turn. WHITE Player should get the 2nd turn.
	 */
	@Test
	public void testUpdateTurn() {
		/* Set up Board */
		Board board = new Board(8, 8);
		
		/* Test data */
		assertEquals(board.playerTurn, Color.BLACK);
		board.updateTurn();
		assertEquals(board.playerTurn, Color.WHITE);
	}

	/**
	 * Tests gameEnded at beginning of game. Then ends game, and tests it again.
	 */
	@Test
	public void testUpdateGameStatus() {
		/* Set up Board */
		Board board = new Board(8, 8);
		
		/* Test gameEnded at beginning of game */
		assertFalse(board.gameEnded);
		
		/* Simulate an ended game, and test gameEnded */
		board.blackPlayer.validMoves.clear();
		board.whitePlayer.validMoves.clear();
		board.updateGameStatus();
		assertTrue(board.gameEnded);
	}

	/**
	 * Place 2 Disks for BLACK to completely capture all of WHITE's Disks. Test that game ended
	 */
	@Test
	public void testUpdateBoard() {
		/* Set up Board */
		Board board = new Board(8, 8);
		
		/* Test data */
		board.placeDisk(new Point(5, 3), Color.BLACK);
		board.placeDisk(new Point(3, 5), Color.BLACK); // placeDisk automatically calls updateBoard().
		assertEquals(board.blackPlayer.validMoves.size(), 0);
		assertEquals(board.whitePlayer.validMoves.size(), 0);
		assertEquals(board.blackPlayer.score, 6);
		assertEquals(board.whitePlayer.score, 0);
		assertTrue(board.gameEnded);
	}

	/**
	 * Tests 4 valid moves for BLACK, 4 valid moves for WHITE. \n
	 * Also tests 2 invalid moves for BLACK, and 2 invalid moves for WHITE.
	 */
	@Test
	public void testValidMove() {
		/* Set up Board */
		Board board = new Board(8, 8);
		
		/* Test 4 valid moves for BLACK */
		assertTrue(board.validMove(new Point(5, 3), Color.BLACK));
		assertTrue(board.validMove(new Point(4, 2), Color.BLACK));
		assertTrue(board.validMove(new Point(3, 5), Color.BLACK));
		assertTrue(board.validMove(new Point(2, 4), Color.BLACK));
		
		/* Test 4 valid moves for WHITE */
		assertTrue(board.validMove(new Point(2, 3), Color.WHITE));
		assertTrue(board.validMove(new Point(3, 2), Color.WHITE));
		assertTrue(board.validMove(new Point(4, 5), Color.WHITE));
		assertTrue(board.validMove(new Point(5, 4), Color.WHITE));
		
		/* Test 2 invalid moves for BLACK */
		assertFalse(board.validMove(new Point(3, 4), Color.BLACK));
		assertFalse(board.validMove(new Point(4, 3), Color.BLACK));
		
		/* Test 2 invalid moves for BLACK */
		assertFalse(board.validMove(new Point(2, 2), Color.WHITE));
		assertFalse(board.validMove(new Point(4, 6), Color.WHITE));
	}

	/**
	 * Tests all 8 directions for placing a WHITE Disk at (2,3)
	 */
	@Test
	public void testValidInDirection() {
		/* Set up Board */
		Board board = new Board(8, 8);
		
		/* Test Invalid in 7 directions for placing (2,3) WHITE Disk */
		assertFalse(board.validInDirection(new Point(2,3), Color.WHITE, -1, -1));
		assertFalse(board.validInDirection(new Point(2,3), Color.WHITE, -1, 0));
		assertFalse(board.validInDirection(new Point(2,3), Color.WHITE, -1, 1));
		assertFalse(board.validInDirection(new Point(2,3), Color.WHITE, 0, -1));
		assertFalse(board.validInDirection(new Point(2,3), Color.WHITE, 0, 1));
		assertFalse(board.validInDirection(new Point(2,3), Color.WHITE, 1, -1));
		assertFalse(board.validInDirection(new Point(2,3), Color.WHITE, 1, 1));
		
		/* Test Valid in the right (east) direction for placing (2,3) WHITE Disk */
		assertTrue(board.validInDirection(new Point(2,3), Color.WHITE, 1, 0));
		
	}
	
	/** 
	 * Tests removing a Disk from a Board
	 */
	@Test
	public void testRemoveDisk() {
		/* Set up Board */
		Board board = new Board(8, 8);
		
		/* Remove Disk. Check it's removed */
		board.removeDisk(new Point(3, 3));
		assertEquals(board.tile[3][3].color, Color.NONE);
	}
	
	/**
	 * 1) Flips a Black Disk. Makes sure color of Disk changes, and scores are correctly adjusted. \n
	 * 2) Flips an "empty" Disk. Makes sure color of Disk changes, and scores are correctly adjusted.
	 */
	@Test
	public void testFlipDisk() {
		/* Set up Board */
		Board board = new Board(8, 8);
		
		/* Flip Black Disk. Test Data */
		board.flipDisk(new Point(3, 3), Color.WHITE);
		assertEquals(board.tile[3][3].color, Color.WHITE);
		assertEquals(board.whitePlayer.score, 3);
		assertEquals(board.blackPlayer.score, 1);
		
		/* Flip "empty" Disk. Test Data */
		board.flipDisk(new Point(2, 2), Color.WHITE);
		assertEquals(board.tile[2][2].color, Color.WHITE);
		assertEquals(board.whitePlayer.score, 4);
		assertEquals(board.blackPlayer.score, 1);
	}

	/**
	 * Tests flipping the (4,3) tile to BLACK and then to WHITE, using valid moves
	 */
	@Test
	public void testFlipTiles() {
		/* Set up Board */
		Board board = new Board(8, 8);
		
		/* Test Data */
		board.flipDisk(new Point(5, 3), Color.BLACK);
		board.flipCaptures(new Point(5, 3), Color.BLACK);
		assertEquals(board.diskColor(new Point(4, 3)), Color.BLACK);
		board.flipCaptures(new Point(5, 2), Color.WHITE);
		assertEquals(board.diskColor(new Point(4, 3)), Color.WHITE);
		
	}

	/**
	 * Tests flipping the (4,3) tile to BLACK and then to WHITE, by using left direction
	 */
	@Test
	public void testFlipInDirection() {
		/* Set up Board */
		Board board = new Board(8, 8);
		
		/* Test Data */
		board.flipDisk(new Point(5, 3), Color.BLACK);
		board.flipInDirection(new Point(5, 3), Color.BLACK, -1, 0);
		assertEquals(board.diskColor(new Point(4, 3)), Color.BLACK);
		board.flipInDirection(new Point(5, 2), Color.WHITE, -1, 1);
		assertEquals(board.diskColor(new Point(4, 3)), Color.WHITE);
	}

	/**
	 * Tests placing a BLACK Disk on Board, and capturing WHITE Disk.
	 */
	@Test
	public void testPlaceDisk() {
		/* Set up Board */
		Board board = new Board(8, 8);
		
		/* Test data */
		board.placeDisk(new Point(5, 3), Color.BLACK);
		assertEquals(board.tile[3][5].color, Color.BLACK);
		assertEquals(board.blackPlayer.score, 4);
		assertEquals(board.whitePlayer.score, 1);
	}
	
	/** 
	 * Tests that getCurrentPlayer() returns the correct Player whose turn it is
	 */
	@Test
	public void testGetCurrentPlayer() {
		/* Set up Board */
		Board board = new Board(8, 8);
		
		/* Test number of X squares owned */
		Player player = board.getCurrentPlayer();
		assertEquals(player.color, Color.BLACK);
	}
	
	/**
	 * Tests number of X squares owned
	 */
	@Test
	public void testXSquaresOwned() {
		/* Set up Board */
		Board board = new Board(8, 8);
		
		/* Test data at setup */
		int xSquaresOwned = board.xSquaresOwned(Color.WHITE);
		assertEquals(xSquaresOwned, 0);
		
		/* Place BLACK disk and test data */
		board.placeDisk(new Point(1,1), Color.BLACK);
		xSquaresOwned = board.xSquaresOwned(Color.BLACK);
		assertEquals(xSquaresOwned, 1);
	}
	
	/**
	 * Tests number of C squares owned
	 */
	@Test
	public void testCSquaresOwned() {
		/* Set up Board */
		Board board = new Board(8, 8);
		
		/* Test data at setup */
		int cSquaresOwned = board.cSquaresOwned(Color.WHITE);
		assertEquals(cSquaresOwned, 0);
		
		/* Place BLACK disk and test data */
		board.placeDisk(new Point(0,1), Color.BLACK);
		cSquaresOwned = board.cSquaresOwned(Color.BLACK);
		assertEquals(cSquaresOwned, 1);
	}
	
	/**
	 * Tests number of corner squares owned
	 */
	@Test
	public void testCornersOwned() {
		/* Set up Board */
		Board board = new Board(8, 8);
		
		/* Test data at setup */
		int cornersOwned = board.cornersOwned(Color.WHITE);
		assertEquals(cornersOwned, 0);
		
		/* Place BLACK disk and test data */
		board.placeDisk(new Point(0,0), Color.BLACK);
		cornersOwned = board.cornersOwned(Color.BLACK);
		assertEquals(cornersOwned, 1);
	}
	
	/**
	 * Simply tests that String is not empty
	 */
	@Test
	public void testToString() {
		/* Set up Board */
		Board board = new Board(8, 8);
		
		/* Test data */
		assertNotNull(board.toString());
	}
}
