package main.java;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

class SharedResourceAccess {

    private static final String PROP_FILE_NAME = "app.properties";

    private List<IWorkerPool> workerPools;
    private final SynchronizedDataStore dataStore;
    private final Logger logger;
    private static boolean running = true;
    private MyConfig config;

    private SharedResourceAccess() {
        logger = MyLogHelper.getLogger(this.toString());
        dataStore = new SynchronizedDataStore();

        initConfigFromPropFile();
        initWorkerPools();
    }

    public static void main(String[] args) {
        SharedResourceAccess sharedResourceAccess = new SharedResourceAccess();
        sharedResourceAccess.operate();
        sharedResourceAccess.waitToBeShutDown();
    }

    private void initConfigFromPropFile() {
        ClassLoader loader = SharedResourceAccess.class.getClassLoader();
        URL url = loader.getResource(PROP_FILE_NAME);
        String absFileName = Objects.requireNonNull(url).getFile();
        config = new MyConfig(absFileName);
        new InfoBanner(logger, config);
    }

    private void waitToBeShutDown() {
        Date startTimestamp = new Date();
        while (running) {

            try {
                Thread.sleep(config.reportIntervalS * 1000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            showStat(startTimestamp);
        }

        for (IWorkerPool workerPool : workerPools) {
            workerPool.terminate();
        }
    }

    private void showStat(Date startTimestamp) {
        SynchronizedDataStat dataStat = new SynchronizedDataStat(startTimestamp, dataStore);
        dataStat.show();
    }

    private void initWorkerPools() {
        this.workerPools = new ArrayList<>();
        logger.info("Create worker thread pools");
        ProducerWorkerPool pwp =
                new ProducerWorkerPool(
                        config.nrOfProducerThreads,
                        dataStore,
                        config.maxWorkerLatencyMs,
                        config.dataElementType);
        workerPools.add(pwp);
        ConsumerWorkerPool cwp =
                new ConsumerWorkerPool(
                        config.nrOfConsumerThreads,
                        dataStore,
                        config.maxWorkerLatencyMs,
                        config.dataElementType);
        workerPools.add(cwp);
    }

    private void operate() {
        for (IWorkerPool workerPool : workerPools) {
            logger.info(String.format("Starting %s", workerPool));
            workerPool.start();
        }
    }
}
