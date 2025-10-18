package dev.luggers;

public class SimulationClock {
    private int timemult = 1;
    private int weekday = 0;
    private int day = 0;
    private int hour = 0;
    private int week = 0;
    private double currentTime;
    private double totalTime =0;

    public void time(double delta) {
        delta *= timemult;
        currentTime += delta;
        totalTime += delta;
        while (currentTime >= 3600) {
            hour++;
            currentTime -= 3600;
        }
        if (hour >= 24) {
            weekday++;
            day++;
            hour = hour - 24;

        }
        if (day == 8) {
            weekday = 0;
            week = 1;
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

