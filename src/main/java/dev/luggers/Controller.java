package dev.luggers;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.ArrayList;
import java.util.List;


public class Controller {
    private Simulation simulation;
    private UIHelper uiHelper;
    private List<TabPane> paneList;
    int currentTab = 0;
    int prevTimeMult = 0;
    @FXML
    private AnchorPane buttonPane;
    @FXML
    private StackPane stackPane;
    @FXML
    private ImageView background;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private StackPane bottomPane;
    @FXML
    private VBox elementField;
    @FXML
    private HBox chartBox;
    @FXML
    private TextField moneyField;
    @FXML
    private TextField powerField;
    @FXML
    private TextField inflowField;
    @FXML
    private TextField timeLabel;
    @FXML
    private HBox timeMultBox;
    @FXML
    private LineChart<Number, Number> rightChart;
    @FXML
    private LineChart<Number, Number> leftChart;
    @FXML
    private Spinner<Integer> timeMultSpinner;
    @FXML
    private Button leftButton;
    @FXML
    private Button rightButton;
@FXML private Button pauseButton;
@FXML private Button startButton;

    @FXML
    private void onLeftClicked() {
        tabLeft();
    }

    @FXML
    private void onPauseClicked() {
        prevTimeMult = timeMultSpinner.getValue();
        timeMultSpinner.getValueFactory().setValue(0);
        startButton.setVisible(true);
        pauseButton.setVisible(false);
    }
    @FXML
    private void onStartClicked() {
        timeMultSpinner.getValueFactory().setValue(prevTimeMult);
        startButton.setVisible(false);
        pauseButton.setVisible(true);
    }

    @FXML
    private void onRightClicked() {
        tabRight();
    }

    @FXML
    public void initialize() {
         simulation = new Simulation();
        uiHelper = new UIHelper(simulation, this);
        background.fitWidthProperty().bind(Bindings.createDoubleBinding(() ->
                        stackPane.getWidth() * 1.01, // 1% overscale
                stackPane.widthProperty()));
        background.fitHeightProperty().bind(stackPane.heightProperty());
        background.setPreserveRatio(true);
        background.setSmooth(true);
        buttonPane.maxWidthProperty().bind(background.fitWidthProperty());
        buttonPane.maxHeightProperty().bind(background.fitHeightProperty());
        buttonPane.prefWidthProperty().bind(background.fitWidthProperty());
        buttonPane.prefHeightProperty().bind(background.fitHeightProperty());
        allBind(mainPane, stackPane, 1, 1);
        allBind(bottomPane, stackPane, 1, 0.1);


        chartBox.prefHeightProperty().bind(mainPane.heightProperty().multiply(0.2));
        leftChart.prefWidthProperty().bind(chartBox.heightProperty());
        leftChart.minWidthProperty().bind(leftChart.prefWidthProperty());
        leftChart.maxWidthProperty().bind(leftChart.prefWidthProperty());
        rightChart.prefWidthProperty().bind(chartBox.heightProperty());
        rightChart.minWidthProperty().bind(rightChart.prefWidthProperty());
        rightChart.maxWidthProperty().bind(rightChart.prefWidthProperty());


    }


    public void startUp() {
        simulation.startUp();

        int length = simulation.getStart().getLength();
        paneList = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            Powerplant kwI = simulation.getPowerplant(i);


            TabPane tabPane = new TabPane();
            tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

            Tab tab1 = new Tab("Controls");
            Tab tab2 = new Tab("Information");

            AnchorPane anchorPane = new AnchorPane();



            Slider slider = new Slider(kwI.getMinWaterflow(), kwI.getMaxWaterflow(), 0);
            sliderConfig(slider);
            slider.valueProperty().bindBidirectional(kwI.turbineFlow);
            slider.setValue(simulation.getInflow());

            Label throughFlow = new Label();
            throughFlow.textProperty().bind(slider.valueProperty().asString("Durchfluss: %.02f m³/s "));

            TextFlow textFlow = new TextFlow();
            Label inFlow = new Label();
            inFlow.textProperty().bind(kwI.specificInflow.asString("Zufluss: %.2f m³/s "));
            Text deltaInFlow = new Text();
            deltaInFlow.textProperty().bind(Bindings.subtract(kwI.specificInflow, kwI.turbineFlow).asString("[%+.2f] "));
            kwI.turbineFlow.addListener((obs, oldValue, newValue) -> {
                double diff = kwI.specificInflow.doubleValue() - newValue.doubleValue();
                deltaInFlow.setFill(diff >= 0 ? Color.GREEN : Color.RED);
            });
            kwI.specificInflow.addListener((obs, oldValue, newValue) -> {
                double diff = newValue.doubleValue() - kwI.turbineFlow.doubleValue();
                deltaInFlow.setFill(diff >= 0 ? Color.GREEN : Color.RED);
            });

            double normalHeight = kwI.getPool().getNormalHeight();
            Label height = new Label();
            height.textProperty().bind((kwI.getPool().height.asString("Stauhöhe: %.2f m ")));
            Text deltaHeight = new Text();
            deltaHeight.textProperty().bind(Bindings.subtract(kwI.getPool().height, normalHeight).asString("[%+.2f] "));
            kwI.getPool().height.addListener((obs, oldValue, newValue) -> {
                double diff = newValue.doubleValue() - normalHeight;
                deltaHeight.setFill(diff >= 0 ? Color.GREEN : Color.RED);
            });
            textFlow.getChildren().addAll(inFlow, deltaInFlow, height, deltaHeight);


            Label power = new Label();
            power.textProperty().bind(kwI.powerMW.asString("Leistung: %.2f Megawatt "));


            HBox controlBox = new HBox();
            controlBox.setAlignment(Pos.CENTER_LEFT);
            controlBox.getChildren().addAll(throughFlow, slider, power);

            HBox variableBox = new HBox();
            variableBox.setAlignment(Pos.CENTER_LEFT);
            variableBox.getChildren().addAll(textFlow);
            Label nameLabel = new Label(String.format("Kraftwerk: %s",kwI.getName()));
            VBox boxCointainer = new VBox();
            boxCointainer.setStyle("-fx-background-color: white;");
            boxCointainer.getChildren().addAll(nameLabel, controlBox, variableBox);
            anchorPane.getChildren().add(boxCointainer);
            AnchorPane.setTopAnchor(boxCointainer, 10.0);
            AnchorPane.setLeftAnchor(boxCointainer, 10.0);

            tab1.setContent(anchorPane);
            tabPane.getTabs().add(tab1);
            tabPane.getTabs().add(tab2);

            tabPane.setStyle("-fx-background-color: white;");

            paneList.add(tabPane);
            bottomPane.getChildren().add(tabPane);
            tabPane.setVisible(false);
        }
        timeMultBox.toFront();
        elementFieldConfig(simulation);

        spinnerConfig();
        paneList.get(currentTab).setVisible(true);
    }

    public void tabLeft() {
        paneList.get(currentTab).setVisible(false);
        paneList.get(loopTabs(-1)).setVisible(true);
    }

    public void tabRight() {
        paneList.get(currentTab).setVisible(false);
        paneList.get(loopTabs(1)).setVisible(true);
    }

    public int loopTabs(int change) {
        if (currentTab + change >= 0) {
            currentTab += change;
        } else {
            currentTab = paneList.size() - 1;
        }
        return currentTab %= paneList.size();

    }

    private void spinnerConfig() {
        timeMultSpinner.setEditable(true);
        timeMultSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10000, 20, 2));

        simulation.timeMult.bind(timeMultSpinner.getValueFactory().valueProperty());
        timeMultSpinner.getEditor().setPrefColumnCount(5);
    }

    private void sliderConfig(Slider slider) {
        slider.setMajorTickUnit(50);
        slider.setMinorTickCount(24);
        slider.setSnapToTicks(true);
        slider.setStyle("-fx-background-color: white;");
        enableSliderColor(slider);
    }

    private void elementFieldConfig(Simulation sim) {
        elementField.getStyleClass().add("elementField");
        moneyField.setEditable(false);
        powerField.setEditable(false);
        inflowField.setEditable(false);
        timeLabel.setEditable(false);
        timeLabel.setPrefColumnCount(16);
        moneyField.textProperty().bind(simulation.money.asString("Kontostand: %,.0f €"));
        powerField.textProperty().bind(simulation.totalPowerMW.asString("Totaleistung: %.2f MW"));
        inflowField.textProperty().bind(simulation.getInflowRepository().inflow.asString("Zufluss: %.0f m³/s"));
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
    public void heightBind(Pane paneRoot, Pane paneParent, double multiplier) {
        paneRoot.minHeightProperty().bind(paneParent.heightProperty().multiply(multiplier));
        paneRoot.maxHeightProperty().bind(paneParent.heightProperty().multiply(multiplier));
    }

    @FXML
    public void widthBind(Pane paneRoot, Pane paneParent, double multiplier) {
        paneRoot.minWidthProperty().bind(paneParent.widthProperty().multiply(multiplier));
        paneRoot.maxWidthProperty().bind(paneParent.widthProperty().multiply(multiplier));
    }

    @FXML
    public void allBind(Pane paneRoot, Pane paneParent, double multiplierWidth, double multiplierHeight) {
        widthBind(paneRoot, paneParent, multiplierWidth);
        heightBind(paneRoot, paneParent, multiplierHeight);
    }

    public LineChart getRightChart() {
        return rightChart;
    }

    public LineChart getLeftChart() {
        return leftChart;
    }

    public TextField getTimeLabel() {
        return timeLabel;
    }
    public void nextTick(double delta) {
        simulation.nextTick(delta);
    }}