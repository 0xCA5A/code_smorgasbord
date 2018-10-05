package src.main.java;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Worker implements Runnable {

    private static long objCount;
    private long jobsCompleted;
    private final int maxWorkerLatencyMs;
    protected final AtomicBoolean running;
    protected IDataStore dataStore;
    protected Class<?> dataElementClass;

    Worker(IDataStore dataStore, int maxWorkerLatencyMs, Class<?> dataElementClass) {
        this.dataStore = dataStore;
        assert dataStore != null;
        this.jobsCompleted = 0;
        this.maxWorkerLatencyMs = maxWorkerLatencyMs;
        this.dataElementClass = dataElementClass;
        this.running = new AtomicBoolean(true);
        incWorkerObjCnt();
    }

    public static void incWorkerObjCnt() {
        objCount++;
    }

    protected int getRandomInt() {
        Random rand = new Random();
        return rand.nextInt(maxWorkerLatencyMs) + 1;
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
