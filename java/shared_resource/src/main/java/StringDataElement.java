package src.main.java;

public class StringDataElement extends AbstractDataElement<String> {

    private static final int MAX_STRING_LEN = 1000;
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public StringDataElement() {
        super(getRandomString());
    }

    private static String getRandomString() {
        int count = RandomHelper.getUnsignedInRange(MAX_STRING_LEN);
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }
}
