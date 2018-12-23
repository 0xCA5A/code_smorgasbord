package main.java;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class AbstractWorker implements IWorker {

    private static long objCount;
    private long jobsCompleted;
    private final int maxWorkerLatencyMs;
    private final AtomicBoolean running;
    IDataStore dataStore;
    Class<?> dataElementClass;

    AbstractWorker(IDataStore dataStore, int maxWorkerLatencyMs, Class<?> dataElementClass) {
        this.dataStore = dataStore;
        assert dataStore != null;
        this.jobsCompleted = 0;
        this.maxWorkerLatencyMs = maxWorkerLatencyMs;
        this.dataElementClass = dataElementClass;
        this.running = new AtomicBoolean(true);
        incWorkerObjCnt();
    }

    private static void incWorkerObjCnt() {
        objCount++;
    }

    int getRandProcessTimeMs() {
        return RandomHelper.getUnsignedInRange(maxWorkerLatencyMs);
    }

    void incJobsCompleted() {
        jobsCompleted++;
    }

    long getJobsCompleted() {
        return jobsCompleted;
    }

    long getObjCount() {
        return objCount;
    }

    public String getUniqueIdentifier() {
        return this.toString();
    }

    int delayWork() {
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
