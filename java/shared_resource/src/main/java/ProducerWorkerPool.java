package src.main.java;

class ProducerWorkerPool extends AbstractWorkerPool {

    ProducerWorkerPool(
            int poolSize, IDataStore dataStore, int maxWorkerLatencyMs, Class<?> dataElementClass) {
        super(poolSize, dataStore, maxWorkerLatencyMs, dataElementClass);
    }

    @Override
    protected IWorker createWorker() {
        return new Producer(dataStore, maxWorkerLatencyMs, dataElementClass);
    }
}
