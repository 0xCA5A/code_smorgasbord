package src.main.java;

public class Producer extends Worker {

    Producer(IDataStore dataStore) {
        super(dataStore);
        LOGGER.finer(
                String.format(
                        "[%s] Create new object %s %d",
                        getUniqueIdentifier(), getClass().getName(), getObjCount()));
    }

    @Override
    public void run() {
        LOGGER.finer(String.format("Starting thread %s", getUniqueIdentifier()));
        while (!Thread.currentThread().isInterrupted()) {
            LOGGER.fine(
                    String.format("[%s] Start job #%d", getUniqueIdentifier(), getJobsCompleted()));
            int processTimeMs = produceData();
            LOGGER.fine(
                    String.format(
                            "[%s] Successfully completed my job #%d (process time: %dms)",
                            getUniqueIdentifier(), getJobsCompleted(), processTimeMs));
            incJobsCompleted();
            Thread.yield();
        }
        LOGGER.finer(String.format("Gracefully exit thread %s", this));
    }

    private int produceData() {
        int processTimeMs = delayWork();
        int dataElement = this.getRandValue();
        dataStore.storeData(dataElement);
        return processTimeMs;
    }
}
