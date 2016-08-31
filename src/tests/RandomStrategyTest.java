package tests;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Test;

import main_components.Board;
import main_components.Color;
import main_components.Controller;
import strategies.Difficulty;
import strategies.RandomStrategy;

/**
 * \brief
 * Tests the choice of random move in RandomStrategy
 * @author Rodney Shaghoulian
 */
public class RandomStrategyTest {

	/**
	 * Tests constructor properly initializes variables to non-null values
	 */
	@Test
	public void testRandomStrategy() {
		/* Set up data */
		Controller controller = new Controller(8, 8, Difficulty.EASY);
		RandomStrategy randomStrategy = new RandomStrategy(controller);
		
		/* Test data */
		assertNotNull(randomStrategy.controller);
		assertNotNull(randomStrategy.view);
		assertNotNull(randomStrategy.commandManager);
	}
	
	/**
	 * Tests that the computer A.I. makes a move
	 */
	@Test
	public void testMove() {
		/* Set up data */
		Controller controller = new Controller(8, 8, Difficulty.EASY);
		RandomStrategy randomStrategy = new RandomStrategy(controller);
		
		/* Test data */
		randomStrategy.move(randomStrategy.controller.board);
		assertEquals(randomStrategy.controller.board.playerTurn, Color.WHITE);
	}

	/**
	 * Tests that a move was selected (not null)
	 */
	@Test
	public void testChooseRandomMove() {
		/* Set up data */
		Controller controller = new Controller(8, 8, Difficulty.EASY);
		RandomStrategy randomStrategy = new RandomStrategy(controller);
		
		/* Test data */
		Board board = randomStrategy.controller.board;
		Point move = randomStrategy.chooseRandomMove(board.blackPlayer.validMoves);
		assertNotNull(move);
	}
}	
