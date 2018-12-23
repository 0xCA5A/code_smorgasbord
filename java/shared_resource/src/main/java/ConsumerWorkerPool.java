package main.java;

class ConsumerWorkerPool extends AbstractWorkerPool {

    ConsumerWorkerPool(
            int poolSize, IDataStore dataStore, int maxWorkerLatencyMs, Class<?> dataElementClass) {
        super(poolSize, dataStore, maxWorkerLatencyMs, dataElementClass);
    }

    @Override
    protected IWorker createWorker() {
        return new Consumer(dataStore, maxWorkerLatencyMs, dataElementClass);
    }
}
