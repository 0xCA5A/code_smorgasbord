package src.main.java;

import java.util.ArrayList;
import java.util.List;

public class ComplexDataElement extends AbstractDataElement<List> {
    private static final int MAX_NR_OF_OBJECTS = 200;

    public ComplexDataElement() {
        super(getObjectList());
    }

    private static List<Object> getObjectList() {
        ArrayList<Object> objList = new ArrayList<Object>();

        int nrOfObjects = RandomHelper.getUnsignedInRange(MAX_NR_OF_OBJECTS);
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
