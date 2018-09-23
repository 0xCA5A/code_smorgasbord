package src.main.java;

import java.util.Random;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Worker extends MyLogger implements Runnable {

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



