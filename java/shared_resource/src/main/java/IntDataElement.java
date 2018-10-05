package src.main.java;

public class IntDataElement extends AbstractDataElement<Integer> {
    private static final int maxIntValue = Integer.MAX_VALUE;

    public IntDataElement() {
        super(RandomHelper.getUnsignedInRange(maxIntValue));
    }
}
