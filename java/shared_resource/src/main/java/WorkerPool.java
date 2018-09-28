package src.main.java;

import java.util.ArrayList;

class ConsumerWorkerPool extends WorkerPool {

    ConsumerWorkerPool(int poolSize, IDataStore dataStore) {
        super(poolSize, dataStore);
    }

    @Override
    protected Worker getWorker() {
        return new Consumer(dataStore);
    }
}

class ProducerWorkerPool extends WorkerPool {

    ProducerWorkerPool(int poolSize, IDataStore dataStore) {
        super(poolSize, dataStore);
    }

    @Override
    protected Worker getWorker() {
        return new Producer(dataStore);
    }
}

public abstract class WorkerPool extends MyLogger {

    private ArrayList<Thread> workers;
    protected int poolSize;
    protected IDataStore dataStore;

    WorkerPool(int poolSize, IDataStore dataStore) {
        this.poolSize = poolSize;
        this.workers = new ArrayList<>();
        this.dataStore = dataStore;
        initPool();
    }

    protected abstract Worker getWorker();

    private void initPool() {
        for (int i = 0; i < poolSize; i++) {
            workers.add(new Thread(this.getWorker()));
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
        LOGGER.info(
                String.format("Successfully stopped %s workers in pool %s", workers.size(), this));
    }
}
