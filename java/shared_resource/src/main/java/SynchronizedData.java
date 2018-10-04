package src.main.java;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

public class SynchronizedData implements IDataStore {

    private ArrayList<byte[]> storage;
    private long readAccessCnt;
    private long writeAccessCnt;

    private Logger logger;

    SynchronizedData() {
        logger = MyLogManager.getLogger(this.toString());
        this.storage = new ArrayList<>();
        this.readAccessCnt = 0;
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

    public void storeData(DataElement dataElement) {
        synchronized (storage) {
            logger.fine(String.format("Add new data element %s", dataElement));

            byte[] serializedData = Serializer.toByteArray(dataElement);

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
            if (storage.isEmpty()) {
                return false;
            }
            return true;
        }
    }

    public DataElement consumeData() throws NoSuchElementException {
        synchronized (storage) {
            if (!canGetData()) {
                throw new NoSuchElementException("No data available");
            }

            byte[] serializedData = storage.remove(0);
            DataElement dataElement = Serializer.fromByteArray(serializedData);
            logger.fine(String.format("Consume data element %s", dataElement.toString()));
            logger.finer(
                    String.format(
                            "New data store size after consuming data: %d elements",
                            getNrOfDataElements()));
            readAccessCnt++;
            return (DataElement) dataElement;
        }
    }

    public long getNrOfDataElements() {
        synchronized (storage) {
            return storage.size();
        }
    }
}
