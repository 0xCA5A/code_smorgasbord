package src.main.java;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

final class RandomHelper {
    private RandomHelper() {};

    public static int getUnsignedInRange(int max) {
        Random r = new Random();
        int value = r.nextInt(max);
        return value < 0 ? value * -1 : value;
    }
}

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
    private static final int maxIntValue = Integer.MAX_VALUE;

    public IntDataElement() {
        super(RandomHelper.getUnsignedInRange(maxIntValue));
    }
}

class StringDataElement extends DataElement<String> {

    private static final int maxStringLen = 1000;
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public StringDataElement() {
        super(getRandomString());
    }

    private static String getRandomString() {
        int count = RandomHelper.getUnsignedInRange(maxStringLen);
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }
}

class ComplexDataElement extends DataElement<List> {
    private static final int maxNrOfObjects = 200;

    public ComplexDataElement() {
        super(getObjectList());
    }

    private static List<Object> getObjectList() {
        ArrayList<Object> objList = new ArrayList<Object>();

        int nrOfObjects = RandomHelper.getUnsignedInRange(maxNrOfObjects);
        for (int i = 0; i < nrOfObjects; i++) {
            // ballast object
            String singleStr = String.format("%d ", nrOfObjects);
            Object obj = new String(new char[nrOfObjects]).replace("\0", singleStr);
            objList.add(obj);
        }
        return objList;
    }

    @Override
    public String toString() {
        String objId = this.getClass().toString();
        return String.format("%s (nr of objects attached: %d)", objId, this.getData().size());
    }
}
