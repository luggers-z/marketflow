package dev.luggers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PowerplantMetadataTest {

	@Test
	void testGetMetadataWithInformation() {
		// Test with information field that has line breaks
		String csvData = "TestPlant;400;20;5;5;2;3;3;10;20000;0.31;0.4985;Line 1\\nLine 2\\nLine 3";
		PowerplantMetadata metadata = PowerplantMetadata.getMetadata(csvData);
		
		assertEquals("TestPlant", metadata.name());
		assertEquals(400.0, metadata.maxwaterflow());
		assertEquals(20.0, metadata.minwaterflow());
		assertEquals("Line 1\nLine 2\nLine 3", metadata.information());
	}

	@Test
	void testGetMetadataWithoutInformation() {
		// Test backwards compatibility - CSV without information field
		String csvData = "TestPlant;400;20;5;5;2;3;3;10;20000;0.31;0.4985";
		PowerplantMetadata metadata = PowerplantMetadata.getMetadata(csvData);
		
		assertEquals("TestPlant", metadata.name());
		assertEquals(400.0, metadata.maxwaterflow());
		assertEquals(20.0, metadata.minwaterflow());
		assertEquals("", metadata.information()); // Should be empty string when not present
	}

	@Test
	void testGetMetadataWithEmptyInformation() {
		// Test with empty information field
		String csvData = "TestPlant;400;20;5;5;2;3;3;10;20000;0.31;0.4985;";
		PowerplantMetadata metadata = PowerplantMetadata.getMetadata(csvData);
		
		assertEquals("", metadata.information());
	}
}
