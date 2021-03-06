package main.java;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

public class SynchronizedDataStore implements IDataStore {

    private final List<byte[]> storage;
    private long readAccessCnt;
    private long readMissCnt;
    private long writeAccessCnt;

    private final Logger logger;

    SynchronizedDataStore() {
        logger = MyLogHelper.getLogger(this.toString());
        this.storage = new ArrayList<>();
        this.readAccessCnt = 0;
        this.readMissCnt = 0;
        this.writeAccessCnt = 0;
    }

    @Override
    public void storeData(IDataElement dataElement) {
        synchronized (storage) {
            logger.fine(String.format("Add new data element %s", dataElement));

            byte[] serializedData = DataElementHelper.toByteArray(dataElement);

            storage.add(serializedData);

            logger.finer(
                    String.format(
                            "New data store size after storing data: %d elements",
                            getNrOfDataElements()));
            writeAccessCnt++;
        }
    }

    @Override
    public void consumeData() throws NoSuchElementException {
        synchronized (storage) {
            if (!canConsumeData()) {
                readMissCnt++;
                throw new NoSuchElementException("No data available");
            }

            byte[] serializedData = storage.remove(0);
            IDataElement dataElement = DataElementHelper.fromByteArray(serializedData);
            logger.fine(String.format("Consume data element %s", dataElement.toString()));
            logger.finer(
                    String.format(
                            "New data store size after consuming data: %d elements",
                            getNrOfDataElements()));
            readAccessCnt++;
        }
    }

    @Override
    public long getNrOfDataElements() {
        synchronized (storage) {
            return storage.size();
        }
    }

    long getReadAccessCnt() {
        return readAccessCnt;
    }

    float getReadAccessPercent() {
        if (getAccessCnt() <= 0) {
            return 0;
        }
        return (100.0f / getAccessCnt()) * readAccessCnt;
    }

    long getReadMissCnt() {
        return readMissCnt;
    }

    float getReadMissPercent() {
        if (getReadMissCnt() <= 0) {
            return 0;
        }
        return (100.0f / getAccessCnt()) * readMissCnt;
    }

    long getWriteAccessCnt() {
        return writeAccessCnt;
    }

    float getWriteAccessPercent() {
        if (getAccessCnt() <= 0) {
            return 0;
        }
        return (100.0f / getAccessCnt()) * writeAccessCnt;
    }

    long getAccessCnt() {
        return readAccessCnt + writeAccessCnt;
    }

    private boolean canConsumeData() {
        synchronized (storage) {
            return !storage.isEmpty();
        }
    }
}
