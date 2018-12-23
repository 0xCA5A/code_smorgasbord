package main.java;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractWorkerPool implements IWorkerPool {

    private final Logger logger;
    private final List<Thread> workers;
    private final int poolSize;
    final IDataStore dataStore;
    final int maxWorkerLatencyMs;
    final Class<?> dataElementClass;

    AbstractWorkerPool(
            int poolSize, IDataStore dataStore, int maxWorkerLatencyMs, Class<?> dataElementClass) {
        this.poolSize = poolSize;
        this.workers = new ArrayList<>();
        this.dataStore = dataStore;
        this.maxWorkerLatencyMs = maxWorkerLatencyMs;
        this.dataElementClass = dataElementClass;
        logger = MyLogHelper.getLogger(this.toString());

        initPool();
    }

    protected abstract IWorker createWorker();

    private void initPool() {
        for (int i = 0; i < poolSize; i++) {
            workers.add(new Thread(this.createWorker()));
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
