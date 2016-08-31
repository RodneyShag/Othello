package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import main_components.Board;
import main_components.Color;
import main_components.Controller;
import strategies.Difficulty;
import strategies.MinimaxStrategy;

/**
 * \brief
 * Tests the choice of move in MinimaxStrategy
 * @author Rodney Shaghoulian
 */
public class MinimaxStrategyTest {

	/**
	 * Tests constructor properly initializes variables to non-null values
	 */
	@Test
	public void testMinimaxStrategy() {
		/* Set up data */
		Controller controller = new Controller(8, 8, Difficulty.EASY);
		MinimaxStrategy minimaxStrategy = new MinimaxStrategy(controller);
		
		/* Test data */
		assertNotNull(minimaxStrategy.controller);
		assertNotNull(minimaxStrategy.view);
		assertNotNull(minimaxStrategy.commandManager);
	}
	
	/**
	 * Tests that the computer A.I. makes a move
	 */
	@Test
	public void testMove() {
		/* Set up data */
		Controller controller = new Controller(8, 8, Difficulty.EASY);
		MinimaxStrategy minimaxStrategy = new MinimaxStrategy(controller);
		
		/* Test data */
		minimaxStrategy.move(minimaxStrategy.controller.board);
		assertEquals(minimaxStrategy.controller.board.playerTurn, Color.WHITE);
	}

	/**
	 * Tests stopping search at a certain depth in the tree.
	 */
	@Test
	public void testMinimax() {
		/* Set up data */
		Controller controller = new Controller(8, 8, Difficulty.EASY);
		MinimaxStrategy minimaxStrategy = new MinimaxStrategy(controller);
		
		/* Test data */
		Board board = minimaxStrategy.minimax(controller.board, 3, 3);
		assertEquals(board.blackPlayer.score, 2);
		assertEquals(board.whitePlayer.score, 2);
		
	}

	/**
	 * Tests that the right number of adjacent Boards are returned (from initial Board position)
	 */
	@Test
	public void testGetAdjacentBoards() {
		/* Set up data */
		Controller controller = new Controller(8, 8, Difficulty.EASY);
		MinimaxStrategy minimaxStrategy = new MinimaxStrategy(controller);
		
		/* Test data */
		ArrayList<Board> adjacentBoards = minimaxStrategy.getAdjacentBoards(controller.board);
		assertEquals(adjacentBoards.size(), 4);
	}

}
