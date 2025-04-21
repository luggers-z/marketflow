package dev.luggers;
import javafx.application.Application;
import javafx.stage.Stage;

public class Controller extends Application {
    Simulation simulation = new Simulation();
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        simulation.setStartTime(System.nanoTime());
    }

}

