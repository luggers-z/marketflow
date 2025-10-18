package dev.luggers;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

public class InflowRepository {
    private Path inflowPath;
    private List<String> lines;

    public InflowRepository() {
        try {
            inflowPath = Paths.get((Objects.requireNonNull(getClass().getResource("/inflow.csv"))).toURI());
        } catch (URISyntaxException e) {
         e.printStackTrace();
        }
        try {
            lines = Files.readAllLines(inflowPath);
        } catch (IOException e) {
           e.printStackTrace();
        }
    }

    public double getInflow(double fullTime) {
        int fullHours = (int) Math.floor(fullTime / 3600);
        double inflow;
        if (fullHours == 0 || lines.size() < fullHours) {
            String line = lines.get(fullHours);
            String[] values = line.split(";");
            inflow = Double.parseDouble(values[1].replace(",", "."));
        } else {
            fullHours = fullHours - lines.size();
            return getInflow(fullHours * 3600);
        }
        return inflow;
    }
}
