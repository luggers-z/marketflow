package dev.luggers;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;

import java.util.Optional;

public class ConfigDemo {

    public static void main(String[] args) {
        Config config = ConfigProvider.getConfig();

        String kraftwerk = config.getValue("flow.kraftwerk", String.class);
        Integer durchfluss = config.getValue("flow.durchfluss", Integer.class);
        Optional<String> optional = config.getOptionalValue("flow.optional", String.class);

        System.out.printf("""
                Kraftwerk: '%s'
                Durchfluss: '%d'
                Optional: '%s'
                """, kraftwerk, durchfluss, optional.orElse("(kein Wert gefunden)"));
    }

}
