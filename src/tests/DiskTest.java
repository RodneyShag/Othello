package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import main_components.Color;
import main_components.Disk;

/**
 * \brief
 * Tests the simple Disk class
 * @author Rodney Shaghoulian
 */
public class DiskTest {

	/**
	 * This is a simple test to make sure the constructor works properly, meaning
 	 * 1) Not Null  2) Correct Color  3) Correct Position
	 */
	@Test
	public void testConstructor() {
		/* Set up data */
		Disk whiteDisk = new Disk(Color.WHITE);
		
		/* Test data was set up correctly */
		assertNotNull(whiteDisk);
		assertEquals(whiteDisk.color, Color.WHITE);
	}
	
	/**
	 * Tests the copy constructor. \n
	 * Creates a white Disk. Makes a copy of it using copy constructor. Verifies its data.
	 */
	@Test
	public void testCopyConstructor() {
		/* Set up data */
		Disk whiteDisk1 = new Disk(Color.WHITE);
		Disk whiteDisk2 = new Disk(whiteDisk1);
		
		/* Test data was set up correctly */
		assertNotNull(whiteDisk2);
		assertEquals(whiteDisk2.color, Color.WHITE);
	}
	
	/**
	 * Tests changing the color of a disk
	 */
	@Test
	public void testChangeColor() {
		/* Set up data */
		Disk disk = new Disk(Color.WHITE);
		
		/* Test changing Disk color */
		disk.changeColor(Color.BLACK);
		assertEquals(disk.color, Color.BLACK);
	}
}
