package dev.luggers;

import javafx.scene.control.Alert;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Properties;

public class SaveManager {
    private static final String KRAFTWERKVOLUMEN = "kraftwerksvolumenNr.%d";
    private Path savePath;

    public SaveManager() {
        savePath = Paths.get(System.getProperty("user.home"), ".marketflow", "save.properties");
    }

    public void startUp(Simulation simulation) {
        Properties prop = new Properties();
        boolean changed = false;
        if (Files.exists(savePath)) {
            try {
                prop.load(Files.newInputStream(savePath));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (propertyCheck(prop, "money")) changed = true;
            simulation.setMoney(Double.parseDouble(prop.getProperty("money")));
            if (propertyCheck(prop, "totalTime")) changed = true;
            ;
            simulation.getSimulationClock().startupClock(Double.parseDouble(prop.getProperty("totalTime")));
            int length = simulation.getStart().getLength();
            for (int i = 0; i < length; i++) {
                Powerplant KWi = simulation.getPowerplant(i);
                if (propertyCheck(prop, KRAFTWERKVOLUMEN.formatted(i))) changed = true;
                ;
                KWi.getPool().setVolume(Double.parseDouble(prop.getProperty(KRAFTWERKVOLUMEN.formatted(i))));
            }
            if (changed) {
                saveStore(prop);
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

    private void saveStore(Properties prop) {
        try {
            prop.store(
                    Files.newOutputStream(savePath,
                            StandardOpenOption.CREATE,
                            StandardOpenOption.TRUNCATE_EXISTING,
                            StandardOpenOption.WRITE),
                    ""
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean propertyCheck(Properties prop, String name) {


        if (!existsProperty(prop, name)) {
            prop.setProperty(name, "0");
            return true;
        } else if (!isParsable(prop, name)) {
            prop.setProperty(name, "0");
            return true;
        }

        return false;
    }

    private boolean isParsable(Properties prop, String property) {
        try {
            Double.parseDouble(prop.getProperty(property));
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private boolean existsProperty(Properties prop, String property) {
        return prop.getProperty(property) != null;

    }

    public void save(Simulation simulation) {
        Properties prop = new Properties();
        prop.setProperty("totalTime", String.valueOf(simulation.getSimulationClock().gettotalTime()));
        prop.setProperty("money", String.valueOf(simulation.getMoney()));
        int length = simulation.getStart().getLength();
        for (int i = 0; i < length; i++) {
            Powerplant KWi = simulation.getPowerplant(i);
            prop.setProperty(KRAFTWERKVOLUMEN.formatted(i), String.valueOf(KWi.getPool().getVolume()));
        }

        saveStore(prop);
    }
}
