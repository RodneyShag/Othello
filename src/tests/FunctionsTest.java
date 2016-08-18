package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import main_components.Color;
import main_components.Functions;

public class FunctionsTest {

	@Test
	public void testGetOppositeColor() {
		/* Test data */
		assertEquals(Functions.getOppositeColor(Color.BLACK), Color.WHITE);
		assertEquals(Functions.getOppositeColor(Color.WHITE), Color.BLACK);
	}

}
