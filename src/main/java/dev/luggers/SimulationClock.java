package dev.luggers;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class SimulationClock {

	protected IntegerProperty totalHours = new SimpleIntegerProperty();
	protected IntegerProperty totalquarterHours = new SimpleIntegerProperty();
	private int quarterHour = 0;
	private double currentTime;
	private double totalTime = 0;

	public void time(double delta) {

		currentTime += delta;
		totalTime += delta;
		while (currentTime >= 900) {
			quarterHour += 1;
			totalquarterHours.set(totalquarterHours.get() + 1);
			currentTime -= 900;
			if (quarterHour >= 4) {
				totalHours.set(totalHours.get() + 1);
				quarterHour -= 4;
			}

		}

	}

	public double gettotalTime() {
		return totalTime;
	}

	public void startupClock(double totalTime1) {
		totalTime = totalTime1;
		if (currentTime >= 36000) {
			currentTime = totalTime1 - (10 * 3600);
			time(0);
			currentTime = 10 * 3600;
		} else {
			currentTime = totalTime1;
		}
	}

}
