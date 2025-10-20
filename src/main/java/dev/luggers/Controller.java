package dev.luggers;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class Controller {


    @FXML private StackPane stackPane;
    @FXML private ImageView background;
    @FXML private AnchorPane mainPane;
    @FXML private AnchorPane topPane;
    @FXML private StackPane bottomPane;
    @FXML private VBox elementField;

    @FXML private TextField moneyField;
    @FXML private TextField powerField;
    @FXML private TextField inflowField;

    @FXML private LineChart<String, Number> rightChart;
    @FXML private LineChart<String, Number> leftChart;


    @FXML
            public void initialize(){
        background.setPreserveRatio(true);
        background.fitWidthProperty().bind(mainPane.widthProperty());
        background.fitHeightProperty().bind(mainPane.heightProperty());
        background.setStyle("-fx-opacity: 1; -fx-border-color: blue;");
    }
}
