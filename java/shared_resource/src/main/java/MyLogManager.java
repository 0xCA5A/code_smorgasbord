package src.main.java;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class MyLogManager {
    private static String loggingProps = "logging.properties";

    static {
        InputStream stream = MyLogManager.class.getClassLoader().getResourceAsStream(loggingProps);
        try {
            LogManager.getLogManager().readConfiguration(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static Logger getLogger(String name) {
        return Logger.getLogger(name);
    }
}
