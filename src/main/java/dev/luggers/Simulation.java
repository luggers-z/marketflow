package dev.luggers;

public class Simulation {
    public Kraftwerk test = new Kraftwerk(null,new Speicherbecken(null,1,1,1,1,1),1,1);
    int timemult = 672;
    int day=0;
    int hour=0;
    int week=0;
    long startTime;
    long currentTime;
    double geld;
    double[] strompreis = new double[13];
    double[] wasserfluss = new double[13];
    Simulation(){
    }
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
    public void nextTick(double delta){
        time();
        fluss(delta);
    }
    public void fluss(double delta){
        geld+=test.fluss(zufluss(),strompreis(),delta);
    }
    public double strompreis(){
        return strompreis[day];
    }
    public double zufluss(){
        return wasserfluss[day];
    }
    public void time(){
        currentTime=(System.nanoTime()/1000000000-startTime)*timemult;
        if(currentTime>=3600){
            hour++;
            currentTime=currentTime-3600;
        }
        if (hour>=24){
            day++;
            hour=hour-24;
        }
        if (day==8){
            day=0;
            week=1;
        }
    }
}
