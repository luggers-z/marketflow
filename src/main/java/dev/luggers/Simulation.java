package dev.luggers;

public class Simulation {
    public Kraftwerk test = new Kraftwerk(null,new Speicherbecken());

    int day;
    double startTime;
    double currentTime;
    Simulation(){
        day=0;

    };
    public void setStartTime(double startTime) {
        this.startTime = startTime;
    }
    public void nextTick(){
        currentTime=startTime- (double) System.nanoTime() /1000000;

    }
}
