package dev.luggers;


import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Simulation {
    public Powerplant start = new Powerplant();
    protected IntegerProperty timeMult = new SimpleIntegerProperty(20);

    SimulationClock simulationClock = new SimulationClock();
    InflowRepository inflowRepository = new InflowRepository();
    EnergyPriceRepository energyPriceRepository = new EnergyPriceRepository();
    SaveManager saveManager = new SaveManager();

    protected DoubleProperty money= new SimpleDoubleProperty();
    protected DoubleProperty totalPowerMW = new SimpleDoubleProperty();

    Simulation() {
    }
    public void nextTick(double delta) {
        delta *= timeMult.get();
        System.out.println(timeMult.get());
        simulationClock.time(delta);
        initiateFlow(delta);
        getRevenue();
        settotalPower();
        save();
    }

    public void startUp() {
        saveManager.startUp(this);
    }

    public void save() {
        saveManager.save(this);
    }

    public void initiateFlow(double delta) {
        start.processFlow(getInflow(), delta);
    }
    public void getRevenue() {
        double totalEnergy=0;
        for(int i =0; i<start.getLength(); i++){
            totalEnergy += start.getNext(i).collectEnergy();
        }
        double revenue = totalEnergy * getPrice();
        money.set(money.get()+revenue);
    }
    public void settotalPower() {
        double totalPower=0;
        for(int i =0; i<start.getLength(); i++){
            totalPower += start.getNext(i).powerMW.get();
        }
        totalPowerMW.set(totalPower);
    }
    public double getPrice() {
        return energyPriceRepository.getPrice(simulationClock.gettotalTime());
    }

    public double getInflow() {
        return inflowRepository.getInflow(simulationClock.gettotalTime());
    }

    public Powerplant getPowerplant(int i) {
        if (i == 0) {
            return start;
        }
        return start.getNext(i - 1);
    }

    public double getMoney() {
        return money.get();
    }

    public void setMoney(double cash) {
        money.set(cash);
    }

    public Powerplant getStart() {
        return start;
    }
    public void started(){

    }
}