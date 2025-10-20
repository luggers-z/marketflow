package dev.luggers;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.beans.binding.*;
import javafx.beans.property.*;

import java.util.ArrayList;


public class Controller extends Application {
    Simulation simulation = new Simulation();
    private long lastUpdate = 0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/firstDraft.fxml"));
        //VBox root = new VBox();
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        /*Slider slider1 = new Slider(simulation.start.getMinWaterflow(), simulation.start.getMaxWaterflow(), simulation.getInflow());
        VBox root = new VBox();
        slider1.valueProperty().bindBidirectional(simulation.start.turbineFlow);
        slider1.valueProperty().addListener((obs, oldVal, newVal) -> {
            System.out.println("Slider value changed to: " + newVal);
        });
        root.getChildren().addAll(slider1);


        ArrayList<VBox> Powerplants = new ArrayList<>();
        int length = simulation.start.getLength();
        for (int i = 0; i < length; i++) {
            VBox vbox = new VBox();
            Kraftwerk KWi = simulation.getPowerplant(i);
            Slider slider = new Slider(KWi.getMinWaterflow(), KWi.getMaxWaterflow(), simulation.getInflow());
            slider.valueProperty().bindBidirectional(KWi.turbineFlow);
            vbox.getChildren().add(slider);
            Powerplants.add(vbox);
        }
        */

        simulation.startUp();
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (lastUpdate > 500000000) {
                    double delta = (now - lastUpdate) / 1000000000.0;
                    simulation.nextTick(delta);
                }
                lastUpdate = now;
            }
        };
        timer.start();
        ;
        primaryStage.setTitle("Simulation Output");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


}
