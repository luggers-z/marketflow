package dev.luggers;

/**
 * Launcher class for the MarketFlow application.
 * This class is needed to launch JavaFX applications from a fat jar.
 * It acts as a workaround for the JavaFX module system requirements.
 */
public class Launcher {
    public static void main(String[] args) {
        Marketflow.main(args);
    }
}
