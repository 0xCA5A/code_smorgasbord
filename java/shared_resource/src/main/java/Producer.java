package src.main.java;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;

public class Producer extends AbstractWorker {
    private Logger logger;

    Producer(IDataStore dataStore, int maxWorkerLatencyMs, Class<?> dataElementClass) {
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
        IDataElement dataElement = createDataElement();
        dataStore.storeData(dataElement);
        return processTimeMs;
    }

    private void logException(String exceptionString) {
        logger.severe(String.format("Caught '%s' while creating a data element", exceptionString));
    }

    private IDataElement createDataElement() {

        Constructor<?> constructor = null;
        try {
            constructor = dataElementClass.getConstructor();
        } catch (NoSuchMethodException constEx) {
            logException(constEx.toString());
        } finally {
            assert constructor != null;
        }

        IDataElement dataElement = null;
        try {
            dataElement = (IDataElement) constructor.newInstance();
        } catch (InstantiationException
                | IllegalAccessException
                | InvocationTargetException multiEx) {
            logException(multiEx.toString());
        } finally {
            assert dataElement != null;
        }
        return dataElement;
    }
}
