package src.main.java;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.logging.Logger;

public class SynchronizedData implements IDataStore {

    private ArrayList<byte[]> storage;
    private int readAccessCnt;
    private int writeAccessCnt;

    private Logger logger;

    SynchronizedData() {
        logger = MyLogManager.getLogger(this.toString());
        this.storage = new ArrayList<>();
        this.readAccessCnt = 0;
        this.writeAccessCnt = 0;
    }

    int getReadAccessCnt() {
        return readAccessCnt;
    }

    float getReadAccessPercent() {
        return (100.0f / getAccessCnt()) * readAccessCnt;
    }

    int getWriteAccessCnt() {
        return writeAccessCnt;
    }

    float getWriteAccessPercent() {
        return (100.0f / getAccessCnt()) * writeAccessCnt;
    }

    int getAccessCnt() {
        return readAccessCnt + writeAccessCnt;
    }

    private static int getRandomNumberInRange(int max) {
        Random r = new Random();
        return r.nextInt(max);
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
