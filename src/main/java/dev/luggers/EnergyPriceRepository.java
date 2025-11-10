package dev.luggers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class EnergyPriceRepository {
	private final List<String> lines;

	public EnergyPriceRepository() {
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(Objects.requireNonNull(
						getClass().getResourceAsStream("/dev/luggers/data/energyPrice.csv"))))) {
			lines = reader.lines().collect(Collectors.toList());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public double getPrice(double fullTime) {
		int fullHours = (int) Math.floor(fullTime / 3600);
		double price;
		if (lines.size() <= fullHours) {
			fullHours = fullHours % lines.size();
		}
		String line = lines.get(fullHours);
		String[] values = line.split(";");
		price = Double.parseDouble(values[2].replace(",", "."));
		return price;
	}
}