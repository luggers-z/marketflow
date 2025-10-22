package dev.luggers;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class SimulationClock {
    private int timemult = 2000;
    private IntegerProperty hour = new SimpleIntegerProperty();
    public IntegerProperty hourProperty() { return hour; }
    private double currentTime;
    private double totalTime =0;

    public void time(double delta) {
        delta *= timemult;
        currentTime += delta;
        totalTime += delta;
        while (currentTime >= 3600) {
            hour.set(hour.get()+1);
            currentTime -= 3600;
        }
    }

    public double gettotalTime(){
        return totalTime;
    }
    public void setTime(double totalTime){
        this.totalTime = totalTime;
        this.currentTime = totalTime;
    }

}
