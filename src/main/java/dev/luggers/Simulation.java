package dev.luggers;

public class Simulation {
    public Kraftwerk test = new Kraftwerk(null,new Speicherbecken());

    int day;
    long startTime;
    long currentTime;
    Simulation(){
        day=0;
    };
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
    public void nextTick(){
        currentTime=(startTime-System.nanoTime())/1000000;

    }
}
