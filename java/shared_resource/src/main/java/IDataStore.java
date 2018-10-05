package src.main.java;

public interface IDataStore {
    void storeData(IDataElement element);

    IDataElement consumeData();

    long getNrOfDataElements();
}
