package src.main.java;

import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

public class SharedResourceAccess {

    private static final int NR_OF_PRODUCER_THREADS = 7;
    private static final int NR_OF_CONSUMER_THREADS = 7;
    private static final int reportIntervalMs = 3000;
    private ArrayList<WorkerPool> workerPools;
    private SynchronizedData dataStore;

    private Logger logger;

    static volatile boolean running = true;
    static int statCnt = 0;

    SharedResourceAccess() {

        logger = MyLogManager.getLogger(this.toString());
        this.dataStore = new SynchronizedData();
        initWorkerPools();
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
        logger.info("Create worker thread pools");
        ConsumerWorkerPool cwp = new ConsumerWorkerPool(NR_OF_CONSUMER_THREADS, dataStore);
        workerPools.add(cwp);
        ProducerWorkerPool pwp = new ProducerWorkerPool(NR_OF_PRODUCER_THREADS, dataStore);
        workerPools.add(pwp);
    }

    private void operate() {
        for (WorkerPool workerPool : workerPools) {
            logger.info(String.format("Starting %s", workerPool));
            workerPool.start();
        }
    }
}
