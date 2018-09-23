package src.main.java;

import java.util.ArrayList;
import java.util.Date;

public class SharedResourceAccess extends MyLogger {

    private static final int NR_OF_PRODUCER_THREADS = 7;
    private static final int NR_OF_CONSUMER_THREADS = 7;
    private final int reportIntervalMs = 3000;
    private ArrayList<WorkerPool> workerPools;
    private SynchronizedData dataStore;

    static volatile boolean running = true;
    static int statCnt = 0;

    SharedResourceAccess() {
        this.workerPools = new ArrayList<WorkerPool>();
        this.dataStore = new SynchronizedData();
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

    private void operate() {
        LOGGER.info("Create worker thread pools");
        int poolSize = NR_OF_CONSUMER_THREADS + NR_OF_PRODUCER_THREADS;

        workerPools.add(new WorkerPool(poolSize, dataStore));
        for (WorkerPool workerPool : workerPools) {
            workerPool.start();
        }
    }
}
