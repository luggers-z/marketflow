package dev.luggers;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class Controller {

    Application application;
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

    @FXML private LineChart<Number, Number> rightChart;
    @FXML private LineChart<Number, Number> leftChart;


    @FXML
    public void initialize() {


        background.setPreserveRatio(true);
        background.fitWidthProperty().bind(stackPane.widthProperty());
        background.fitHeightProperty().bind(stackPane.heightProperty());

        allBind(mainPane,stackPane,1,1);
        allBind(bottomPane,stackPane,1,0.1);
        heightBind(elementField,mainPane,0.3);


        elementField.prefWidthProperty().bind(elementField.heightProperty().multiply(0.5));
        chartBox.prefHeightProperty().bind(mainPane.heightProperty().multiply(0.2));
        leftChart.prefWidthProperty().bind(chartBox.heightProperty());
        leftChart.minWidthProperty().bind(leftChart.prefWidthProperty());
        leftChart.maxWidthProperty().bind(leftChart.prefWidthProperty());
        rightChart.prefWidthProperty().bind(chartBox.heightProperty());
        rightChart.minWidthProperty().bind(rightChart.prefWidthProperty());
        rightChart.maxWidthProperty().bind(rightChart.prefWidthProperty());


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