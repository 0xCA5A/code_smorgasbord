package src.main.java;

import java.io.Serializable;

public abstract class AbstractDataElement<T> implements Serializable, IDataElement {
    private final T data;

    AbstractDataElement(T randData) {
        this.data = randData;
    }

    public String toString() {
        return getData().toString();
    }

    public T getData() {
        return data;
    }
}
