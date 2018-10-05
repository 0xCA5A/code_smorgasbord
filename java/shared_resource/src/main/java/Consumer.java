package src.main.java;

import java.util.NoSuchElementException;
import java.util.logging.Logger;

public class Consumer extends Worker {

    private Logger logger;

    Consumer(IDataStore dataStore, int maxWorkerLatencyMs, Class<?> dataElementClass) {
        super(dataStore, maxWorkerLatencyMs, dataElementClass);
        logger = MyLogHelper.getLogger(this.toString());

        logger.finer(
                String.format(
                        "[%s] Create new object %s %d",
                        getUniqueIdentifier(), getClass().getName(), getObjCount()));
    }

    @Override
    public void run() {
        logger.finer(String.format("Starting thread %s", getUniqueIdentifier()));
        int processTimeMs;
        while (!Thread.currentThread().isInterrupted()) {
            logger.fine(
                    String.format("[%s] Start job #%d", getUniqueIdentifier(), getJobsCompleted()));
            try {
                processTimeMs = consumeData();
            } catch (NoSuchElementException noElemEx) {
                int delayTimeMs = getRandomInt();
                String msg =
                        String.format(
                                "'%s' reported by thread %s - " + "interrupt work for %dms",
                                noElemEx.getMessage(), this, delayTimeMs);
                logger.warning(msg);
                try {
                    Thread.sleep(delayTimeMs);
                } catch (InterruptedException irEx) {
                    logger.severe(String.format("Thread %s could not sleep", this));
                }
                continue;
            }

            logger.fine(
                    String.format(
                            "[%s] Successfully completed my job #%d (process time: %dms)",
                            getUniqueIdentifier(), getJobsCompleted(), processTimeMs));
            incJobsCompleted();
            Thread.yield();
        }
        logger.finer(String.format("Gracefully exit thread %s", this));
    }

    private int consumeData() {
        int processTime = delayWork();
        dataStore.consumeData();
        return processTime;
    }
}
