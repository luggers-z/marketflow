package dev.luggers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class KraftwerkTest {

	@Test
	void testKraftwerk() {
		var maxwehr=new Kraftwerk();
		maxwehr.processFlow(100,360000);
		System.out.println(maxwehr.collectEnergy()*0.28);
	}

	@Test
	@Disabled
	void testProcessFlow() {
		fail("Not yet implemented");
	}

	@Test
	@Disabled
	void testCalculateRevenue() {
		fail("Not yet implemented");
	}

	@Test
	@Disabled
	void testToString() {
		fail("Not yet implemented");
	}

}
