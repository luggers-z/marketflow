module marketflow {
	requires transitive javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.media;
    requires de.jensd.fx.glyphs.fontawesome;

    opens dev.luggers to javafx.fxml, javafx.graphics, javafx.media;
}