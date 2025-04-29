package dev.luggers;

public class Simulation {
    public Kraftwerk test = new Kraftwerk(null,new Speicherbecken(null,20000,15,3,0.01,4),1.5,0.8, 1, 1);
    int timemult = 672;
    int weekday=0;
    int day=0;
    int hour=0;
    int week=0;
    double currentTime;
    double geld;
    double[] strompreis = new double[13];
    double[] wasserfluss = new double[13];
    Simulation(){
    }
    public void nextTick(double delta){
        delta=delta*timemult;
        time(delta);
        fluss(delta);
    }
    public void fluss(double delta){
        geld+=test.fluss(zufluss(),strompreis(),delta);
    }
    public double strompreis(){
        //return strompreis[day];
        return 0.28;
    }
    public double zufluss(){
        //return wasserfluss[day];
        return 500;
    }
    public void time(double delta){
        currentTime += delta;

        while (currentTime >= 3600) {
            hour++;
            currentTime -= 3600;
        }
        if (hour>=24){
            weekday++;
            day++;
            hour=hour-24;
        }
        if (day==8){
            weekday=0;
            week=1;
        }
    }
}
