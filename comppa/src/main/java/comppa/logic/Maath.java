package comppa.logic;

import comppa.domain.Constants;

/**
 * Class that contains static methods for basic math operations.
 */
public class Maath {

    /**
     * Returns the absolute value of the passed argument.
     * @param n The argument whose absolute value should be returned.
     * @return The positive value of the argument.
     */
    public static int abs(int n) {
        if (n == Constants.INT_MIN_VAL) {
            throw new IllegalArgumentException("Cannot return positive value of Integer MIN_VALUE: " + n + ".");
        }
        return n > 0 ? n : -n;
    }

    /**
     * Returns the maximum value of the two arguments.
     * @param n1 First value to compare.
     * @param n2 Second value to compare.
     * @return The bigger one of the arguments.
     */
    public static int max(int n1, int n2) {
        return n1 > n2 ? n1 : n2;
    }
}
