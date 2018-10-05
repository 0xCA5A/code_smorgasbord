package src.main.java;

import java.io.Serializable;

public abstract class AbstractDataElement<T> implements Serializable, IDataElement {
    private T randData;

    AbstractDataElement(T randData) {
        this.randData = randData;
    }

    public String toString() {
        return getData().toString();
    }

    public T getData() {
        return randData;
    }
}
