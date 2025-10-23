package dev.luggers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class PowerplantTest {

	@Test
	void testKraftwerk() {
		var maxwehr=new Powerplant();
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
