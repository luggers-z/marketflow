package dev.luggers;


import javafx.fxml.FXMLLoader;
import javafx.animation.AnimationTimer;
import javafx.stage.Stage;
import javafx.scene.Scene;



public class Application extends javafx.application.Application {
    Simulation simulation = new Simulation();
    private long lastUpdate = 0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/firstDraft.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Controller controller = fxmlLoader.getController();
        controller.startUp(simulation);
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
        UIUpdater uiUpdater = new UIUpdater(simulation, controller);
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (lastUpdate > 0) {
                    double delta = (now - lastUpdate) / 1000000000.0;
                    if (delta<0.25){
                        return;
                    }
                    if (delta>0.25)
                    {
                        delta = 0.25;
                    }
                    System.out.println(delta);
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