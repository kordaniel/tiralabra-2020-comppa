package comppa.domain;

/**
 * A class that provides methods for manipulating single bits.
 * All methods for manipulating or examining individual bits take
 * constant time O(1). That is every method do at most 63 bit shifts.
 * For space complexity the overhead is at most 63 bits.
 * @author danielko
 */
public class Bitarray {

    public static final int DEF_SIZE   = 64; // Default size of the bitarray, in bits

    private static final int DATA_WIDTH = 64; // Width of the used datatype, in bits

    private int    length; // Length of the array
    private int    size;   // Number of bits this object can store
    private int    maxBit; // The most significant bit that has been altered
    private long[] bits;   // Array that holds the bits


    public Bitarray() {
        this(DEF_SIZE);
    }

    public Bitarray(int initialSize) {
        this.length = computeNeededLength(initialSize);
        this.size   = this.length * DATA_WIDTH;
        this.maxBit = -1;
        this.bits   = new long[this.length];
    }

    /**
     * This methods sets the bit at the specified bitIndex to 1.
     * @param bitIndex The index of the bit to be set to 1 (or simply to "true").
     * @throws IndexOutOfBoundsException If bitIndex is negative or out of bounds for the instance of this object.
     */
    public void setBit(int bitIndex) {
        // @TODO: If we get an bitIndex argument, that is larger than what will fit in the current bits-array
        //        either resize the array (if needed) or create tests that the correct exception is thrown.
        if (!isValidIndex(bitIndex)) {
            throw new IndexOutOfBoundsException(
                "Index " + bitIndex + " out of bounds for this Bitarray with the size of " + this.size
            );
        }
        if (bitIndex > this.maxBit) {
            this.maxBit = bitIndex;
        }

        this.bits[calcIndexForArray(bitIndex)] |= 1L << calcIndexForLong(bitIndex);
    }

    /**
     * This method unsets the bit at the specified bitIndex. That is sets the bit to the value 0.
     * @param bitIndex The index of the bit to be unset to the value 0 (or simply to "false").
     * @throws IndexOutOfBoundsException If bitIndex is negative or out of bounds for the instance of this object.
     */
    public void unsetBit(int bitIndex) {
        // @TODO: If we get an bitIndex argument, that is larger than what will fit in the current bits-array
        //        either resize the array (if needed) or create tests that the correct exception is thrown.
        if (!isValidIndex(bitIndex)) {
            throw new IndexOutOfBoundsException(
                "Index " + bitIndex + " out of bounds for this Bitarray with the size of " + this.size
            );
        }
        if (bitIndex > this.maxBit) {
            this.maxBit = bitIndex;
        }

        this.bits[calcIndexForArray(bitIndex)] &= ~(1L << calcIndexForLong(bitIndex));
    }

    /**
     * Get the value of the bit at the specified bitIndex.
     * @param bitIndex The index of the bit whose value is to be returned.
     * @return true, if the bit is set (has the value of 1 or is "true"), false otherwise.
     * @throws IndexOutOfBoundsException If bitIndex is negative or out of bounds for the instance of this object.
     */
    public boolean getBit(int bitIndex) {
        if (!isValidIndex(bitIndex)) {
            throw new IndexOutOfBoundsException(
                "Index " + bitIndex + " out of bounds for this Bitarray with the size of " + this.size
            );
        }

        return (this.bits[calcIndexForArray(bitIndex)] & (1L << calcIndexForLong(bitIndex))) != 0;
    }

    /**
     * The size of bits this instance can store.
     * @return Amount of bits this instance can store.
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Returns the index of the most significant bit that has been altered.
     * @return Index of the most significant bit that has been altered.
     */
    public int getMostSignificantBit() {
        return this.maxBit;
    }

    /**
     * Textual representation of this bitarray. Most significant bit is
     * printed towards the left and the "smallest" bit which also uses
     * the lowest index is printed to the end (right).
     * @return String representing the bitarray bits.
     */
    @Override
    public String toString() {
        // Add room for spaces betwen bytes.
        int strSize = getSize() + (getSize() / 8 - 1);
        char[] bitValues = new char[strSize];

        int o  = 0; // Helper offset index for spaces.
        int ri = -1; // Reverse index for filling arr from end towards beginning.

        for (int i = 0; i < getSize(); i++) {
            ri = (strSize-1) - (i+o);
            if (i % 8 == 0 && i != 0) {
                // Insert space between bytes.
                bitValues[ri] = ' ';
                o++;
                ri--; // Decrement offseted reverse index to correct for space.
            }

            // Use char '1' if the bit is set, '0' otherwise.
            bitValues[ri] = this.getBit(i) ? '1' : '0';
        }

        return new String(bitValues);
    }

    /* THIS IS NOT A JAVADOC SO WE NEED TO ADD TEXT HERE*
     * Resizes the bitarray and updates all object fields
     * to match the new size. Resizes to the smallest
     * effective size that is at least as big as the
     * specified newSize argument.
     * @param newSize The new desired size of the array. The new
     *                size will be between [newSize..newSize + 63] bits.
     */
    /* THIS METHOD IS NOT YET TESTED NOR IN USE, "maybe needed later"
    private void expand(int newSize) {
        int newLength = computeNeededLength(newSize);
        newSize = newLength * DATA_WIDTH;

        long[] bitArr = new long[newLength];
        for (int i = 0; i <= this.maxBit; i++) {
            // We could copy the old array, but if the class
            // works correctly, it is sufficient to copy only
            // up to the most significant bit that has been
            // touched.
            bitArr[i] = this.bits[i];
        }

        this.length = newLength;
        this.size = newSize;
        this.bits = bitArr;
    }
*/

    /**
     * Computes the needed length for the array to store size amount of bits.
     * @param size The size of the bitarray, i.e. the amount of bits to store.
     * @return The minimum size of the array to store size bits.
     */
    private int computeNeededLength(int size) {
        return ((size-1) / DATA_WIDTH) + 1;
    }

    /**
     * Computes the correct index of the array. That is the index for
     * the long that holds the specified bit.
     * @param bitIndex Index of the desired bit.
     * @return The index of the array that holds the wanted bit.
     */
    private int calcIndexForArray(int bitIndex) {
        return bitIndex / 64;
    }

    /**
     * Computes the index of the bit in the primitive long.
     * @param bitIndex Index of the desired bit.
     * @return The index of the long that holds the wanted bit.
     */
    private int calcIndexForLong(int bitIndex) {
        return bitIndex % 64;
    }

    /**
     * Helper method that checks the index is in bounds.
     * @param bitIndex Index of the desired bit.
     * @return true, if the index is a valid one and false otherwise.
     */
    private boolean isValidIndex(int bitIndex) {
        return bitIndex < this.size && bitIndex >= 0;
    }
}
