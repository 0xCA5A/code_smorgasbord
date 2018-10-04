package src.main.java;

import java.util.logging.Logger;

public class Producer extends Worker {
    private Logger logger;

    Producer(IDataStore dataStore, int maxWorkerLatencyMs) {
        super(dataStore, maxWorkerLatencyMs);
        logger = MyLogManager.getLogger(this.toString());
        logger.finer(
                String.format(
                        "[%s] Create new object %s %d",
                        getUniqueIdentifier(), getClass().getName(), getObjCount()));
    }

    @Override
    public void run() {
        logger.finer(String.format("Starting thread %s", getUniqueIdentifier()));
        while (!Thread.currentThread().isInterrupted()) {
            logger.fine(
                    String.format("[%s] Start job #%d", getUniqueIdentifier(), getJobsCompleted()));
            int processTimeMs = produceData();
            logger.fine(
                    String.format(
                            "[%s] Successfully completed my job #%d (process time: %dms)",
                            getUniqueIdentifier(), getJobsCompleted(), processTimeMs));
            incJobsCompleted();
            Thread.yield();
        }
        logger.finer(String.format("Gracefully exit thread %s", this));
    }

    private int produceData() {
        int processTimeMs = delayWork();
        DataElement dataElement = new ComplexDataElement();
        dataStore.storeData(dataElement);
        return processTimeMs;
    }
}
