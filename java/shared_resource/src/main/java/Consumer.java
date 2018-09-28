package src.main.java;

import java.util.NoSuchElementException;

public class Consumer extends Worker {

    Consumer(IDataStore dataStore) {
        super(dataStore);
        LOGGER.finer(
                String.format(
                        "[%s] Create new object %s %d",
                        getUniqueIdentifier(), getClass().getName(), getObjCount()));
    }

    @Override
    public void run() {
        LOGGER.finer(String.format("Starting thread %s", getUniqueIdentifier()));
        int processTimeMs;
        while (!Thread.currentThread().isInterrupted()) {
            LOGGER.fine(
                    String.format("[%s] Start job #%d", getUniqueIdentifier(), getJobsCompleted()));
            try {
                processTimeMs = consumeData();
            } catch (NoSuchElementException noElemEx) {
                int delayTimeMs = getRandomInt();
                String msg =
                        String.format(
                                "'%s' reported by thread %s - " + "interrupt work for %dms",
                                noElemEx.getMessage(), this, delayTimeMs);
                LOGGER.warning(msg);
                try {
                    Thread.sleep(delayTimeMs);
                } catch (InterruptedException irEx) {
                    LOGGER.severe(String.format("Thread %s could not sleep", this));
                }
                continue;
            }

            LOGGER.fine(
                    String.format(
                            "[%s] Successfully completed my job #%d (process time: %dms)",
                            getUniqueIdentifier(), getJobsCompleted(), processTimeMs));
            incJobsCompleted();
            Thread.yield();
        }
        LOGGER.finer(String.format("Gracefully exit thread %s", this));
    }

    private int consumeData() {
        int processTime = delayWork();
        dataStore.consumeData();
        return processTime;
    }
}
