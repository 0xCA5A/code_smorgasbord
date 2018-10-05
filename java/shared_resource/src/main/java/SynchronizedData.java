package src.main.java;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

public class SynchronizedData implements IDataStore {

    private final List<byte[]> storage;
    private long readAccessCnt;
    private long readMissCnt;
    private long writeAccessCnt;

    private final Logger logger;

    SynchronizedData() {
        logger = MyLogHelper.getLogger(this.toString());
        this.storage = new ArrayList<>();
        this.readAccessCnt = 0;
        this.readMissCnt = 0;
        this.writeAccessCnt = 0;
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

    public void storeData(IDataElement dataElement) {
        synchronized (storage) {
            logger.fine(String.format("Add new data element %s", dataElement));

            byte[] serializedData = SerializationHelper.toByteArray(dataElement);

            storage.add(serializedData);

            logger.finer(
                    String.format(
                            "New data store size after storing data: %d elements",
                            getNrOfDataElements()));
            writeAccessCnt++;
        }
    }

    public boolean canGetData() {
        synchronized (storage) {
            return !storage.isEmpty();
        }
    }

    public IDataElement consumeData() throws NoSuchElementException {
        synchronized (storage) {
            if (!canGetData()) {
                readMissCnt++;
                throw new NoSuchElementException("No data available");
            }

            byte[] serializedData = storage.remove(0);
            IDataElement dataElement = SerializationHelper.fromByteArray(serializedData);
            logger.fine(String.format("Consume data element %s", dataElement.toString()));
            logger.finer(
                    String.format(
                            "New data store size after consuming data: %d elements",
                            getNrOfDataElements()));
            readAccessCnt++;
            return (IDataElement) dataElement;
        }
    }

    public long getNrOfDataElements() {
        synchronized (storage) {
            return storage.size();
        }
    }
}
