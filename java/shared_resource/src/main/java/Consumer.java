package src.main.java;

public class Consumer extends Worker {

    Consumer(IDataStore dataStore) {
        super(dataStore);
        assert (dataStore != null);
        LOGGER.finer(String.format("[%s] Create new object %s %d", getUniqueIdentifier(), getClass().getName(),
                getObjCount()));

    }

    private int consumeData() {
        int processTime = delayWork();
        dataStore.consumeData();
        return processTime;
    }

    @Override
    public void run() {
        LOGGER.finer(String.format("Starting thread %s", getUniqueIdentifier()));

        while (!Thread.currentThread().isInterrupted()) {
            LOGGER.fine(String.format("[%s] Start job #%d", getUniqueIdentifier(), jobsCompleted));
            int processTimeMs = consumeData();
            LOGGER.fine(String.format("[%s] Successfully completed my job #%d (process time: %dms)", getUniqueIdentifier(), jobsCompleted, processTimeMs));


            jobsCompleted++;
            Thread.yield();

        }
        LOGGER.finer(String.format("Gracefully exit thread %s", this));
    }
}
