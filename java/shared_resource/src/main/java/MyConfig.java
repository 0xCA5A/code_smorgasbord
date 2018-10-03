package src.main.java;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class MyConfig {

    private Logger logger;

    public static final int NR_OF_PRODUCER_THREADS = 7;
    public static final int NR_OF_CONSUMER_THREADS = 7;
    public static final int MAX_WORKER_LATENCY_MS = 42;
    public static final int REPORT_INTERVAL_S = 3;

    public final int nrOfConsumerThreads;
    public final int nrOfProducerThreads;
    public final int maxWorkerLatencyMs;
    public final int reportIntervalS;

    public void print() {
        String separator = new String(new char[80]).replace("\0", "#");
        logger.info(separator);
        logger.info(String.format("# %s", this));
        logger.info(separator);
        logger.info(String.format("# * nrOfConsumerThreads: %d", nrOfConsumerThreads));
        logger.info(String.format("# * nrOfProducerThreads: %d", nrOfProducerThreads));
        logger.info(String.format("# * maxWorkerLatencyMs: %d", maxWorkerLatencyMs));
        logger.info(String.format("# * reportIntervalS: %d", reportIntervalS));
        logger.info(separator);
    }

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
