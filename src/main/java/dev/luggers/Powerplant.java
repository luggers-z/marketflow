package dev.luggers;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * Powerplant is ...
 */
public class Powerplant {
    public static final long MEGA_HOURS = 3600000000L;
    protected final Pool pool;
    private final String name;
    private final Powerplant next;
    private final double efficiency;
    private final double maxwaterflow;
    private final double minwaterflow;
    private final double height;
    protected DoubleProperty turbineFlow = new SimpleDoubleProperty();
    protected DoubleProperty powerMW = new SimpleDoubleProperty();
    private double energy;

    /**
     * Create a new instance of a power plant, with the given capacity....
     *
     * @param name         A String representing the human-readable name of the
     *                     power plant.
     * @param next         an instance of power plant, following down the line.
     *                     Maybe null.
     * @param pool         A reservoir attached to this power plant, never null.
     * @param height       The falling height of the water generating Power
     * @param efficiency   The efficiency of the power plant
     * @param maxwaterflow The maximum waterflow
     * @param minwaterflow The minimum waterflow
     */
    public Powerplant(final String name, final Powerplant next, final Pool pool, final double height,
                      final double efficiency, final double maxwaterflow, final double minwaterflow) {
        this.name = name;
        this.next = next;
        this.pool = pool;
        this.height = height;
        this.efficiency = efficiency;
        this.maxwaterflow = maxwaterflow;
        this.minwaterflow = minwaterflow;
        turbineFlow.set(100);
    }

    /**
     * Create a new instance of a Powerplant, with the given capacity, without a
     * downstream power plant.
     * * @param name          A String representing the human-readable name of the
     * *                      power plant.
     * * @param pool          A reservoir attached to this power plant, never null.
     * * @param height         The falling height of the water generating Power
     * * @param efficiency    The efficiency of the power plant
     * * @param maxwaterflow  The maximum waterflow
     * * @param minwaterflow  The minimum waterflow
     */
    public Powerplant(final String name, final Pool pool, final double height, final double efficiency,
                      final double maxwaterflow, double minwaterflow) {
        this(name, null, pool, height, efficiency, maxwaterflow, minwaterflow);
    }

    public Powerplant() {
        name = null;
        next = null;
        pool = new Pool();
        height = 1.5;
        efficiency = 0.8;
        turbineFlow.set(700);
        maxwaterflow = 1000;
        minwaterflow = 20;
    }

    public void processFlow(double incomingFlow, double delta) {
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

    private double calculateEnergy(double turbineFlow, double delta) {
        double tempPower = height * turbineFlow * 1000 * 9.81 * efficiency;
        powerMW.set(tempPower / 1000000);

        return tempPower * delta / MEGA_HOURS;
    }

    public double collectEnergy() {
        double energysave = energy;
        energy = 0;
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
        return maxwaterflow;
    }

    public double getMinWaterflow() {
        return minwaterflow;
    }
}