package dev.luggers;

import javafx.scene.control.Alert;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class SaveManager {
    public static final String KRAFTWERKVOLUMEN = "kraftwerksvolumen Nr. %d";
    Path savePath;

    public SaveManager() {
        savePath = Paths.get(System.getProperty("user.home"), "myapp", "save.properties");
    }

    public void startUp(Simulation simulation) {
        Properties prop = new Properties();
        if (Files.exists(savePath)) {
            try {
                prop.load(Files.newInputStream(savePath));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (!prop.isEmpty()) {
                simulation.setMoney(Double.parseDouble(prop.getProperty("money")));
                simulation.simulationClock.setTime(Double.parseDouble(prop.getProperty("totalTime")));
                int length = simulation.start.getLength();
                for (int i = 0; i < length; i++) {
                    Kraftwerk KWi = simulation.getPowerplant(i);
                    KWi.pool.setVolume(Double.parseDouble(prop.getProperty(KRAFTWERKVOLUMEN.formatted(i))));
                }
            }
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

    public void save(Simulation simulation) {
        Properties prop = new Properties();
        prop.setProperty("totalTime", String.valueOf(simulation.simulationClock.gettotalTime()));
        prop.setProperty("money", String.valueOf(simulation.getMoney()));
        int length = simulation.getStart().getLength();
        for (int i = 0; i < length; i++) {
            Kraftwerk KWi = simulation.getPowerplant(i);
            prop.setProperty(KRAFTWERKVOLUMEN.formatted(i), String.valueOf(KWi.pool.getVolume()));
        }

        try {
            prop.store(Files.newOutputStream(savePath), "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
