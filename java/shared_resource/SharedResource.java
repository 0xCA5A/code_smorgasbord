import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.LogManager;
import java.util.logging.Logger;

class MyLogger {
    protected static Logger LOGGER = null;
    private static String loggingProps = "logging.properties";

    static {
        InputStream stream = MyLogger.class.getClassLoader().
                getResourceAsStream(loggingProps);
        try {
            LogManager.getLogManager().readConfiguration(stream);
            LOGGER = Logger.getLogger(MyLogger.class.getName());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

//class Factory2<T> {
//
//    //NOTE: T type MUST have a default constructor
//    private final Class<T> type;
//    private IDataStore dataStore;
//
//    public Factory2(Class<T> type, IDataStore dataStore) {
//        this.type = type;
//        this.dataStore = dataStore;
//    }
//
//    public static <F> Factory2<F> getInstance(Class<F> type) {
//        return new Factory2<F>(type, dataStore);
//    }
//
//    public T getInstance() {
//        try {
//            // assume type is a public class
//            T obj = type.newInstance();
//            obj.linkDataStore(dataStore);
//            return obj;
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//}

interface IDataStore {
    public void storeData(int value);

    public int consumeData();

    public int getNrOfDataElements();
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

    public void work() {
        for (Thread worker : workers) {
            // Why do i have to do this downcast??
//            WorkerThread worker = (WorkerThread)thread;

            worker.start();
        }
    }
}


abstract class Worker extends MyLogger implements Runnable {

    private static int objCount;
    protected int jobsCompleted;
    protected final int upperRandLimit = 42;
    //    protected Logger logger;
    private int nrOfProcessedObjects = 0;
//    private String uniqueIdentifier;

    protected boolean running;
    IDataStore dataStore;


    Worker(IDataStore dataStore) {
        this.dataStore = dataStore;
        this.running = true;
        this.jobsCompleted = 0;
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
        if (value < 0) {value *= -1;}
        return value;
    }
//
//    public int getTCount() {
//        return objCount;
//    }

    public String getUniqueIdentifier() {
        return this.toString();
    }

    public abstract void run();

    public void stop() {
        running = false;
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

    int getWriteAccessCnt() {
        return writeAccessCnt;
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
//                int dataElement = data.get(0);
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
        LOGGER.finer(String.format("New %s: %d @ %s", getClass().getName(), getObjCount(), getUniqueIdentifier()));
    }


    private int consumeData() {
        int processTime = delayWork();
        dataStore.consumeData();
        return processTime;
    }


    public void run() {
        LOGGER.finer(String.format("Starting thread %s", getUniqueIdentifier()));

        while (running) {
            assert (dataStore != null);
            LOGGER.fine(String.format("[%s] Start job #%d", getUniqueIdentifier(), jobsCompleted));
            int processTimeMs = consumeData();
            LOGGER.fine(String.format("[%s] Successfully completed job #%d (process time: %dms)", getUniqueIdentifier(), jobsCompleted, processTimeMs));
            jobsCompleted++;
            Thread.yield();
        }

    }
}

class Producer extends Worker {


    Producer(IDataStore dataStore) {
        super(dataStore);
        LOGGER.finer(String.format("New %s: %d @ %s", getClass().getName(), getObjCount(), getUniqueIdentifier()));
    }

    private int produceData() {
        int processTimeMs = delayWork();
        int dataElement = this.getRandValue();
        dataStore.storeData(dataElement);
        return processTimeMs;
    }

    public void run() {
        LOGGER.finer(String.format("Starting thread %s", getUniqueIdentifier()));

        while (running) {
            assert (dataStore != null);
            LOGGER.fine(String.format("[%s] Start job #%d", getUniqueIdentifier(), jobsCompleted));
            int processTimeMs = produceData();
            LOGGER.fine(String.format("[%s] Successfully completed job #%d (process time: %dms)", getUniqueIdentifier(), jobsCompleted, processTimeMs));
            jobsCompleted++;
            Thread.yield();
        }

    }

}


class SharedResourceAccess extends MyLogger {

    private static final int NR_OF_PRODUCER_THREADS = 7;
    private static final int NR_OF_CONSUMER_THREADS = 7;
    private final int sleepTimeInSec = 4;
    //    static final int producerThreadPoolSize = 10;
//    static final int consumerThreadPoolSize = 10;
//    private Logger logger;
    private ArrayList<WorkerPool> workerPools;
    private boolean running;
    private SynchronizedData dataStore;

    SharedResourceAccess() {
        this.workerPools = new ArrayList<WorkerPool>();
        this.running = true;

        this.dataStore = new SynchronizedData();
    }

    public static void main(String[] args) {
        SharedResourceAccess sharedResourceAccess = new SharedResourceAccess();
        sharedResourceAccess.operate();
    }

    public void operate() {
        LOGGER.info("Create worker thread pools");
        int poolSize = NR_OF_CONSUMER_THREADS + NR_OF_PRODUCER_THREADS;
        workerPools.add(new WorkerPool(poolSize, dataStore));
        for (WorkerPool pool : workerPools) {
            pool.work();
        }

        while (running) {
            LOGGER.info(String.format("Process data for %ss, data store access count: %d, current number of data elements: %d", sleepTimeInSec, dataStore.getAccessCnt(), dataStore.getNrOfDataElements()));
            try {
                Thread.sleep(sleepTimeInSec * 1000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

