package dev.luggers;
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
    private long lastUpdate = 0;
    Simulation simulation = new Simulation();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {


        TextArea textArea = new TextArea(null);
        TextArea textArea2 = new TextArea(null);
        TextArea textArea3 = new TextArea(null);
        TextArea textArea4 = new TextArea(null);
        TextArea textArea5 = new TextArea(null);
        textArea.setEditable(false);
        textArea2.setEditable(false);
        textArea3.setEditable(false);
        textArea4.setEditable(false);
        textArea5.setEditable(false);
        Slider slider1 =new Slider(simulation.start.getMinWaterflow(), simulation.start.getMaxWaterflow(), simulation.getInflow());
        VBox root = new VBox();
        slider1.valueProperty().bindBidirectional(simulation.start.turbineFlow);
        slider1.valueProperty().addListener((obs, oldVal, newVal) -> {
            System.out.println("Slider value changed to: " + newVal);
        });
        root.getChildren().addAll(textArea, textArea2, textArea3, textArea4, textArea5, slider1);









        ArrayList<VBox> Powerplants = new ArrayList<>();
        int length = simulation.start.getLength();
        for (int i=0; i<length; i++) {
            VBox vbox = new VBox();
            Kraftwerk KWi= simulation.getPowerplant(i);
            Slider slider= new Slider(KWi.getMinWaterflow(), KWi.getMaxWaterflow(), simulation.getInflow());
            slider.valueProperty().bindBidirectional(KWi.turbineFlow);
            vbox.getChildren().add(slider);
            Powerplants.add(vbox);
        }
        Scene scene = new Scene(Powerplants.getFirst(), 600, 400);
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (lastUpdate > 0) {
                    double delta = (now - lastUpdate) / 1000000000.0;

                    simulation.nextTick(delta);


                    textArea.setText("Zeit in Sekunden:" + String.valueOf(simulation.currentTime));
                    textArea2.setText("Zeit in Stunden: " + String.valueOf(simulation.hour));
                    textArea3.setText("Zeit in Tagen: " + String.valueOf(simulation.day));
                    textArea4.setText("Geld in Euro: " + String.valueOf(simulation.money));
                    textArea5.setText("Höhe des Flusses" + String.valueOf(simulation.start.pool.height));


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
