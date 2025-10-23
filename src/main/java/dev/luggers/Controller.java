package dev.luggers;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;


public class Controller {
    Simulation simulation;
    @FXML private StackPane stackPane;
    @FXML private ImageView background;
    @FXML private AnchorPane mainPane;
    @FXML private StackPane bottomPane;
    @FXML private VBox elementField;
    @FXML private HBox chartBox;
    @FXML private TextField moneyField;
    @FXML private TextField powerField;
    @FXML private TextField inflowField;
    @FXML private Label timeLabel;
@FXML private HBox timeMultBox;

    @FXML private LineChart<Number, Number> rightChart;
    @FXML private LineChart<Number, Number> leftChart;

    @FXML private Spinner<Integer> timeMultSpinner;

    @FXML
    public void initialize() {


        background.setPreserveRatio(true);
        background.fitWidthProperty().bind(stackPane.widthProperty());
        background.fitHeightProperty().bind(stackPane.heightProperty());

        allBind(mainPane,stackPane,1,1);
        allBind(bottomPane,stackPane,1,0.1);



        chartBox.prefHeightProperty().bind(mainPane.heightProperty().multiply(0.2));
        leftChart.prefWidthProperty().bind(chartBox.heightProperty());
        leftChart.minWidthProperty().bind(leftChart.prefWidthProperty());
        leftChart.maxWidthProperty().bind(leftChart.prefWidthProperty());
        rightChart.prefWidthProperty().bind(chartBox.heightProperty());
        rightChart.minWidthProperty().bind(rightChart.prefWidthProperty());
        rightChart.maxWidthProperty().bind(rightChart.prefWidthProperty());


    }
    public void startUp(Simulation sim){
        simulation = sim;
        int length = simulation.start.getLength();
        for (int i = 0; i < length; i++) {
            TabPane tabPane = new TabPane();
            tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

            Tab tab1 = new Tab("Controls");
            Tab tab2 = new Tab("Information");

            AnchorPane anchorPane = new AnchorPane();

            Powerplant kwI = simulation.getPowerplant(i);

            Slider slider = new Slider(kwI.getMinWaterflow(), kwI.getMaxWaterflow(), simulation.getInflow());
            sliderConfig(slider);
            slider.valueProperty().bindBidirectional(kwI.turbineFlow);


            Label inflow = new Label();
            inflow.textProperty().bind(slider.valueProperty().asString("Durchfluss: %.02f m³/s "));



            Label height = new Label();
            height.textProperty().bind(kwI.pool.height.asString("Stauhöhe: %.2f m "));

            Label power = new Label();
            power.textProperty().bind(kwI.powerMW.asString("Leistung: %.2f Megawatt"));



            HBox controlBox = new HBox();
            controlBox.setAlignment(Pos.CENTER_LEFT);
            controlBox.getChildren().addAll(inflow,slider);

            HBox variableBox = new HBox();
            variableBox.setAlignment(Pos.CENTER_LEFT);
            variableBox.getChildren().addAll(height, power);

            VBox boxCointainer = new VBox();
            boxCointainer.setStyle("-fx-background-color: white;");
            boxCointainer.getChildren().addAll(controlBox, variableBox);
            anchorPane.getChildren().add(boxCointainer);
            AnchorPane.setTopAnchor(boxCointainer, 10.0);
            AnchorPane.setLeftAnchor(boxCointainer, 10.0);

            tab1.setContent(anchorPane);
            tabPane.getTabs().add(tab1);
            tabPane.getTabs().add(tab2);

            tabPane.setStyle("-fx-background-color: white;");

            bottomPane.getChildren().add(tabPane);
        }
        timeMultBox.toFront();

        elementFieldConfig(simulation);

        spinnerConfig();
    }
    private void spinnerConfig() {
        timeMultSpinner.setEditable(true);
        timeMultSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10000, 20, 2));

        simulation.timeMult.bind(timeMultSpinner.getValueFactory().valueProperty());
        timeMultSpinner.getEditor().setPrefColumnCount(5);
    }
    private void sliderConfig(Slider slider){
        slider.setMajorTickUnit(50);
        slider.setMinorTickCount(24);
        slider.setSnapToTicks(true);
        slider.setStyle("-fx-background-color: white;");
        enableSliderColor(slider);
    }
    private void elementFieldConfig(Simulation sim){
        elementField.getStyleClass().add("elementField");
        moneyField.setEditable(false);
        powerField.setEditable(false);
        inflowField.setEditable(false);
        moneyField.textProperty().bind(simulation.money.asString("Kontostand: %,.0f €"));
        powerField.textProperty().bind(simulation.totalPowerMW.asString("Totaleistung: %.2f MW"));
        inflowField.textProperty().bind(simulation.inflowRepository.inflow.asString("Zufluss: %.0f m³/s"));
    }
    private void enableSliderColor(Slider slider) {
        slider.skinProperty().addListener((obs, oldSkin, newSkin) -> {
            if (newSkin == null) return;

            Platform.runLater(() -> {
                StackPane track = (StackPane) slider.lookup(".track");
                if (track == null) return;

                track.setStyle("-fx-background-color: linear-gradient(to right, #005b96 0%, #b3cde0 0%);");

                slider.valueProperty().addListener((obsVal, oldVal, newVal) -> {
                    double percent = (newVal.doubleValue() - slider.getMin()) /
                            (slider.getMax() - slider.getMin()) * 100.0;
                    String style = String.format(
                            java.util.Locale.US,
                            "-fx-background-color: linear-gradient(to right, #005b96 %.2f%%, #b3cde0 %.2f%%);",
                            percent, percent
                    );
                    track.setStyle(style);
                });
            });
        });
    }

    @FXML
    public void heightBind(Pane paneRoot, Pane paneParent, double multiplier){
        paneRoot.minHeightProperty().bind(paneParent.heightProperty().multiply(multiplier));
        paneRoot.maxHeightProperty().bind(paneParent.heightProperty().multiply(multiplier));
    }
    @FXML
    public void widthBind(Pane paneRoot, Pane paneParent, double multiplier){
        paneRoot.minWidthProperty().bind(paneParent.widthProperty().multiply(multiplier));
        paneRoot.maxWidthProperty().bind(paneParent.widthProperty().multiply(multiplier));
    }
    @FXML
    public void allBind(Pane paneRoot, Pane paneParent, double multiplierWidth , double multiplierHeight){
        widthBind(paneRoot,paneParent, multiplierWidth);
        heightBind(paneRoot,paneParent, multiplierHeight);
    }
    public LineChart getRightChart() {
        return rightChart;
    }
    public LineChart getLeftChart() {
        return leftChart;
    }
    public Label getTimeLabel(){
        return timeLabel;
    }
}