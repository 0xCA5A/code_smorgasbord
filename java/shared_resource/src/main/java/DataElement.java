package src.main.java;

import java.io.Serializable;
import java.util.Random;

public abstract class DataElement<T> implements Serializable {
    private T randData;

    DataElement(T randData) {
        this.randData = randData;
    }

    public String toString() {
        return getData().toString();
    }

    public T getData() {
        return randData;
    }
}

class IntDataElement extends DataElement<Integer> {
    IntDataElement() {
        super(getRandomUnsignedInt());
    }

    private static int getRandomUnsignedInt() {
        Random rand = new Random();
        int value = rand.nextInt();
        return (value < 0) ? value : value * -1;
    }
}

class StringDataElement extends DataElement<String> {

    private static final int maxStringLen = 1000;
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    StringDataElement() {
        super(getRandomString());
    }

    private static int getRandomUnsignedInRange(int max) {
        Random r = new Random();
        int value = r.nextInt(max);
        return (value < 0) ? value * -1 : value;
    }

    private static String getRandomString() {
        int count = getRandomUnsignedInRange(maxStringLen);
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }
}

class ComplexDataElement extends DataElement<Object[]> {
    ComplexDataElement(Object[] randData) {

        super(randData);
    }

    @Override
    public String toString() {
        return this.toString();
    }
}
