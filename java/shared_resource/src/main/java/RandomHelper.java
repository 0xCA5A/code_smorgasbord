package main.java;

import java.util.Random;

final class RandomHelper {
    private RandomHelper() {}

    public static int getUnsignedInRange(int max) {
        Random rand = new Random();
        int value = rand.nextInt(max);
        return value < 0 ? value * -1 : value;
    }
}
