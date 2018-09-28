package src.main.java;

import java.io.Serializable;

public abstract class DataElement<T> implements Serializable {
    private T data;

    DataElement(T data) {
        this.data = data;
    }

    public abstract String toString();

    public T getData() {
        return data;
    }

    public static void main(String[] args) {}
}

class IntDataElement extends DataElement<Integer> {
    IntDataElement(int data) {
        super(data);
    }

    @Override
    public String toString() {
        return String.format("%d", getData());
    }
}

class StringDataElement extends DataElement<String> {
    StringDataElement(String data) {
        super(data);
    }

    @Override
    public String toString() {
        return getData();
    }
}
