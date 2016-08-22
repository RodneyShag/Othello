package strategies;

import java.util.Comparator;

import main_components.Board;

/**
 * \brief
 * Used in sorting an array of boards so that the moves that immediately give WHITE the largest utility advantage come first
 * @author Rodney Shaghoulian
 */
public class BoardComparatorWhite implements Comparator<Board>{
	/**
	 * Compares 2 Boards to determine which one is better for whitePlayer.
	 * @param board1	Board 1. Will compare to Board 2.
	 * @param board2	Board 2. Will compare to Board 1.
	 * @return		A negative integer if Board 1 is better than Board 2 for whitePlayer. \n
	 * 				Zero if Board 1 is equal to Board 2 for whitePlayer. \n
	 *              A positive integer if Board 1 is better than Board 2 for whitePlayer.
	 */
	@Override
	public int compare(Board board1, Board board2){
		Utility utility1 = new Utility(board1);	// corresponds to BLACK's utility
		utility1.utilityFinal();
		
		Utility utility2 = new Utility(board2); // corresponds to BLACK's utility
		utility2.utilityFinal();
		
		return utility1.value - utility2.value; // Example: If utility1 > utility2, we want board1 to come after board2
	}
}

