package dev.luggers;

public record PowerplantMetadata(String name, double maxwaterflow, double minwaterflow, double height, double maxHeight,
		double minHeight, double normalHeight, double startHeight, double width, double length, double relXpos,
		double relYpos, String information) {
	private static final int MAXWATERFLOW = 1;
	private static final int MINWATERFLOW = 2;
	private static final int NAME = 0;
	private static final int HEIGHT = 3;
	private static final int MAXHEIGHT = 4;
	private static final int MINHEIGHT = 5;
	private static final int NORMALHEIGHT = 6;
	private static final int STARTHEIGHT = 7;
	private static final int WIDTH = 8;
	private static final int LENGTH = 9;
	private static final int RELATIVEXPOSITION = 10;
	private static final int RELATIVEYPOSITION = 11;
	private static final int INFORMATION = 12;

	public static PowerplantMetadata getMetadata(String csvData) {
		try {
			final String[] values = csvData.split(";");

			final var name = values[NAME];
			final var maxwaterflow = Double.parseDouble(values[MAXWATERFLOW]);
			final var minwaterflow = Double.parseDouble(values[MINWATERFLOW]);
			final var height = Double.parseDouble(values[HEIGHT]);
			final var maxHeight = Double.parseDouble(values[MAXHEIGHT]);
			final var minHeight = Double.parseDouble(values[MINHEIGHT]);
			final var normalHeight = Double.parseDouble(values[NORMALHEIGHT]);
			final var startHeight = Double.parseDouble(values[STARTHEIGHT]);
			final var width = Double.parseDouble(values[WIDTH]);
			final var length = Double.parseDouble(values[LENGTH]);
			final var relXpos = Double.parseDouble(values[RELATIVEXPOSITION]);
			final var relYpos = Double.parseDouble(values[RELATIVEYPOSITION]);
			
			// Information field is optional - use empty string if not present
			final var information = values.length > INFORMATION ? values[INFORMATION].replace("\\n", "\n") : "";

			return new PowerplantMetadata(name, maxwaterflow, minwaterflow, height, maxHeight, minHeight, normalHeight,
					startHeight, width, length, relXpos, relYpos, information);
		} catch (Exception ex) {
			System.err.printf("Please check Powerplant CSV data! %s", ex.getMessage());
			throw new RuntimeException(ex);
		}
	}

}
