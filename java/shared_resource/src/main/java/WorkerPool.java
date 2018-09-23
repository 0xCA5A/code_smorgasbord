package src.main.java;

import java.util.ArrayList;

public class WorkerPool extends MyLogger {

    private ArrayList<Thread> workers;
    private int poolSize;
    private IDataStore dataStore;

    WorkerPool(int poolSize, IDataStore dataStore) {
        this.poolSize = poolSize;
        this.workers = new ArrayList<>();
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