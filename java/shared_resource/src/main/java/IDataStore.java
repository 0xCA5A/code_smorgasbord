package src.main.java;

public interface IDataStore {
    public void storeData(int value);

    public int consumeData();

    public int getNrOfDataElements();
}
