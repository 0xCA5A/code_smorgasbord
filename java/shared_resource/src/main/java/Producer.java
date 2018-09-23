
package src.main.java;

public class Producer extends Worker {

    Producer(IDataStore dataStore) {
        super(dataStore);
        assert (dataStore != null);
        LOGGER.finer(String.format("[%s] Create new object %s %d", getUniqueIdentifier(), getClass().getName(), getObjCount()));
    }

    private int produceData() {
        int processTimeMs = delayWork();
        int dataElement = this.getRandValue();
        dataStore.storeData(dataElement);
        return processTimeMs;
    }

    @Override
    public void run() {
        LOGGER.finer(String.format("Starting thread %s", getUniqueIdentifier()));

        while (!Thread.currentThread().isInterrupted()) {
            LOGGER.fine(String.format("[%s] Start job #%d", getUniqueIdentifier(), jobsCompleted));
            int processTimeMs = produceData();
            LOGGER.fine(String.format("[%s] Successfully completed my job #%d (process time: %dms)", getUniqueIdentifier(), jobsCompleted, processTimeMs));
            jobsCompleted++;
            Thread.yield();
        }

    }

}
