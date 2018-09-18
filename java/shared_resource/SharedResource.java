import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;
import java.util.logging.LogManager;
import java.util.Iterator;
import java.io.InputStream;
import java.io.IOException;
import java.lang.UnsupportedOperationException;

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

class Factory2<T> {

    //NOTE: T type MUST have a default constructor
    private final Class<T> type;
    private IDataStore dataStore;

    public Factory2(Class<T> type, IDataStore dataStore) {
        this.type = type;
        this.dataStore = dataStore;
    }

    public static <F> Factory2<F> getInstance(Class<F> type, IDataStore dataStore) {
        return new Factory2<F>(type, dataStore);
    }

    public T getInstance() {
        try {
            // assume type is a public class
            return type.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

interface IDataStore {
    public void addData(int value);
    public int getData();
    public int getSize();
}

class WorkerThreadPool<T> extends MyLogger {

    private final Class<T> type;
    private ArrayList<T> workerThreads;
    private int threadPoolSize;
    private Thread thread;
    private IDataStore dataStore;

    WorkerThreadPool(Class<T> type, int threadPoolSize, IDataStore dataStore) {
        this.type = type;
        threadPoolSize = threadPoolSize;
        workerThreads = new ArrayList<T>();
        dataStore = dataStore;

        //FIXME: This has to be here, I can not put that to a private function... how to fix that ??
        for (int i = 0; i < threadPoolSize; i++) {
            Factory2<T> factory = Factory2.getInstance(type, dataStore);
            workerThreads.add(factory.getInstance());
        }
        initThreadPool();
    }

    public static <F> WorkerThreadPool<F> getInstance(Class<F> type, int threadPoolSize, IDataStore dataStore) {
        return new WorkerThreadPool<F>(type, threadPoolSize, dataStore);
    }

    //FIXME!!!
    private void initThreadPool() {
        for (int i = 0; i < threadPoolSize; i++) {
            Factory2<T> factory = Factory2.getInstance(type, dataStore);
            workerThreads.add(factory.getInstance());
        }
    }

    public void work() {
        for (T thread: workerThreads) {
            // Why do i have to do this downcast??
            WorkerThread worker = (WorkerThread)thread;
            worker.run();
        }
    }
}




abstract class WorkerThread extends MyLogger implements Runnable {

    private static int objCount;
    protected int jobsProcessed;
    protected final int upperRandLimit = 100;
    //    protected Logger logger;
    private int nrOfProcessedObjects = 0;
//    private String uniqueIdentifier;

    protected boolean running;
    IDataStore dataStore;


    WorkerThread(IDataStore dataStore) {
//        uniqueIdentifier = String.format("%s_%d_%s", getClass().getName(), objCount, hashCode());
        dataStore = dataStore;
        objCount++;
        running = true;
        jobsProcessed = 0;



    }

    protected int getRandProcessTimeMs() {
        Random rand = new Random();
        return rand.nextInt(upperRandLimit) + 1;
    }

    protected int getRandValue() {
        Random rand = new Random();
        return rand.nextInt();
    }

    public int getThreadCount() {
        return objCount;
    }

    public String getUniqueIdentifier() {
        return this.toString();
    }

    public abstract void run();
    public void stop() {
        running = false;
    }
}


class SynchronizedData extends MyLogger implements IDataStore {

    private ArrayList<Integer> data;

    SynchronizedData() {
        data = new ArrayList<>();
    }

    private static int getRandomNumberInRange(int max) {
        Random r = new Random();
        return r.nextInt(max);
    }

    public void addData(int dataElement) {
        synchronized (data) {
            LOGGER.fine(String.format("Add data element %d", dataElement));
            data.add(dataElement);
        }
    }

    public boolean canGetData() {
        if (data.isEmpty()) {
            return false;
        }
        return true;

    }

    public int getData() {
        synchronized (data) {

            if (canGetData()) {
                int dataElement = data.get(0);
                LOGGER.fine(String.format("Get data element %d", dataElement));
                return dataElement;
            }

            LOGGER.info("FIXME");
            return 0;

        }
    }

    public int getSize() {
        synchronized (data) {
            return data.size();
        }
    }
}


class DummyData implements IDataStore {
@java.lang.Override
public void addData(int value) {

        }

@java.lang.Override
public int getData() {
        return 0;
        }

@java.lang.Override
public int getSize() {
        return 0;
        }
}

class ConsumerThread extends WorkerThread {

    // BUG: this is here because of the factory...
    ConsumerThread() {
        super(new DummyData());
        LOGGER.info(String.format("IN DEFAULT CONSTRUCTOR @ %s", this));
    }

    ConsumerThread(IDataStore dataStore) {
        super(dataStore);
        LOGGER.fine(String.format("New %s: %d @ %s", getClass().getName(), getThreadCount(), getUniqueIdentifier()));
    }

    private void consumeData() {
        try {
            int processTimeinMs = getRandProcessTimeMs();
            Thread.yield();
            Thread.sleep(processTimeinMs);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        // BAAAAAD
        if (dataStore != null) {
            int dataElement = dataStore.getData();
            LOGGER.info(String.format("Consumed data element '%s'", dataElement));
        }
    }

    public void run() {
//        LOGGER.info(String.format("Running thread %s", getUniqueIdentifier()));

        while (running) {

            if (dataStore == null) {
                running = false;
                continue;
            }

            Thread.yield();
            consumeData();

            LOGGER.info(String.format("[%s] Successfully processed job #%d", getUniqueIdentifier(), jobsProcessed));
            jobsProcessed++;

        }

    }
}

class ProducerThread extends WorkerThread {


    // BUG: this is here because of the factory...
    ProducerThread() {
        super(new DummyData());
        LOGGER.info(String.format("IN DEFAULT CONSTRUCTOR @ %s", this));

    }

    ProducerThread(IDataStore dataStore) {
        super(dataStore);
        LOGGER.info(String.format("New %s: %d @ %s", getClass().getName(), getThreadCount(), getUniqueIdentifier()));
    }

    private void produceData() {
        try {
            int processTimeinMs = getRandProcessTimeMs();
            Thread.sleep(processTimeinMs);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        int value = getRandValue();
        dataStore.addData(value);

        LOGGER.info(String.format("[%] Successfully processed job #%d", getUniqueIdentifier(), jobsProcessed));
        jobsProcessed++;


    }

    public void run() {
        LOGGER.info(String.format("[%s] Running %s", getUniqueIdentifier(), this.getClass()));

        while (running) {


            if (dataStore == null) {
                running = false;
                continue;
            }

            Thread.yield();
            LOGGER.info(String.format("[%s] Start job #%d", getUniqueIdentifier(), jobsProcessed));


            produceData();
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
    private ArrayList<WorkerThreadPool> threadPools;
    private boolean running;
    private IDataStore dataStore;

    SharedResourceAccess() {
        threadPools = new ArrayList<WorkerThreadPool>();
        running = true;
    }

    public static void main(String[] args) {
        SharedResourceAccess sharedResourceAccess = new SharedResourceAccess();
        sharedResourceAccess.operate();
    }

    public void operate() {

        dataStore = new SynchronizedData();


        LOGGER.info("Create worker thread pools");
        threadPools.add(WorkerThreadPool.getInstance(ConsumerThread.class, NR_OF_CONSUMER_THREADS, dataStore));
        threadPools.add(WorkerThreadPool.getInstance(ProducerThread.class, NR_OF_PRODUCER_THREADS, dataStore));
        for (WorkerThreadPool threadPool : threadPools) {
            threadPool.work();
        }

        while (running) {
            LOGGER.info(String.format("Process data for %ss", sleepTimeInSec));
            try {
                Thread.sleep(sleepTimeInSec * 1000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

