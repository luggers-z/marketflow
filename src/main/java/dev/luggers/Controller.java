package dev.luggers;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;

public class Controller extends Application {
    private long lastUpdate=0;
    Simulation simulation = new Simulation();
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        simulation.setStartTime(System.nanoTime());
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (lastUpdate > 0) {
                    double delta = (now - lastUpdate)/1000000000.0;
                    simulation.nextTick(delta);
                    //render
                }
                lastUpdate = now;
            }
        };
        timer.start();;
    }

}

