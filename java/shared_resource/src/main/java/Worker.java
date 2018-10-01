package src.main.java;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Worker implements Runnable {

    private static long objCount;
    private long jobsCompleted;
    private static final int upperRandLimit = 42;
    protected final AtomicBoolean running;
    protected IDataStore dataStore;

    Worker(IDataStore dataStore) {
        this.dataStore = dataStore;
        assert (dataStore != null);
        this.jobsCompleted = 0;
        this.running = new AtomicBoolean(true);
        incWorkerObjCnt();
    }

    public static void incWorkerObjCnt() {
        objCount++;
    }

    protected int getRandomInt() {
        Random rand = new Random();
        return rand.nextInt(upperRandLimit) + 1;
    }

    protected int getRandProcessTimeMs() {
        return getRandomInt();
    }

    protected void incJobsCompleted() {
        jobsCompleted++;
    }

    protected long getJobsCompleted() {
        return jobsCompleted;
    }

    protected long getObjCount() {
        return objCount;
    }

    protected IntDataElement getRandomData() {
        Random rand = new Random();
        int value = rand.nextInt();
        if (value < 0) {
            value *= -1;
        }
        return new IntDataElement(value);
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
