package dev.luggers;

public class Speicherbecken {
    double length;
    double width;
    double height;
    double volume;
    double minh;
    double maxh;
    double maxVolume;
    double minVolume;
    Speicherbecken next;

    Speicherbecken(Speicherbecken next, double length, double width, double height, double minh, double maxh) {
        this.next = next;
        this.length = length;
        this.width = width;
        this.height = height;
        this.volume = length * width * height;
        this.minh = minh;
        this.maxh = maxh;
        maxVolume = this.length * width * maxh;
        minVolume = this.length * height * minh;
    }

    Speicherbecken() {
        next = null;
        length = 20000;
        width = 15;
        height = 4;
        minh = 3;
        maxh = 5;
        volume = length * width * height;
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
        height = volume / width / length;
    }

    public boolean isFull() {
        return volume == maxVolume;
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

    public void setVolume(double volume) {
        this.volume = volume;
    }
}
