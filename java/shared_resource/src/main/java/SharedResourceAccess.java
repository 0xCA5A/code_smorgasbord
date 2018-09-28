package src.main.java;

import java.util.ArrayList;
import java.util.Date;

public class SharedResourceAccess extends MyLogger {

    private static final int NR_OF_PRODUCER_THREADS = 7;
    private static final int NR_OF_CONSUMER_THREADS = 7;
    private static final int reportIntervalMs = 3000;
    private ArrayList<WorkerPool> workerPools;
    private SynchronizedData dataStore;

    static volatile boolean running = true;
    static int statCnt = 0;

    SharedResourceAccess() {
        this.dataStore = new SynchronizedData();
        initWorkerPools();
    }

    public static void main(String[] args) {

        Runtime.getRuntime()
                .addShutdownHook(
                        new Thread() {
                            public void run() {
                                LOGGER.info("Ctrl C catched");
                                running = false;
                            }
                        });

        SharedResourceAccess sharedResourceAccess = new SharedResourceAccess();
        sharedResourceAccess.operate();
        sharedResourceAccess.waitToBeShutDown();
        LOGGER.info("Gracefully exit main");
    }

    public void waitToBeShutDown() {
        Date startTimestamp = new Date();
        while (running) {

            try {
                Thread.sleep(reportIntervalMs);
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
        LOGGER.info("Create worker thread pools");
        int poolSize = NR_OF_CONSUMER_THREADS + NR_OF_PRODUCER_THREADS;

        ConsumerWorkerPool cwp = new ConsumerWorkerPool(NR_OF_CONSUMER_THREADS, dataStore);
        workerPools.add(cwp);
        ProducerWorkerPool pwp = new ProducerWorkerPool(NR_OF_PRODUCER_THREADS, dataStore);
        workerPools.add(pwp);
    }

    private void operate() {
        for (WorkerPool workerPool : workerPools) {
            LOGGER.info(String.format("Starting %s", workerPool));
            workerPool.start();
        }
    }
}
