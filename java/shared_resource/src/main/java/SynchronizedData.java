package src.main.java;

import java.util.ArrayList;
import java.util.Random;

public class SynchronizedData extends MyLogger implements IDataStore {

    private ArrayList<Integer> data;
    int readAccessCnt;
    int writeAccessCnt;

    SynchronizedData() {
        this.data = new ArrayList<>();
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

    public void storeData(int dataElement) {
        synchronized (data) {
            LOGGER.fine(String.format("Add new data element %d", dataElement));
            data.add(dataElement);
            LOGGER.finer(
                    String.format(
                            "New data store size after storing data: %d elements",
                            getNrOfDataElements()));
            writeAccessCnt++;
        }
    }

    public boolean canGetData() {
        synchronized (data) {
            if (data.isEmpty()) {
                return false;
            }
            return true;
        }
    }

    public int consumeData() {
        synchronized (data) {
            if (canGetData()) {
                int dataElement = data.remove(0);
                LOGGER.fine(String.format("Consume data element %d", dataElement));
                LOGGER.finer(
                        String.format(
                                "New data store size after consuming data: %d elements",
                                getNrOfDataElements()));
                readAccessCnt++;
            }
            return -1;
        }
    }

    public int getNrOfDataElements() {
        synchronized (data) {
            return data.size();
        }
    }
}
