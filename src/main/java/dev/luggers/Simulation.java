package dev.luggers;

public class Simulation {
	public Kraftwerk start = new Kraftwerk();
	int timemult = 672;
	int weekday = 0;
	int day = 0;
	int hour = 0;
	int week = 0;
	double currentTime;
	double money;
	double[] price = new double[14];
	double[] inflow = new double[14];

	Simulation() {
	}

	public void nextTick(double delta) {
		delta = delta * timemult;
		time(delta);
		initiateFlow(delta);
		getRevenue();

	}

	public void initiateFlow(double delta) {
		start.processFlow(getInflow(), delta);
	}
    public void getRevenue() {
		double revenue=start.collectEnergy()*getPrice();
		money+=revenue;
	}
	public double getPrice() {
		// return price[day];
		return 0.20;
	}

	public double getInflow() {
		// return inflow[day];
		return 250;
	}

	public void time(double delta) {
		currentTime += delta;

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
}
