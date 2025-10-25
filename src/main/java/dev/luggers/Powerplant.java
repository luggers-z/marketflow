package dev.luggers;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

/**
 * Powerplant is ...
 */
public class Powerplant {
    public static final long MEGA_HOURS = 3600000000L;
    protected final Pool pool;
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
    private Path path;
    private List<String> lines;

    public Powerplant(int i) {
        efficiency = 0.8;
        try {
            path = Paths.get((Objects.requireNonNull(getClass().getResource("/powerplants.csv"))).toURI());
        } catch (URISyntaxException e) {
        }
        try {
            lines = Files.readAllLines(path);
        } catch (Exception e) {
        }
        String line = lines.get(i);
        String[] values = line.split(";");
        name = values[0];
        maxwaterflow = Double.parseDouble(values[1]);
        minwaterflow = Double.parseDouble(values[2]);
        height = Double.parseDouble(values[3]);
        next = null;
        double maxHeight = Double.parseDouble(values[4]);
        double minHeight = Double.parseDouble(values[5]);
        double normalHeight = Double.parseDouble(values[6]);
        double startHeight = Double.parseDouble(values[7]);
        double width = Double.parseDouble(values[8]);
        double length = Double.parseDouble(values[9]);
        pool = new Pool(null, length, width, startHeight, minHeight, maxHeight, normalHeight);
        if (i < lines.size() - 1) {
            next = new Powerplant(i + 1);
            pool.setNext(next.getPool());

        }

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
}