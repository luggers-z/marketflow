package dev.luggers;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * Powerplant is ...
 */
public class Powerplant {
    public static final int MAXWATERFLOW = 1;
    public static final int MINWATERFLOW = 2;
    private  static final long MEGA_HOURS = 3600000000L;
    public static final int NAME = 0;
    public static final int HEIGHT = 3;
    public static final int MAXHEIGHT = 4;
    public static final int MINHEIGHT = 5;
    public static final int NORMALHEIGHT = 6;
    public static final int STARTHEIGHT = 7;
    public static final int WIDTH = 8;
    public static final int LENGTH = 9;
    private  final Pool pool;
    private final String name;
    private final double efficiency;
    private final double maxwaterflow;
    private final double minwaterflow;
    private final double height;
    protected DoubleProperty turbineFlow = new SimpleDoubleProperty();
    protected DoubleProperty powerMW = new SimpleDoubleProperty();
    protected DoubleProperty specificInflow = new SimpleDoubleProperty();
    private Powerplant next;
    private double energy;

    public Powerplant(String name, double maxwaterflow, double minwaterflow, double height,
                      double maxHeight, double minHeight, double normalHeight, double startHeight,
                      double width, double length) {
        this.efficiency = 0.8;
        this.name = name;
        this.maxwaterflow = maxwaterflow;
        this.minwaterflow = minwaterflow;
        this.height = height;
        this.next = null;
        this.pool = new Pool(null, length, width, startHeight, minHeight, maxHeight, normalHeight);
    }

    public void processFlow(double incomingFlow, double delta) {
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

    public Pool getPool() {
        return pool;
    }

    public void setNext(Powerplant next) {
        this.next = next;
        if (next != null) {
            this.pool.setNext(next.getPool());
        }
    }
}