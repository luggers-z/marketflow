package dev.luggers;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * Powerplant is ...
 */
public class Powerplant {
	private static final long MEGA_HOURS = 3600000000L;

	private static final double EIGHTY_PERCENT = 0.8;

	private final Pool pool;

	private final PowerplantMetadata metadata;
	private final double efficiency = EIGHTY_PERCENT;

	protected DoubleProperty turbineFlow = new SimpleDoubleProperty();
	protected DoubleProperty powerMW = new SimpleDoubleProperty();
	protected DoubleProperty specificInflow = new SimpleDoubleProperty();

	private Powerplant next;
	private double energy;

	public Powerplant(final PowerplantMetadata metadata) {
		this.metadata = metadata;
		this.pool = new Pool(null, metadata.length(), metadata.width(), metadata.startHeight(), metadata.minHeight(),
				metadata.maxHeight(), metadata.normalHeight());
	}

	public void processFlow(final double incomingFlow, final double delta) {
		specificInflow.set(incomingFlow);
		double tempTurbineFlow = turbineFlow.get();

		if (pool.isFull() && tempTurbineFlow < incomingFlow) {
			turbineFlow.set(incomingFlow);
			tempTurbineFlow = incomingFlow;
		}

		if (pool.isEmptyif(incomingFlow - tempTurbineFlow) && tempTurbineFlow > incomingFlow) {
			turbineFlow.set(incomingFlow);
			tempTurbineFlow = incomingFlow;
		}

		double flowToPool = (incomingFlow - tempTurbineFlow) * delta;
		pool.processFlow(flowToPool);
		energy += calculateEnergy(tempTurbineFlow, delta);
		if (next != null) {
			next.processFlow(tempTurbineFlow, delta);
		}
	}

	private double calculateEnergy(final double turbineFlow, final double delta) {
		final double tempPower = metadata.height() * turbineFlow * 1000 * 9.81 * efficiency;
		powerMW.set(tempPower / 1000000);

		return tempPower * delta / MEGA_HOURS;
	}

	public double collectEnergy() {
		final var energysave = energy;
		energy = 0;
		return energysave;
	}

	/**
	 * Get the human-readable name of the power plant.
	 *
	 * @return A String representing the human-readable name of the power plant.
	 */
	public String getName() {
		return metadata.name();
	}

	public Powerplant getNext(int i) {
		if (i == 0) {
			return this;
		}
		return next.getNext(i - 1);
	}

	public int getLength() {
		if (next != null) {
			return (next.getLength() + 1);
		}
		return 1;
	}

	public double getMaxWaterflow() {
		return metadata.maxwaterflow();
	}

	public double getMinWaterflow() {
		return metadata.minwaterflow();
	}

	public Pool getPool() {
		return pool;
	}

	public void setNext(Powerplant next) {
		this.next = next;
		if (next != null) {
			this.pool.setNext(next.getPool());
		}
	}

	public double length() {
		return metadata.length();
	}

	public double width() {
		return metadata.width();
	}

	public double getRelXPos() {
		return metadata.relXpos();
	}

	public double getRelYPos() {
		return metadata.relYpos();
	}
}