package dev.luggers;


import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;


public class Application extends javafx.application.Application {

    private long lastUpdate = 0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/firstDraft.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Controller controller = fxmlLoader.getController();
        controller.startUp();
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.LEFT) {
                    controller.tabLeft();
                }
                if (event.getCode() == KeyCode.RIGHT) {
                    controller.tabRight();
                }
            }
        });

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (lastUpdate > 0) {
                    double delta = (now - lastUpdate) / 1000000000.0;
                    if (delta < 0.25) {
                        return;
                    }
                    if (delta > 0.25) {
                        delta = 0.25;
                    }
                    scene.getRoot().requestFocus();
                    System.out.println(delta);
                    controller.nextTick(delta);
                }
                lastUpdate = now;
            }
        };
        timer.start();
        primaryStage.setTitle("Simulation Output");
        primaryStage.setScene(scene);
        primaryStage.show();
        scene.getRoot().requestFocus();
    }


}