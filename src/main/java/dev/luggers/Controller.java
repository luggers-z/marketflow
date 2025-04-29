package dev.luggers;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

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
        textArea.setEditable(false);
        textArea2.setEditable(false);
        textArea3.setEditable(false);
        textArea4.setEditable(false);
        VBox root = new VBox();
        root.getChildren().addAll(textArea,textArea2, textArea3, textArea4);
        Scene scene = new Scene(root, 600, 400);


        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (lastUpdate > 0) {
                    double delta = (now - lastUpdate) / 1000000000.0;

                        simulation.nextTick(delta);

                        textArea.setText(String.valueOf(simulation.currentTime));
                        textArea2.setText(String.valueOf(simulation.hour));
                        textArea3.setText(String.valueOf(simulation.day));
                        textArea4.setText(String.valueOf(simulation.geld));



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
