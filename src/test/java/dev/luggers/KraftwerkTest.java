package dev.luggers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class KraftwerkTest {

	@Test
	void testKraftwerk() {
		var maxwehr=new Kraftwerk("Maxwehr", null, 9, 1, 2, 3 );
		assertEquals(9, maxwehr.hoehe, "height should be identical");
		assertEquals("Maxwehr", maxwehr.getName(), "name should be identical");		
	}

	@Test
	@Disabled
	void testFluss() {
		fail("Not yet implemented");
	}

	@Test
	@Disabled
	void testGewinn() {
		fail("Not yet implemented");
	}

	@Test
	@Disabled
	void testToString() {
		fail("Not yet implemented");
	}

}
