import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;
import java.util.logging.LogManager;
import java.io.InputStream;
import java.io.IOException;


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

    public Factory2(Class<T> type) {
        this.type = type;
    }

    public static <F> Factory2<F> getInstance(Class<F> type) {
        return new Factory2<F>(type);
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
    public void produce();
    public void consume();
    public int getSize();
}

class WorkerThreadPool<T> extends MyLogger {

    private final Class<T> type;
    private ArrayList<T> workerThreads;
    private int threadPoolSize;
    private Thread thread;

    WorkerThreadPool(Class<T> type, int threadPoolSize) {
        this.type = type;
        threadPoolSize = threadPoolSize;
        workerThreads = new ArrayList<>();

        //FIXME: This has to be here, I can not put that to a private function... how to fix that ??
        for (int i = 0; i < threadPoolSize; i++) {
            Factory2<T> factory = Factory2.getInstance(type);
            workerThreads.add(factory.getInstance());
        }
        initThreadPool();
    }

    public static <F> WorkerThreadPool<F> getInstance(Class<F> type, int threadPoolSize, IDataStore dataStore) {
        return new WorkerThreadPool<F>(type, threadPoolSize);
    }

    //FIXME!!!
    private void initThreadPool() {
        for (int i = 0; i < threadPoolSize; i++) {
            Factory2<T> factory = Factory2.getInstance(type);
            workerThreads.add(factory.getInstance());
        }
    }


//
//    class Work implements runnable {
//
//        public void run() {
//
//
//            LOGGER.info("hello from Work");
//        }
//    }

    public void work() {


//
//        LOGGER.fine("Let's process some data...");
//        thread = new Thread(new Work());




    }


}




class WorkerThread extends MyLogger {

    private static int objCount;
    protected final int upperRandLimit = 50;
    //    protected Logger logger;
    private int nrOfProcessedObjects = 0;
//    private String uniqueIdentifier;


    WorkerThread() {
//        uniqueIdentifier = String.format("%s_%d_%s", getClass().getName(), objCount, hashCode());
        objCount++;
    }

    protected int getRandProcessTimeMs() {
        Random rand = new Random();
        return rand.nextInt(upperRandLimit) + 1;
    }

    public int getThreadCount() {
        return objCount;
    }

    public String getUniqueIdentifier() {
        return this.toString();
    }

}


class SynchronizedData extends MyLogger implements IDataStore {

//    private List<String> syncal;

    //    private Logger logger;
    private ArrayList<Integer> data;

    SynchronizedData() {
//        logger = Logger.getLogger(getClass().getName());

        //        syncal = Collections.synchronizedList(new ArrayList<String>());
        data = new ArrayList<>();

    }

    private static int getRandomNumberInRange(int max) {
        Random r = new Random();
        return r.nextInt(max);
    }

    public void produce() {
        synchronized (data) {
            int dataElement = getRandomNumberInRange(Integer.MAX_VALUE);
            LOGGER.fine(String.format("Add data element %d", dataElement));
            data.add(dataElement);
        }
    }

    public void consume() {
        synchronized (data) {
            if (data.isEmpty()) {
                LOGGER.fine("No data to get");
                return;
            }

            int d = data.get(0);
            LOGGER.fine(String.format("Get data element %d", d));
        }
    }

    public int getSize() {
        synchronized (data) {
            return data.size();
        }
    }


//        System.out.println("Iterating synchronized ArrayList:");
//        synchronized(syncal) {
//            Iterator<String> iterator = syncal.iterator();
//            while (iterator.hasNext())
//                System.out.println(iterator.next());
//        }
}


class ConsumerThread extends WorkerThread {
//    private Logger logger;

    ConsumerThread() {
//        logger = Logger.getLogger(getClass().getName());
        LOGGER.fine(String.format("New %s: %d @ %s", getClass().getName(), getThreadCount(), getUniqueIdentifier()));
    }
}

class ProducerThread extends WorkerThread {


    ProducerThread() {
//        logger = Logger.getLogger(getClass().getName());
        LOGGER.fine(String.format("New %s: %d @ %s", getClass().getName(), getThreadCount(), getUniqueIdentifier()));
    }

    private void produceData() {
        try {
            int processTimeinMs = getRandProcessTimeMs();
            Thread.sleep(processTimeinMs);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }


//        sharedData.add()


    }

    public void run() {
        LOGGER.info(String.format("Starting %s", getUniqueIdentifier()));
    }

}


class SharedResourceAccess extends MyLogger {

    private static final int NR_OF_PRODUCER_THREADS = 7;
    private static final int NR_OF_CONSUMER_THREADS = 7;
    private final int sleepTimeInSec;
    //    static final int producerThreadPoolSize = 10;
//    static final int consumerThreadPoolSize = 10;
//    private Logger logger;
    private ArrayList<WorkerThreadPool> threadPools;
    private boolean running;
    private IDataStore dataStore;

    SharedResourceAccess() {
//        logger = Logger.getLogger(getClass().getName());
        threadPools = new ArrayList<WorkerThreadPool>();


        running = true;
        sleepTimeInSec = 5;
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
        ;
    }


}

