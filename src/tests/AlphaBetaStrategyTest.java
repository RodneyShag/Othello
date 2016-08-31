package tests;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.ArrayList;

import org.junit.Test;

import main_components.Board;
import main_components.Color;
import main_components.Command;
import main_components.Controller;
import strategies.Difficulty;
import strategies.AlphaBetaStrategy;

/**
 * \brief
 * Tests the choice of move in AlphaBetaStrategy
 * @author Rodney Shaghoulian
 */
public class AlphaBetaStrategyTest {

	/**
	 * Tests constructor properly initializes variables to non-null values
	 */
	@Test
	public void testAlphaBetaStrategy() {
		/* Set up data */
		Controller controller = new Controller(8, 8, Difficulty.EASY);
		AlphaBetaStrategy alphaBetaStrategy = new AlphaBetaStrategy(controller);
		
		/* Test data */
		assertNotNull(alphaBetaStrategy.controller);
		assertNotNull(alphaBetaStrategy.view);
		assertNotNull(alphaBetaStrategy.commandManager);
	}
	
	/**
	 * Tests that the computer A.I. makes a move
	 */
	@Test
	public void testMove() {
		/* Set up data */
		Controller controller = new Controller(8, 8, Difficulty.EASY);
		AlphaBetaStrategy alphaBetaStrategy = new AlphaBetaStrategy(controller);
		
		/* Test data */
		alphaBetaStrategy.move(alphaBetaStrategy.controller.board);
		assertEquals(alphaBetaStrategy.controller.board.playerTurn, Color.WHITE);
	}

	/**
	 * Tests stopping search at a certain depth in the tree.
	 */
	@Test
	public void testAlphaBeta() {
		/* Set up data */
		Controller controller = new Controller(8, 8, Difficulty.EASY);
		AlphaBetaStrategy alphaBetaStrategy = new AlphaBetaStrategy(controller);
		
		/* Test data */
		Board board = alphaBetaStrategy.alphaBeta(controller.board, 3, 3, 0, 0);
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
		AlphaBetaStrategy alphaBetaStrategy = new AlphaBetaStrategy(controller);
		
		/* Test data */
		ArrayList<Board> adjacentBoards = alphaBetaStrategy.getAdjacentBoards(controller.board);
		assertEquals(adjacentBoards.size(), 4);
	}

	/**
	 * Tests getting a command from 2 Boards
	 */
	@Test
	public void testGetCommand(){
		/* Set up data */
		Controller controller = new Controller(8, 8, Difficulty.EASY);
		Board board1 = controller.board;
		Board board2 = new Board(8, 8);
		board2.placeDisk(new Point(0,0), Color.WHITE);
		AlphaBetaStrategy alphaBetaStrategy = new AlphaBetaStrategy(controller);
		Command command = alphaBetaStrategy.getCommand(board1, board2);
		
		/* Test Command */
		assertEquals(command.destination, new Point(0,0));
		assertEquals(command.color, Color.WHITE);
		
	}
}
