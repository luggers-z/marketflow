package dev.luggers;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Marketflow extends javafx.application.Application {

    private long lastUpdate = 0;
    private Scene gameScene;
    private Scene tutorialScene;
    FXMLLoader gameLoader;
    FXMLLoader tutorialLoader;
    private Controller controller;
    private Button startGameButton;
    private boolean firstStart = true;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        loadGameScene(primaryStage);
        loadTutorialScene(primaryStage);

        Image image = new Image(getClass().getResourceAsStream("/dev/luggers/icon.png"));
        primaryStage.getIcons().add(image);

        primaryStage.setTitle("MarketFlow");
        primaryStage.setScene(tutorialScene);
        primaryStage.show();
    }

    private void loadGameScene(Stage primaryStage) {
        gameLoader = new FXMLLoader(getClass().getResource("/dev/luggers/main.fxml"));
        try {
            gameScene = new Scene(gameLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        controller = gameLoader.getController();
        Button homeButton = controller.getHomeBtn();
        homeButton.setOnAction(event -> {
            primaryStage.setScene(tutorialScene);
            controller.onPauseClicked();
        });
    }

    private void loadTutorialScene(Stage primaryStage) {
        tutorialLoader = new FXMLLoader(getClass().getResource("/dev/luggers/tutorial.fxml"));
        try {
            tutorialScene = new Scene(tutorialLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        startGameButton = new Button("Simulation Starten");
        startGameButton.getStyleClass().add("start-button");
        startGameButton.setOnAction(event -> {
            if (firstStart) {
                enterGameScene();
                firstStart = false;
            }
            primaryStage.setScene(gameScene);
            controller.onStartClicked();
        });
        
        final Parent tutorialLoaderRoot = tutorialLoader.getRoot();
        final ImageView backgroundImage = (ImageView) tutorialLoaderRoot.lookup("#tutorialImage");
        final Pane tutorialPane = (Pane) tutorialLoaderRoot;
        
        backgroundImage.fitWidthProperty().bind(tutorialPane.widthProperty());
        backgroundImage.fitHeightProperty().bind(tutorialPane.heightProperty());
        tutorialPane.getChildren().add(startGameButton);
    }

    private void enterGameScene() {
        controller.startUp();
        gameScene.setOnKeyPressed(event -> {
            if (KeyCode.LEFT == event.getCode()) {
                controller.tabLeft();
            }
            if (KeyCode.RIGHT == event.getCode()) {
                controller.tabRight();
            }
        });

        final AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (lastUpdate > 0) {
                    double delta = (now - lastUpdate) / 1000000000.0;
                    if (delta < 0.25) {
                        return;
                    }
                    if (delta >= 0.25) {
                        delta = 0.25;
                    }
                    controller.nextTick(delta);
                }
                lastUpdate = now;
            }
        };
        timer.start();
    }
}
