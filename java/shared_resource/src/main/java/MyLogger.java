package src.main.java;

import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.io.IOException;
import java.io.InputStream;

public class MyLogger {
    protected static Logger LOGGER = null;
    private static String loggingProps = "logging.properties";

        static {

            InputStream stream = MyLogger.class.getClassLoader().getResourceAsStream(loggingProps);
            try {
                LogManager.getLogManager().readConfiguration(stream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            LOGGER = Logger.getLogger(MyLogger.class.getName());

    }
}
