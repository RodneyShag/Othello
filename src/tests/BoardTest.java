package tests;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Test;

import main_components.Board;
import main_components.Player;
import main_components.Color;

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
		Board board = new Board();
		
		/* Test data */
		assertEquals(board.rows, 8);
		assertEquals(board.columns, 8);
		assertNotNull(board.blackPlayer);
		assertNotNull(board.whitePlayer);
		assertEquals(board.playerTurn, Color.BLACK);
		assertFalse(board.gameEnded);
		assertNull(board.winner);
		assertEquals(board.turn, 1);
		
		assertEquals(board.getDiskColor(new Point(3, 3)), Color.BLACK);
		assertEquals(board.getDiskColor(new Point(4, 4)), Color.BLACK);
		assertEquals(board.getDiskColor(new Point(3, 4)), Color.WHITE);
		assertEquals(board.getDiskColor(new Point(4, 3)), Color.WHITE);
	}

	/**
	 * Tests Board's Copy Constructor: Creates a Board, copies it, and verifies data is correct.
	 */
	@Test
	public void testBoardCopyConstructor() {
		/* Set up Boards */
		Board board1 = new Board();
		Board board2 = new Board(board1);
		
		/* Test data */
		assertEquals(board2.rows, 8);
		assertEquals(board2.columns, 8);
		assertEquals(board2.playerTurn, Color.BLACK);
		assertFalse(board2.gameEnded);
		assertNull(board2.winner);
		assertNotNull(board2.blackPlayer);
		assertNotNull(board2.whitePlayer);
	}

	/**
	 * Tests out of range positions on Board (should be false). Also tests true condition.
	 */
	@Test
	public void testValidPosition() {
		/* Set up Board */
		Board board = new Board();
		
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
	public void testGetDiskColor() {
		/* Set up Board */
		Board board = new Board();
		
		/* Test data */
		Color color = board.getDiskColor(new Point(3,3));
		assertEquals(color, Color.BLACK);
		color = board.getDiskColor(new Point(3,4));
		assertEquals(color, Color.WHITE);
		color = board.getDiskColor(new Point(4,3));
		assertEquals(color, Color.WHITE);
		color = board.getDiskColor(new Point(4,4));
		assertEquals(color, Color.BLACK);
		
	}

	/**
	 * Tests setting Disk Color on a Board
	 */
	@Test
	public void testSetDiskColor() {
		/* Set up Board */
		Board board = new Board();
		
		/* Test data */
		board.setDiskColor(new Point(1, 1), Color.BLACK);
		Color color = board.getDiskColor(new Point(1,1));
		assertEquals(color, Color.BLACK);
	}
	
	/**
	 * Tests that turns alternate. BLACK should get 1st turn. WHITE Player should get the 2nd turn.
	 */
	@Test
	public void testUpdateTurn() {
		/* Set up Board */
		Board board = new Board();
		
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
		Board board = new Board();
		
		/* Test gameEnded at beginning of game */
		assertFalse(board.gameEnded);
		
		/* Simulate an ended game, and test gameEnded */
		board.blackPlayer.validMoves = 0L;
		board.whitePlayer.validMoves = 0L;
		board.updateGameStatus();
		assertTrue(board.gameEnded);
	}

	/**
	 * Place 2 Disks for BLACK to completely capture all of WHITE's Disks. Test that game ended
	 */
	@Test
	public void testUpdateBoard() {
		/* Set up Board */
		Board board = new Board();
		
		/* Test data */
		board.placeDisk(new Point(5, 3), Color.BLACK);
		board.placeDisk(new Point(3, 5), Color.BLACK); // placeDisk automatically calls updateBoard().
		assertEquals(board.blackPlayer.validMoves, 0);
		assertEquals(board.whitePlayer.validMoves, 0);
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
		Board board = new Board();
		
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
		Board board = new Board();
		
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
		Board board = new Board();
		
		/* Remove Disk. Check it's removed */
		assertEquals(board.getDiskColor(new Point(3, 3)), Color.BLACK);
		board.removeDisk(new Point(3, 3));
		assertEquals(board.getDiskColor(new Point(3, 3)), Color.NONE);
	}

	/**
	 * Tests flipping the (4,3) tile to BLACK and then to WHITE, using valid moves
	 */
	@Test
	public void testGetCaptures() {
		/* Set up Board */
		Board board = new Board();
		
		/* Test Data */
		board.setDiskColor(new Point(5, 3), Color.BLACK);
		long disksToFlip = board.getCaptures(new Point(5, 3), Color.BLACK);
		assertEquals(disksToFlip, 0x0000000800000000L);
		
//		board = new Board();
//		board.placeDisk(new Point(5, 3), Color.BLACK);
//		System.out.println(board);
	}

	/**
	 * Tests flipping the (4,3) tile to BLACK and then to WHITE, by using left direction
	 */
	@Test
	public void testToFlipInDirection() {
		/* Set up Board */
		Board board = new Board();
		
		/* Test Data */
		long disksToFlip = board.toFlipInDirection(new Point(5, 3), Color.BLACK, -1, 0);
		assertEquals(disksToFlip, 0x0000000800000000L);
	}

	/**
	 * Tests placing a BLACK Disk on Board, and capturing WHITE Disk.
	 */
	@Test
	public void testPlaceDisk() {
		/* Set up Board */
		Board board = new Board();
		
		/* Test data */
		board.placeDisk(new Point(5, 3), Color.BLACK);
		assertEquals(board.getDiskColor(new Point(5, 3)), Color.BLACK);
	}
	
	/** 
	 * Tests that getCurrentPlayer() returns the correct Player whose turn it is
	 */
	@Test
	public void testGetCurrentPlayer() {
		/* Set up Board */
		Board board = new Board();
		
		/* Test number of X squares owned */
		Player player = board.getCurrentPlayer();
		assertEquals(player.color, Color.BLACK);
	}
	
	/** 
	 * Tests that getOpponent() returns the correct Player whose turn it is
	 */
	@Test
	public void testGetOpponent() {
		/* Set up Board */
		Board board = new Board();
		
		/* Test number of X squares owned */
		Player player = board.getOpponent();
		assertEquals(player.color, Color.WHITE);
	}
	
	/**
	 * Tests that blackPlayer is properly returned for Color.BLACK
	 */
	@Test
	public void testGetPlayerFromColor() {
		Board board = new Board();
		Player player = board.getPlayerFromColor(Color.BLACK);
		assertEquals(player, board.blackPlayer);
	}
	
	/**
	 * Tests that whitePlayer is properly returned as an opponent for Color.BLACK
	 */
	@Test
	public void testGetOpponentOfColor() {
		Board board = new Board();
		Player player = board.getOpponentOfColor(Color.BLACK);
		assertEquals(player, board.whitePlayer);
	}
	
	/**
	 * Tests number of X squares owned
	 */
	@Test
	public void testXSquaresOwned() {
		/* Set up Board */
		Board board = new Board();
		
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
		Board board = new Board();
		
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
		Board board = new Board();
		
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
		Board board = new Board();
		assertNotNull(board.toString());
	}
}
