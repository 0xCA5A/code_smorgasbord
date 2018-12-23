package main.java;

interface IDataStore {
    void storeData(IDataElement element);

    void consumeData();

    long getNrOfDataElements();
}
