/** \brief Modular Tests for our Othello Game */
package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


/**
 * \brief
 * A Test Suite to run all written J-Unit tests
 * @author Rodney Shaghoulian
 */
@RunWith(Suite.class)
@SuiteClasses({ DiskTest.class, PlayerTest.class, BoardTest.class, ButtonTest.class,
	CommandManagerTest.class, CommandTest.class, ControllerTest.class, UtilityTest.class,
	RandomStrategyTest.class, MinimaxStrategyTest.class, AlphaBetaStrategyTest.class})
public class AllTests {

}