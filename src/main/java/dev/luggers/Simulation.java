package dev.luggers;

import javafx.scene.control.Alert;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.List;
import java.util.Objects;
import java.util.Properties;


public class Simulation {
    public Kraftwerk start = new Kraftwerk();


    SimulationClock simulationClock = new SimulationClock();
    InflowRepository inflowRepository = new InflowRepository();
    EnergyPriceRepository energyPriceRepository = new EnergyPriceRepository();
    SaveManager saveManager = new SaveManager();

    private double money;


    Simulation() {
    }
    public void nextTick(double delta) {
        simulationClock.time(delta);
        initiateFlow(delta);
        getRevenue();
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
        money += revenue;
    }

    public double getPrice() {
        return energyPriceRepository.getPrice(simulationClock.gettotalTime());
    }

    public double getInflow() {
        return inflowRepository.getInflow(simulationClock.gettotalTime());
    }

    public Kraftwerk getPowerplant(int i) {
        if (i == 0) {
            return start;
        }
        return start.getNext(i - 1);
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public Kraftwerk getStart() {
        return start;
    }
    public void started(){

    }
}