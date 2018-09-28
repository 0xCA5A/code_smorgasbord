package src.main.java;

public interface IDataStore {
    void storeData(DataElement element);

    DataElement consumeData();

    long getNrOfDataElements();
}
