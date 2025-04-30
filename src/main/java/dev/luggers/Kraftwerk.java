package dev.luggers;

/**
 * Powerplant is ...
 */
public class Kraftwerk {
	public static final int KILOWATT_HOURS = 3600000;

	private final String name;
	private final Kraftwerk nf;
	/*private*/ final Speicherbecken pool;

	private double wirkungsgrad;
	private double durchfluss;
	private double maxdurchfluss;
	private double mindurchfluss;
	/*private*/ double hoehe;

	/**
	 * Create a new instance of a Powerplant, with the given capacity....
	 * 
	 * @param name          A String representing the human readable name of the
	 *                      powerplant.
	 * @param nachfolger    an instance of Powerplant, following down the line.
	 *                      Maybe null.
	 * @param pool          A reservoir attached to this Powerplant, never null.
	 * @param hoehe
	 * @param wirkungsgrad
	 * @param maxdurchfluss
	 * @param mindurchfluss
	 */
	public Kraftwerk(final String name, final Kraftwerk nachfolger, final Speicherbecken pool, final double hoehe,
			final double wirkungsgrad, final double maxdurchfluss, double mindurchfluss) {
		this.name = name;
		this.nf = nachfolger;
		this.pool = pool;
		this.hoehe = hoehe;
		this.wirkungsgrad = wirkungsgrad;
		this.maxdurchfluss = maxdurchfluss;
		this.mindurchfluss = mindurchfluss;
		durchfluss = 510;
	}

	/**
	 * Create a new instance of a Powerplant, with the given capacity, without a
	 * downstream power plant.
	 *
	 * @param name          A String representing the human readable name of the
	 *                      powerplant.
	 * @param pool          A reservoir attached to this Powerplant, never null.
	 * @param hoehe
	 * @param wirkungsgrad
	 * @param maxdurchfluss
	 * @param mindurchfluss
	 */
	public Kraftwerk(final String name, final Speicherbecken pool, final double hoehe, final double wirkungsgrad,
			final double maxdurchfluss, double mindurchfluss) {
		this(name, null, pool, hoehe, wirkungsgrad, maxdurchfluss, mindurchfluss);
	}

	public double fluss(final double zufluss, final double strompreis, final double delta) {
		double fluss = (zufluss - durchfluss) * delta;
		if (!pool.berechnen(fluss)) {
			durchfluss = zufluss;
		}
		final var gewinn = gewinn(durchfluss, strompreis, delta);

		if (nf != null) {
			return (gewinn + nf.fluss(durchfluss, strompreis, delta));
		} else {
			return gewinn;
		}
	}

	public double gewinn(double durchfluss, double strompreis, double delta) {
		double energie = hoehe * durchfluss * delta * 1000 * 9.81 * wirkungsgrad;
		double kwh = energie / KILOWATT_HOURS;
		return kwh * strompreis;
	}

	/**
	 * Get the human readable name of the power plant.
	 * 
	 * @return A String representing the human readable name of the power plant.
	 */
	public String getName() {
		return name;
	}
}
