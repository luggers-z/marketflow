package dev.luggers;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Pool {
    final double normalHeight;
    protected DoubleProperty height = new SimpleDoubleProperty();
    double length;
    double width;
    double volume;
    double minh;
    double maxh;
    double maxVolume;
    double minVolume;
    Pool next;

    Pool(Pool next, double length, double width, double h, double minh, double maxh, int normalHeight, double nHeight) {
        this.next = next;
        this.length = length;
        this.width = width;
        this.normalHeight = nHeight;
        height.set(h);
        this.volume = length * width * h;
        this.minh = minh;
        this.maxh = maxh;
        maxVolume = this.length * width * maxh;
        minVolume = this.length * width * minh;
    }

    Pool() {
        next = null;
        length = 20000;
        width = 15;
        height.set(4);
        minh = 3;
        maxh = 5;
        normalHeight = 3.5;
        volume = length * width * height.get();
        maxVolume = length * width * maxh;
    }

    public void processFlow(double inFlow) {
        if (isFull()) {
            if (next != null) {
                next.triggerSpillway(inFlow);
            }
        }
        double projectedVolume = volume + inFlow;
        if (projectedVolume < maxVolume) {
            volume = projectedVolume;
        } else {
            volume = maxVolume;
            if (next != null) {
                next.triggerSpillway(projectedVolume - maxVolume);
            }
        }
        height.set(volume / width / length);
    }

    public boolean isFull() {
        return volume >= maxVolume;
    }

    public boolean isEmptyif(double inFlow) {
        double projectedVolume = volume + inFlow;
        double projectedHeight = projectedVolume / length / width;
        if (projectedHeight <= minh) {
            volume = minh * width * length;
            return true;
        } else {
            return false;
        }
    }

    public void triggerSpillway(double inFlow) {
        next.processFlow(inFlow);
    }

    public double getVolume() {
        return volume;
    }

    public double getNormalHeight() {
        return normalHeight;
    }
    public void setVolume(double volume) {
        this.volume = volume;
    }
}
