package dev.luggers;

import javafx.scene.control.Alert;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.nio.file.*;
import java.util.Properties;


public class Simulation {
    public Kraftwerk start = new Kraftwerk();
    int timemult = 672;
    int weekday = 0;
    int day = 0;
    int hour = 0;
    int fullHours;
    int week = 0;
    double currentTime;
    double fullTime;

    double money;
    double[] price = new double[14];
    double[] inflow = new double[14];

    Simulation() {
    }

    public void startUp() {
        Path savePath = Paths.get("resources/save.properties");
        Properties prop = new Properties();
        if (Files.exists(savePath)) {

            try {
                prop.load(Files.newInputStream(savePath));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            money = Double.parseDouble(prop.getProperty("money"));
            fullTime = Double.parseDouble(prop.getProperty("fullTime"));
            currentTime = fullTime;
            int length = start.getLength();
            for (int i = 0; i < length; i++) {
                Kraftwerk KWi = getPowerplant(i);
                KWi.pool.setVolume(Double.parseDouble(prop.getProperty("Kraftwerkvolumen" + i)));
            }
        } else {
            try {
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
        Path savePath = Paths.get("resources/save.properties");
        Properties prop = new Properties();
        prop.setProperty("time", String.valueOf(fullTime));
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
        double revenue = start.collectEnergy() * getPrice();
        money += revenue;
    }

    public double getPrice() {
        /* Path pricePath = Paths.get("resources/energyPrice.csv");

         */
        return 0.20;
    }

    public double getInflow() {
        /* Path inflowPath = Paths.get("resources/inflow.csv");

         */
        return 250;
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
