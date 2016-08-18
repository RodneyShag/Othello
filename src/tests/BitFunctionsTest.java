package tests;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Test;

import main_components.BitFunctions;

public class BitFunctionsTest {

	/**
	 * Tests that the correct tile numbers are returned (0 to 63)
	 */
	@Test
	public void testGetTileNum() {
		Point pos = new Point(0, 0);
		byte tileNum = BitFunctions.getTileNum(pos);
		assertEquals(tileNum, 63);
		
		pos = new Point(7, 7);
		tileNum = BitFunctions.getTileNum(pos);
		assertEquals(tileNum, 0);
		
		pos = new Point(2, 3);
		tileNum = BitFunctions.getTileNum(pos);
		assertEquals(tileNum, 37);
	}

	/**
	 * Tests that the correct positions are returned for a few tile numbers from 0 to 63
	 */
	@Test
	public void testGetPosition() {
		byte tileNum = 3;
		Point pos = BitFunctions.getPoint(tileNum);
		assertEquals(pos, new Point(4, 7));
		
		tileNum = 63;
		pos = BitFunctions.getPoint(tileNum);
		assertEquals(pos, new Point(0, 0));
		
	}
	/**
	 * Tests that proper bit is returned, which usually represents a piece on a Board
	 */
	@Test
	public void testGetBitLongPoint() {
		long boardRep = 0x0100000000000000L;
		boolean bit = BitFunctions.getBit(boardRep, new Point(7, 0));
		assertTrue(bit);
	}

	/**
	 * Tests that proper bit is returned, which usually represents a piece on a Board
	 */
	@Test
	public void testGetBitLongByte() {
		long boardRep = 0x0000000100000000L;
		boolean bit = BitFunctions.getBit(boardRep, (byte) 32);
		assertTrue(bit);

		boardRep = 0x8000000000000000L;
		bit = BitFunctions.getBit(boardRep, (byte) 63);
		assertTrue(bit);
	}

	/**
	 * Tests that we can set bits on a board representation properly
	 */
	@Test
	public void testSetBitLongPoint() {
		long boardRep = 0L;
		boardRep = BitFunctions.setBit(boardRep, new Point(0, 0));
		boolean bit = BitFunctions.getBit(boardRep, (byte) 63);
		assertTrue(bit);
	}

	/**
	 * Tests that we can set bits on a board representation properly
	 */
	@Test
	public void testSetBitLongByte() {
		long boardRep = 0L;
		boardRep = BitFunctions.setBit(boardRep, new Point(0, 0));
		boolean bit = BitFunctions.getBit(boardRep, (byte) 63);
		assertTrue(bit);
	}

	/**
	 * Tests that we can set a bit in a 64-bit "long" to 0 
	 */
	@Test
	public void testClearBitLongPoint() {
		/* Test (0, 0) */
		long boardRep = 0x8000000000000000L;
		boolean bit = BitFunctions.getBit(boardRep, new Point(0, 0));
		assertTrue(bit);

		boardRep = BitFunctions.clearBit(boardRep, new Point(0, 0));
		bit = BitFunctions.getBit(boardRep, new Point(0, 0));
		assertFalse(bit);
		
		/* Test (3, 5) */
		boardRep = 0xFFFFFFFFFFFFFFFFL;
		bit = BitFunctions.getBit(boardRep, new Point(3, 5));
		assertTrue(bit);

		boardRep = BitFunctions.clearBit(boardRep, new Point(3, 5));
		bit = BitFunctions.getBit(boardRep, new Point(3, 5));
		assertFalse(bit);
	}

	/**
	 * Tests that we can set a bit in a 64-bit "long" to 0 
	 */
	@Test
	public void testClearBitLongByte() {
		/* Test tile 63 */
		long boardRep = 0x8000000000000000L;
		boolean bit = BitFunctions.getBit(boardRep, (byte) 63);
		assertTrue(bit);

		boardRep = BitFunctions.clearBit(boardRep, (byte) 63);
		bit = BitFunctions.getBit(boardRep, (byte) 63);
		assertFalse(bit);
	}

	/**
	 * Tests counting the number of bits set in a long
	 */
	@Test
	public void testCardinality() {
		long boardRep = 0x8000000000000000L;
		byte cardinality = BitFunctions.cardinality(boardRep);
		assertEquals(cardinality, 1);
		
		boardRep = 0x8000000000000101L;
		cardinality = BitFunctions.cardinality(boardRep);
		assertEquals(cardinality, 3);
	}
}
