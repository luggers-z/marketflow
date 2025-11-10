package dev.luggers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class InflowRepository {
	protected DoubleProperty inflow = new SimpleDoubleProperty();
	private Path inflowPath;
	private List<String> lines;

	public InflowRepository() {
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(Objects.requireNonNull(
                            getClass().getResourceAsStream("/dev/luggers/data/inflow.csv"))));

            lines = reader.lines().collect(Collectors.toList());
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
	}

	public double getInflow(double fullTime) {
		int fullHours = (int) Math.floor(fullTime / 3600);
		int index = (fullHours * 4) % lines.size();

		String line = lines.get(index).trim();
		if (line.isEmpty()) {
			return getInflow(fullTime + 3600);
		}

		String[] values = line.split(";");
		String numeric = values[1];
		if (numeric.isEmpty()) {
			return getInflow(fullTime + 3600);
		}

		inflow.set(Double.parseDouble(values[1].replace(",", ".")));
		return inflow.get();
	}
}