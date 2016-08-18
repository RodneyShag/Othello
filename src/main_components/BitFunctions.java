package main_components;
/** \brief The main classes for our Othello Program */

import java.awt.Point;

public class BitFunctions {
	
	/**
	 * Returns tile number from 0 to 63, corresponding to the numbering below \n
	 * 
	 *  7  6  5  4  3  2  1  0              \n
	 * 15 14 13 12 11 10  9  8              \n
	 * 23 22 21 20 19 18 17 16              \n
	 * 31 30 29 28 27 26 25 24              \n
	 * 39 38 37 36 35 34 33 32              \n
	 * 47 46 45 44 43 42 41 40              \n
	 * 55 54 53 52 51 50 49 48              \n
	 * 63 62 61 60 59 58 57 56              \n
	 * 
	 * @param pos 	A 2-d representation of Point in question
	 * @return 		the 1-d representation of the Point
	 */
	public static byte getTileNum(Point pos){
		return (byte) (63 - (8*pos.y + pos.x));
	}
	
	/**
	 * converts from tile number (in BIG-ENDIAN numbering) to Point numbering
	 * @param tileNum	the tile number (in BIG-ENDIAN numbering)
	 * @return			the corresponding Point numbering
	 */
	public static Point getPoint(byte tileNum){
		return new Point(7 - tileNum % 8, 7 - tileNum / 8);
	}
	
	/**
	 * Determines if bit exists at position "pos"
	 * @param boardRep 	the board representation
	 * @param pos		the position to check a bit for
	 * @return			true if bit exists at position. false otherwise
	 */
	public static boolean getBit(long boardRep, Point pos){
		byte tileNum = getTileNum(pos);
		return (boardRep & (1L << tileNum)) != 0; // the 1 MUST be represented as a long as such.
	}
	
	/**
	 * Determines if bit exists at position represented by "tileNum"
	 * @param boardRep 	the board representation
	 * @param tileNum	the numbered tile to change to 1
	 * @return			true if bit exists at position. false otherwise
	 */
	public static boolean getBit(long boardRep, byte tileNum){
		return (boardRep & (1L << tileNum)) != 0; // the 1 MUST be represented as a long as such.
	}
	
	/**
	 * Changes bit at "Point" to 1 in the long representation of a board
	 * @param boardRep  the board representation
	 * @param pos		the Point to add
	 * @return			the board as a long
	 */
	public static long setBit(long boardRep, Point pos){
		byte tileNum = getTileNum(pos);
		return boardRep | (1L << tileNum); // the 1 MUST be represented as a long as such.
	}
	
	/**
	 * Changes bit at "tileNum" to 1 in the long representation of a board
	 * @param boardRep  the board representation
	 * @param tileNum	the numbered tile to change to 1
	 * @return			the board as a long
	 */
	public static long setBit(long boardRep, byte tileNum){
		return boardRep | (1L << tileNum); // the 1 MUST be represented as a long as such.
	}
	
	/**
	 * Changes bit at "Point" to 0 in the long representation of a board
	 * @param boardRep  the board representation
	 * @param pos		the Point to remove
	 * @return			the board as a long
	 */
	public static long clearBit(long boardRep, Point pos){
		byte tileNum = getTileNum(pos);
		long mask = ~(1L << tileNum); // must use 64-bit long here, not 32-bit int
		return boardRep & mask;
	}
	
	/**
	 * Changes bit at "tileNum" to 0 in the long representation of a board
	 * @param boardRep  the board representation
	 * @param tileNum	the numbered tile to change to 0
	 * @return			the board as a long
	 */
	public static long clearBit(long boardRep, byte tileNum){
		long mask = ~(1L << tileNum); // must use 64-bit long here, not 32-bit int
		return boardRep & mask;
	}
	
	/**
	 * Counts the number of bits set in a long
	 * @param num	The 64-bit "long" to count the bits of
	 * @return		The number of bits set in the "long" (0 to 64)
	 */
	public static byte cardinality(long num){
		byte result = 0;
		for (int i = 0; i < 64; i++, num >>= 1){ //assumes 64-bit long
			if ((num & 1) == 1)
				result++;
		}
		return result;
	}
}
