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
    int timemult = 1;
    int weekday = 0;
    int day = 0;
    int hour = 0;
    int fullHours=0;
    int week = 0;
    double currentTime;
    double fullTime;

    double money;
    double[] price = new double[14];
    double[] inflow = new double[14];

    Simulation() {
    }

    public void startUp() {
        Path savePath;
        savePath = Paths.get(System.getProperty("user.home"), "myapp", "save.properties");
        Properties prop = new Properties();
        if (Files.exists(savePath)) {
            try {
                prop.load(Files.newInputStream(savePath));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (!prop.isEmpty()){
            money = Double.parseDouble(prop.getProperty("money"));
            fullTime = Double.parseDouble(prop.getProperty("fullTime"));
            currentTime = fullTime;
            int length = start.getLength();
            for (int i = 0; i < length; i++) {
                Kraftwerk KWi = getPowerplant(i);
                KWi.pool.setVolume(Double.parseDouble(prop.getProperty("kraftwerkvolumen" + i)));
            }}
        } else {
            try {
                Files.createDirectories(savePath.getParent());
                Files.createFile(savePath);
            } catch (IOException e) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Fehler");
                error.setHeaderText("Datei konnte nicht erstellt werden, bitte starten sie die Simulation im Administator-Modus");
                error.setContentText(e.getMessage());
                error.showAndWait();
            }
        }
    }

    public void save() {
        Path savePath;
        savePath = Paths.get(System.getProperty("user.home"), "myapp", "save.properties");
        Properties prop = new Properties();

        prop.setProperty("fullTime", String.valueOf(fullTime));
        prop.setProperty("money", String.valueOf(money));
        int length = start.getLength();
        for (int i = 0; i < length; i++) {
            Kraftwerk KWi = getPowerplant(i);
            prop.setProperty("kraftwerkvolumen" + i, String.valueOf(KWi.pool.getVolume()));
        }

        try {
            prop.store(Files.newOutputStream(savePath), "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        prop.list(System.out);
    }

    public void nextTick(double delta) {
        delta = delta * timemult;
        time(delta);
        initiateFlow(delta);
        getRevenue();
        save();
    }

    public void initiateFlow(double delta) {
        start.processFlow(getInflow(), delta);
    }

    public void getRevenue() {
        double revenue = start.collectEnergy() * getPrice();
        money += revenue;
    }

    public double getPrice() {
        double price = 0.4;


        Path pricePath;
        try {
            pricePath = Paths.get((getClass().getResource("/energyPrice.csv")).toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        try {
            List<String> lines = Files.readAllLines(pricePath);
            String line = lines.get(fullHours);
            String[] values = line.split(";");
            price = Double.parseDouble(values[2].replace(",", "."));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return price;
    }

    public double getInflow() {
        double inflow = 100;

        Path inflowPath;
        try {
            inflowPath = Paths.get((getClass().getResource("/inflow.csv")).toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        try {
            List<String> lines = Files.readAllLines(inflowPath);
            String line = lines.get(fullHours);
            String[] values = line.split(";");
            inflow = Double.parseDouble(values[1].replace(",", "."));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return inflow;
    }

    public void time(double delta) {
        currentTime += delta;
        fullTime += delta;
        while (currentTime >= 3600) {
            hour++;
            currentTime -= 3600;
        }
        if (hour >= 24) {
            weekday++;
            day++;
            fullHours++;
            hour = hour - 24;

        }
        if (day == 8) {
            weekday = 0;
            week = 1;
        }
    }

    public Kraftwerk getPowerplant(int i) {
        if (i == 0) {
            return start;
        }
        return start.getNext(i - 1);
    }
}
