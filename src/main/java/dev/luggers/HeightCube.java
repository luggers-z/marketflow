package dev.luggers;

import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;


public class HeightCube extends StackPane {
    private final Region face = new Region();

    private final double minPx;
    private final double maxPx;

    public HeightCube(Pool pool, double minPx, double maxPx) {
        this.minPx = minPx;
        this.maxPx = maxPx;

        getStyleClass().add("height-cube");
        face.getStyleClass().add("height-cube-face");

        setAlignment(Pos.TOP_CENTER);
        setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        setPrefSize(64, maxPx + 8);
        setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);


        final double vMin = pool.getMinVolume();
        final double vMax = pool.getMaxVolume();
        final double span = Math.max(1e-9, vMax - vMin);


        face.setPrefWidth(42);
        face.maxWidthProperty().bind(face.prefWidthProperty());
        face.minWidthProperty().bind(face.prefWidthProperty());

        face.prefHeightProperty().bind(Bindings.createDoubleBinding(() -> {
            double v = pool.getVolume();
            double norm = (v - vMin) / span; // 0..1
            if (norm < 0) norm = 0;
            if (norm > 1) norm = 1;
            return minPx + norm * (maxPx - minPx);
        }, pool.height));
        face.maxHeightProperty().bind(face.prefHeightProperty());
        face.minHeightProperty().bind(face.prefHeightProperty());

        getChildren().add(face);
    }
}

