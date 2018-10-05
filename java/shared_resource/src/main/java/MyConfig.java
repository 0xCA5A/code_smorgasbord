package src.main.java;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class MyConfig {

    private Logger logger;

    private static final int NR_OF_PRODUCER_THREADS = 7;
    private static final int NR_OF_CONSUMER_THREADS = 7;
    private static final int MAX_WORKER_LATENCY_MS = 42;
    private static final int REPORT_INTERVAL_S = 3;
    private static final String DEFAULT_DATA_ELEMENT_TYPE = "src.main.java.IntDataElement";

    public final int nrOfConsumerThreads;
    public final int nrOfProducerThreads;
    public final int maxWorkerLatencyMs;
    public final int reportIntervalS;
    public final Class<?> dataElementType;

    public MyConfig(String propFileName) {
        logger = MyLogManager.getLogger(this.toString());
        Properties props = readPropFile(propFileName);

        String stringValue;
        stringValue =
                props.getProperty(
                        "app.nr_of_consumer_threads", String.valueOf(NR_OF_CONSUMER_THREADS));
        nrOfConsumerThreads = Integer.parseInt(stringValue);

        stringValue =
                props.getProperty(
                        "app.nr_of_producer_threads", String.valueOf(NR_OF_PRODUCER_THREADS));
        nrOfProducerThreads = Integer.parseInt(stringValue);

        stringValue =
                props.getProperty(
                        "app.max_worker_latency_ms", String.valueOf(MAX_WORKER_LATENCY_MS));
        maxWorkerLatencyMs = Integer.parseInt(stringValue);

        stringValue = props.getProperty("app.report_interval_s", String.valueOf(REPORT_INTERVAL_S));
        reportIntervalS = Integer.parseInt(stringValue);

        stringValue = props.getProperty("app.data_element_type", DEFAULT_DATA_ELEMENT_TYPE);
        dataElementType = getClassByName(stringValue);
    }

    private static Class<?> getClassByName(String className) {
        Class<?> prototype = null;
        try {
            prototype = Class.forName(className);
        } catch (ClassNotFoundException ex) {
            // Cheap loop prevention
            assert !className.equals(DEFAULT_DATA_ELEMENT_TYPE);
            // FIXME: how to log from static context?
            System.out.println(
                    String.format(
                            "Unable to get class for type '%s', using fallback '%s'",
                            className, DEFAULT_DATA_ELEMENT_TYPE));
            prototype = getClassByName(DEFAULT_DATA_ELEMENT_TYPE);
        }
        assert prototype != null;
        return prototype;
    }

    private Properties readPropFile(String propFileName) {
        Properties props = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream(propFileName);
            props.load(input);

            File file = new File(propFileName);
            logger.info(
                    String.format("Properties from file '%s' successfully loaded", file.getName()));
            logger.fine(String.format("Properties file path: '%s'", propFileName));
        } catch (IOException ex) {
            logger.warning(
                    ex.getMessage()
                            + String.format(
                                    ", unable to load properties from file '%s' - using defaults",
                                    propFileName));
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return props;
    }
}
