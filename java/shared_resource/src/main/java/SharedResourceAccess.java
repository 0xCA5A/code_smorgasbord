package src.main.java;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

public class SharedResourceAccess {

    private static final String propFileName = "app.properties";

    private ArrayList<WorkerPool> workerPools;
    private SynchronizedData dataStore;

    private Logger logger;

    static volatile boolean running = true;
    static long statCnt = 0;

    MyConfig config;

    SharedResourceAccess() {
        logger = MyLogManager.getLogger(this.toString());
        dataStore = new SynchronizedData();

        initConfig();
        initWorkerPools();
    }

    private void initConfig() {
        ClassLoader loader = SharedResourceAccess.class.getClassLoader();
        URL url = loader.getResource(propFileName);
        String absFileName = url.getFile();
        config = new MyConfig(absFileName);
        new InfoBanner(logger, config);
    }

    public static void main(String[] args) {

        Runtime.getRuntime()
                .addShutdownHook(
                        new Thread() {
                            public void run() {
                                running = false;
                            }
                        });

        SharedResourceAccess sharedResourceAccess = new SharedResourceAccess();
        sharedResourceAccess.operate();
        sharedResourceAccess.waitToBeShutDown();
    }

    public void waitToBeShutDown() {
        Date startTimestamp = new Date();
        while (running) {

            try {
                Thread.sleep(config.reportIntervalS * 1000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            showStat(startTimestamp);
        }

        for (WorkerPool workerPool : workerPools) {
            workerPool.terminate();
        }
    }

    private void showStat(Date startTimestamp) {
        SynchronizedDataStat dataStat = new SynchronizedDataStat(startTimestamp, dataStore);
        dataStat.show();
    }

    private void initWorkerPools() {
        this.workerPools = new ArrayList<WorkerPool>();
        logger.info("Create worker thread pools");
        ProducerWorkerPool pwp = new ProducerWorkerPool(config.nrOfProducerThreads, dataStore);
        workerPools.add(pwp);
        ConsumerWorkerPool cwp = new ConsumerWorkerPool(config.nrOfConsumerThreads, dataStore);
        workerPools.add(cwp);
    }

    private void operate() {
        for (WorkerPool workerPool : workerPools) {
            logger.info(String.format("Starting %s", workerPool));
            workerPool.start();
        }
    }
}
