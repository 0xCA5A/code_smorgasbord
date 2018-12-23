package main.java;

class IntDataElement extends AbstractDataElement<Integer> {
    private static final int MAX_INT_VALUE = Integer.MAX_VALUE;

    public IntDataElement() {
        super(RandomHelper.getUnsignedInRange(MAX_INT_VALUE));
    }
}
