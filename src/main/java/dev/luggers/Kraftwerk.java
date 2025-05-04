package dev.luggers;

/**
 * Powerplant is ...
 */
public class Kraftwerk {
	public static final int KILO_HOURS = 3600000;

	private final String name;
	private final Kraftwerk next;
	protected final Speicherbecken pool;
	private final double efficiency;
	private double turbineFlow;
	private final double maxwaterflow;
	private final double minwaterflow;
	private final double height;
    private double energy;
	/**
	 * Create a new instance of a power plant, with the given capacity....
	 * 
	 * @param name          A String representing the human-readable name of the
	 *                      power plant.
	 * @param next    an instance of power plant, following down the line.
	 *                      Maybe null.
	 * @param pool          A reservoir attached to this power plant, never null.
	 * @param height         The falling height of the water generating Power
	 * @param efficiency    The efficiency of the power plant
	 * @param maxwaterflow  The maximum waterflow
	 * @param minwaterflow  The minimum waterflow
	 */
	public Kraftwerk(final String name, final Kraftwerk next, final Speicherbecken pool, final double height,
			final double efficiency, final double maxwaterflow, final double minwaterflow) {
		this.name = name;
		this.next = next;
		this.pool = pool;
		this.height = height;
		this.efficiency = efficiency;
		this.maxwaterflow = maxwaterflow;
		this.minwaterflow = minwaterflow;
		turbineFlow = 100;
	}

	/**
	 * Create a new instance of a Powerplant, with the given capacity, without a
	 * downstream power plant.
	 *    * @param name          A String representing the human-readable name of the
	 * 	 *                      power plant.
	 * 	 * @param pool          A reservoir attached to this power plant, never null.
	 * 	 * @param height         The falling height of the water generating Power
	 * 	 * @param efficiency    The efficiency of the power plant
	 * 	 * @param maxwaterflow  The maximum waterflow
	 * 	 * @param minwaterflow  The minimum waterflow
	 */
	public Kraftwerk(final String name, final Speicherbecken pool, final double height, final double efficiency,
					 final double maxwaterflow, double minwaterflow) {
		this(name, null, pool, height, efficiency, maxwaterflow, minwaterflow);
	}

	public Kraftwerk(){
		name = null;
		next = new Kraftwerk(null, new Speicherbecken(null, 20000, 15, 1.5, 0.01, 4), 1.5, 0.5, 1, 1);
		pool = new Speicherbecken();
		height = 1.5;
		efficiency = 0.5;
		turbineFlow = 100;
		maxwaterflow = 500;
		minwaterflow = 50;
	}

	public void processFlow(double incomingFlow, double delta) {
		if (pool.isFull()&&turbineFlow>incomingFlow) {
			turbineFlow = incomingFlow;
		}
		double flowToPool = (incomingFlow - turbineFlow) * delta;
		pool.processFlow(flowToPool);
		energy+=calculateEnergy(turbineFlow,delta);
		if(next!=null) {
			next.processFlow(turbineFlow,delta);
		}
	}

	private double calculateEnergy(double turbineFlow, double delta) {
		double energy = height * turbineFlow * delta * 1000 * 9.81 * efficiency;
        return energy / KILO_HOURS;
	}

	public double collectEnergy(){
		double energysave =energy;
        energy = 0;
		if(next!=null) {
			energysave+=next.collectEnergy();
		}
		return energysave;
	}
	/**
	 * Get the human-readable name of the power plant.
	 * 
	 * @return A String representing the human-readable name of the power plant.
	 */
	public String getName() {
		return name;
	}
}
