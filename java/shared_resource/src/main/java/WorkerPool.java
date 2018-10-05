package src.main.java;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

class ConsumerWorkerPool extends WorkerPool {

    ConsumerWorkerPool(
            int poolSize, IDataStore dataStore, int maxWorkerLatencyMs, Class<?> dataElementClass) {
        super(poolSize, dataStore, maxWorkerLatencyMs, dataElementClass);
    }

    @Override
    protected Worker getWorker() {
        return new Consumer(dataStore, maxWorkerLatencyMs, dataElementClass);
    }
}

class ProducerWorkerPool extends WorkerPool {

    ProducerWorkerPool(
            int poolSize, IDataStore dataStore, int maxWorkerLatencyMs, Class<?> dataElementClass) {
        super(poolSize, dataStore, maxWorkerLatencyMs, dataElementClass);
    }

    @Override
    protected Worker getWorker() {
        return new Producer(dataStore, maxWorkerLatencyMs, dataElementClass);
    }
}

public abstract class WorkerPool {

    private Logger logger;
    private List<Thread> workers;
    protected int poolSize;
    protected IDataStore dataStore;
    protected int maxWorkerLatencyMs;
    protected Class<?> dataElementClass;

    WorkerPool(
            int poolSize, IDataStore dataStore, int maxWorkerLatencyMs, Class<?> dataElementClass) {
        this.poolSize = poolSize;
        this.workers = new ArrayList<>();
        this.dataStore = dataStore;
        this.maxWorkerLatencyMs = maxWorkerLatencyMs;
        this.dataElementClass = dataElementClass;
        logger = MyLogHelper.getLogger(this.toString());

        initPool();
    }

    protected abstract Worker getWorker();

    private void initPool() {
        for (int i = 0; i < poolSize; i++) {
            workers.add(new Thread(this.getWorker()));
        }
    }

    public void start() {
        logger.info(String.format("Starting %d workers in pool %s", workers.size(), this));
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
        logger.info(
                String.format("Successfully stopped %s workers in pool %s", workers.size(), this));
    }
}
