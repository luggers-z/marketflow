package dev.luggers;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class SimulationClock {
    private int timemult = 180;
    private int weekday = 0;
    private int day = 0;
    public IntegerProperty hour = new SimpleIntegerProperty();

    private int week = 0;
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
        if (hour.get() >= 24) {
            weekday++;
            day++;
            hour.subtract(24);

        }
        if (day == 8) {
            weekday = 0;
            week += 1;
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
