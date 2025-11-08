package dev.luggers;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Simulation {
	protected IntegerProperty timeMult = new SimpleIntegerProperty(20);
    private int tempTimeMult;
	protected DoubleProperty money = new SimpleDoubleProperty();
	protected DoubleProperty totalPowerMW = new SimpleDoubleProperty();
	private Powerplant start;
	private SimulationClock simulationClock = new SimulationClock();
	private InflowRepository inflowRepository = new InflowRepository();
	private EnergyPriceRepository energyPriceRepository = new EnergyPriceRepository();
	private SaveManager saveManager = new SaveManager();

	Simulation() {
	}

	public void nextTick(double delta) {
		delta *= timeMult.get();
		simulationClock.time(delta);
		initiateFlow(delta);
		getRevenue();
		settotalPower();
		save();
	}

	public void startUp() {
		saveManager.startUp(this);

	}
    public void pause(){
        tempTimeMult = timeMult.get();
        timeMult.set(0);
    }
    public void resume(){
       timeMult.set(tempTimeMult);
    }
	public void save() {
		saveManager.save(this);
	}

	public void initiateFlow(double delta) {
		start.processFlow(getInflow(), delta);
	}

	public void getRevenue() {
		double totalEnergy = 0;
		for (int i = 0; i < start.getLength(); i++) {
			totalEnergy += start.getNext(i).collectEnergy();
		}
		double revenue = totalEnergy * getPrice();
		money.set(money.get() + revenue);
	}

	public void settotalPower() {
		double totalPower = 0;
		for (int i = 0; i < start.getLength(); i++) {
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
		return start.getNext(i);
	}

	public void startUpClock(double time) {
		simulationClock.startupClock(time);
	}

	public SimulationClock getSimulationClock() {
		return simulationClock;
	}

	public InflowRepository getInflowRepository() {
		return inflowRepository;
	}

	public EnergyPriceRepository getEnergyPriceRepository() {
		return energyPriceRepository;
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

	public void setStart(Powerplant start) {
		this.start = start;
	}

}