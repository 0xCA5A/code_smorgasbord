import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;

class Factory2<T> {

    // T type MUST have a default constructor
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

class WorkerThreadPool<T> {

    private final Class<T> type;
    private ArrayList<T> workerThreads;
    private int threadPoolSize;
    private Logger logger;

    WorkerThreadPool(Class<T> type, int threadPoolSize) {
        this.type = type;
        threadPoolSize = threadPoolSize;
        logger = Logger.getLogger(getClass().getName());
        workerThreads = new ArrayList<>();

        for (int i = 0; i < threadPoolSize; i++) {
            Factory2<T> factory = Factory2.getInstance(type);
            workerThreads.add(factory.getInstance());
        }
    }

    public static <F> WorkerThreadPool<F> getInstance(Class<F> type, int threadPoolSize) {
        return new WorkerThreadPool<F>(type, threadPoolSize);
    }


}

class WorkerThread extends Thread {

    private static int objCount;
    protected final int upperRandLimit = 50;
    protected Logger logger;
    private int nrOfProcessedObjects = 0;
    private String uniqueIdentifier;


    WorkerThread() {
        uniqueIdentifier = String.format("%s_%d_%s", getClass().getName(), objCount, hashCode());
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
        return uniqueIdentifier;
    }

}


class ConsumerThread extends WorkerThread {
    private Logger logger;

    ConsumerThread() {
        logger = Logger.getLogger(getClass().getName());
        logger.fine(String.format("New %s: %d @ %s", getClass().getName(), getThreadCount(), this));
    }
}

class SynchronizedData {

//    private List<String> syncal;

    private Logger logger;
    private ArrayList<Integer> data;

    SynchronizedData() {
        logger = Logger.getLogger(getClass().getName());

        //        syncal = Collections.synchronizedList(new ArrayList<String>());
        data = new ArrayList<>();

    }


    public void add(int dataElement) {
        synchronized (data) {

            logger.fine(String.format("Add data element %d", dataElement));
            data.add(dataElement);
        }
    }


    public void get() {
        synchronized (data) {
            if (data.isEmpty()) {
                logger.fine("No data to get");
                return;
            }

            int d = data.get(0);
            logger.fine(String.format("Get data element %d", d));
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


class ProducerThread extends WorkerThread {


    ProducerThread() {
        logger = Logger.getLogger(getClass().getName());
        logger.info(String.format("New %s: %d @ %s", getClass().getName(), getThreadCount(), this));
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
        logger.info(String.format("Starting %s", getUniqueIdentifier()));
    }

}


class SharedResource {

    static final int NR_OF_PRODUCER_THREADS = 7;
    static final int NR_OF_CONSUMER_THREADS = 7;
    private final int sleepTimeinSec = 5;
    private final int producerThreadPoolSize = 10;
    private final int consumerThreadPoolSize = 10;
    private Logger logger;
    private WorkerThreadPool<ProducerThread> ptp;
    private WorkerThreadPool<ConsumerThread> ctp;
    private boolean running;

    SharedResource() {
        logger = Logger.getLogger(getClass().getName());
        ptp = WorkerThreadPool.getInstance(ProducerThread.class, producerThreadPoolSize);
        ctp = WorkerThreadPool.getInstance(ConsumerThread.class, consumerThreadPoolSize);
        running = true;
    }

    public static void main(String[] args) {
        SharedResource sharedResource = new SharedResource();
        sharedResource.run();
    }

    private void initThreadPools() {
//        ptp.init();
//        ctp.init();
    }

    public void run() {


        initThreadPools();

        while (running) {
            logger.info(String.format("Process data for %ss", sleepTimeinSec));
            try {
                Thread.sleep(sleepTimeinSec * 1000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
//        ptp.s();
//        ctp.stopAll();
    }


}

