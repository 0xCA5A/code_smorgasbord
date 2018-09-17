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

// TODO: HOW TO AVOID THIS???
class MyLoggingThread extends MyLogger {

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

class WorkerThreadPool<T> extends MyLoggingThread {

    private final Class<T> type;
    private ArrayList<T> workerThreads;
    private int threadPoolSize;

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

    public static <F> WorkerThreadPool<F> getInstance(Class<F> type, int threadPoolSize) {
        return new WorkerThreadPool<F>(type, threadPoolSize);
    }

    //FIXME!!!
    private void initThreadPool() {

//        LOGGER.fine(String.format("thread pool size: %d", threadPoolSize));

        for (int i = 0; i < threadPoolSize; i++) {
            Factory2<T> factory = Factory2.getInstance(type);
            workerThreads.add(factory.getInstance());
        }
    }


    public void run() {


        LOGGER.fine("Let's process some data...");


    }


}


class WorkerThread extends MyLoggingThread {

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


class SynchronizedData extends MyLogger {

//    private List<String> syncal;

    //    private Logger logger;
    private ArrayList<Integer> data;

    SynchronizedData() {
//        logger = Logger.getLogger(getClass().getName());

        //        syncal = Collections.synchronizedList(new ArrayList<String>());
        data = new ArrayList<>();

    }


    public void add(int dataElement) {
        synchronized (data) {

            LOGGER.fine(String.format("Add data element %d", dataElement));
            data.add(dataElement);
        }
    }


    public void get() {
        synchronized (data) {
            if (data.isEmpty()) {
                LOGGER.fine("No data to get");
                return;
            }

            int d = data.get(0);
            LOGGER.fine(String.format("Get data element %d", d));
        }
    }

    public int size(int dataElement) {
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

        LOGGER.info("Create worker thread pools");
        threadPools.add(WorkerThreadPool.getInstance(ConsumerThread.class, NR_OF_CONSUMER_THREADS));
        threadPools.add(WorkerThreadPool.getInstance(ProducerThread.class, NR_OF_PRODUCER_THREADS));
        for (WorkerThreadPool threadPool : threadPools) {
            threadPool.run();
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

