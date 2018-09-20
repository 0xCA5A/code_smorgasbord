import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.LogManager;
import java.util.logging.Logger;

interface IDataStore {
    public void storeData(int value);

    public int consumeData();

    public int getNrOfDataElements();
}

class MyLogger {
    protected static Logger LOGGER = null;
    private static String loggingProps = "logging.properties";

    static {
        InputStream stream = MyLogger.class.getClassLoader().
                getResourceAsStream(loggingProps);
        try {
            LogManager.getLogManager().readConfiguration(stream);
            LOGGER = Logger.getLogger(MyLogger.class.getName());

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

class WorkerPool extends MyLogger {

    private ArrayList<Thread> workers;
    private int poolSize;
    private IDataStore dataStore;

    WorkerPool(int poolSize, IDataStore dataStore) {
        this.poolSize = poolSize;
        this.workers = new ArrayList<Thread>();
        this.dataStore = dataStore;
        initPool();
    }

    private void initPool() {
        for (int i = 0; i < poolSize / 2; i++) {
            workers.add(new Thread(new Producer(dataStore)));
        }
        for (int i = 0; i < poolSize / 2; i++) {
            workers.add(new Thread(new Consumer(dataStore)));
        }
    }

    public void start() {
        LOGGER.info(String.format("Starting %d workers in pool %s", workers.size(), this));
        for (Thread worker : workers) {
            worker.start();
        }
    }

    public void terminate() {

        for (Thread worker : workers) {
            worker.interrupt();
            try {
                worker.join();
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
        LOGGER.info(String.format("Successfully stopped %s workers in pool %s", workers.size(), this));
    }


}

abstract class Worker extends MyLogger implements Runnable {

    private static int objCount;
    protected int jobsCompleted;
    protected final int upperRandLimit = 42;
    private int nrOfProcessedObjects = 0;
    protected final AtomicBoolean running;
    protected IDataStore dataStore;

    Worker(IDataStore dataStore) {
        this.dataStore = dataStore;
        this.jobsCompleted = 0;
        this.running = new AtomicBoolean(true);
        this.objCount++;
    }

    protected int getRandProcessTimeMs() {
        Random rand = new Random();
        return rand.nextInt(upperRandLimit) + 1;
    }

    protected int getObjCount() {
        return objCount;
    }

    protected int getRandValue() {
        Random rand = new Random();
        int value = rand.nextInt();
        if (value < 0) {
            value *= -1;
        }
        return value;
    }

    public String getUniqueIdentifier() {
        return this.toString();
    }

    protected int delayWork() {
        int processTimeMs = -1;
        try {
            processTimeMs = getRandProcessTimeMs();
            Thread.sleep(processTimeMs);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        return processTimeMs;
    }

    public abstract void run();
}

class SynchronizedData extends MyLogger implements IDataStore {

    private ArrayList<Integer> data;
    int readAccessCnt;
    int writeAccessCnt;

    SynchronizedData() {
        this.data = new ArrayList<>();
        this.readAccessCnt = 0;
        this.writeAccessCnt = 0;
    }

    int getReadAccessCnt() {
        return readAccessCnt;
    }

    float getReadAccessPercent() {
        return (100.0f / getAccessCnt()) * readAccessCnt;
    }

    int getWriteAccessCnt() {
        return writeAccessCnt;
    }

    float getWriteAccessPercent() {
        return (100.0f / getAccessCnt()) * writeAccessCnt;
    }

    int getAccessCnt() {
        return readAccessCnt + writeAccessCnt;
    }

    private static int getRandomNumberInRange(int max) {
        Random r = new Random();
        return r.nextInt(max);
    }

    public void storeData(int dataElement) {
        synchronized (data) {
            LOGGER.fine(String.format("Add new data element %d", dataElement));
            data.add(dataElement);
            LOGGER.finer(String.format("New data store size after storing data: %d elements", getNrOfDataElements()));
            writeAccessCnt++;
        }
    }

    public boolean canGetData() {
        synchronized (data) {
            if (data.isEmpty()) {
                return false;
            }
            return true;
        }

    }

    public int consumeData() {
        synchronized (data) {
            if (canGetData()) {
                int dataElement = data.remove(0);
                LOGGER.fine(String.format("Consume data element %d", dataElement));
                LOGGER.finer(String.format("New data store size after consuming data: %d elements", getNrOfDataElements()));
                readAccessCnt++;

            }
            return -1;
        }
    }

    public int getNrOfDataElements() {
        synchronized (data) {
            return data.size();
        }
    }
}

class Consumer extends Worker {

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

class Producer extends Worker {

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

class SynchronizedDataStat extends MyLogger {

    private static int statObjCnt = 0;
    private long timeDiffMs;
    private SynchronizedData dataStore;

    SynchronizedDataStat(Date startTimestamp, SynchronizedData dataStore) {
        this.dataStore = dataStore;
        statObjCnt++;
        timeDiffMs = getDateDiff(startTimestamp, new Date(), TimeUnit.MILLISECONDS);
    }

    public void show() {
        LOGGER.info("====================================================================");
        LOGGER.info(String.format("=== STATUS REPORT #%d (%s) ", statObjCnt, this));
        LOGGER.info(String.format(" * data process time:\t\t\t%sms", timeDiffMs));
        LOGGER.info(String.format(" * total data store access count:\t%d", dataStore.getAccessCnt()));
        LOGGER.info(String.format(" * data store read access count:\t%d (%.2f%%)", dataStore.getReadAccessCnt(), dataStore.getReadAccessPercent()));
        LOGGER.info(String.format(" * data store write access count:\t%d (%.2f%%)", dataStore.getWriteAccessCnt(), dataStore.getWriteAccessPercent()));
        LOGGER.info(String.format(" * nr of data elements in data store:\t%d", dataStore.getNrOfDataElements()));
        LOGGER.info("====================================================================");
    }

    public long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }
}


class SharedResourceAccess extends MyLogger {

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

        Runtime.getRuntime().addShutdownHook(new Thread() {
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

