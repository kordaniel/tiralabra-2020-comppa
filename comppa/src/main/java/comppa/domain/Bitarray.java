package comppa.domain;

import java.util.Arrays;

/**
 * A class that provides methods for manipulating single bits.
 * All methods for manipulating or examining individual bits take
 * constant time O(1). That is every method do at most 63 bit shifts.
 * For space complexity the overhead is at most 63 bits.
 * @author danielko
 */
public class Bitarray implements Cloneable {

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
        if (initialSize < 1) {
            throw new IllegalArgumentException("Cannot instantiate Bitarray with size less than 1");
        }

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
        if (!isValidIndex(bitIndex)) {
            throw new IndexOutOfBoundsException(
                "ERRRRRRRRRRRRRRRRRRR this should never be thrown"
            );
        }
        if (bitIndex > this.getMostSignificantBit()) {
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
        if (!isValidIndex(bitIndex)) {
            throw new IndexOutOfBoundsException(
                "ERRRRRRRRRRRRRRRRRRR this should never be thrown when unsetting bit..!"
            );
        }
        if (bitIndex > this.getMostSignificantBit()) {
            this.maxBit = bitIndex;
        }

        this.bits[calcIndexForArray(bitIndex)] &= ~(1L << calcIndexForLong(bitIndex));
    }

    /**
     * "Appends" a new bit to the bitarray. That is sets the next bit to the left (the "bigger" bit)
     * from the current mostSignificant altered bit to be 1 or 0 depending on the
     * bitIsSet parameter boolean value passed as argument.
     * @param bitIsSet Whether the bit should be set to 1 or 0. Sets the bit to 1 if passed
     *                 true and 0 if passed false.
     */
    public void appendBit(boolean bitIsSet) {
        if (bitIsSet) {
            this.setBit(this.getMostSignificantBit() + 1);
        } else {
            this.unsetBit(this.getMostSignificantBit() + 1);
        }
    }

    /**
     * Appends all the bits from the Bitarray passed as argument to this Bitarray.
     * @param bArr The object whose bits should be appended to this Bitarray.
     */
    public void appendBits(Bitarray bArr) {
        for (int i = 0; i <= bArr.getMostSignificantBit(); i++) {
            this.appendBit(bArr.getBit(i));
        }
    }

    /**
     * Get the value of the bit at the specified bitIndex.
     * @param bitIndex The index of the bit whose value is to be returned.
     * @return true, if the bit is set (has the value of 1 or is "true"), false otherwise.
     * @throws IndexOutOfBoundsException If bitIndex is negative or out of bounds for the instance of this object.
     */
    public boolean getBit(int bitIndex) {
        if (bitIndex < 0 || bitIndex >= this.getSize()) {
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

    public void setMostSignificantBit(int bitIndex) {
        // @TODO: Implement necessary checks
        this.maxBit = bitIndex;
    }

    /**
     * Textual representation of this bitarray. Most significant bit is
     * printed towards the left and the "smallest" bit which also uses
     * the lowest index is printed to the end (right).
     * @return String representing the bitarray bits.
     */
    @Override
    public String toString() {
        // Add room for spaces between bytes.
        int strSize = getSize() + (getSize() / 8 - 1);
        char[] bitValues = new char[strSize];

        int o  = 0; // Helper offset index for spaces.
        int ri = -1; // Reverse index for filling arr from end towards beginning.

        for (int i = 0; i < getSize(); i++) {
            ri = (strSize - 1) - (i + o);
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

    /**
     * Method that implements the Cloneable interface. Returns a
     * deep copy of this object. That is the returned clone will
     * be equal to the original object, but will have it's own
     * independent fields.
     * @return Deep copy of this Bitarray object.
     * @throws CloneNotSupportedException
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        Bitarray clone = (Bitarray) super.clone();
        clone.bits = Arrays.copyOf(this.bits, this.length);
        return clone;
    }

    /**
     * Compares this object with the one given as argument.
     * The two objects are considered to be equal, if and only if
     * they both are either instances of Bitarray-class with equal
     * filled variables, or both references are null.
     * @param o The object to compare this object to.
     * @return True, if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }

        Bitarray other = (Bitarray) o;
        return this.length == other.length &&
                this.size == other.size &&
                this.maxBit == other.maxBit &&
                Arrays.equals(this.bits, other.bits);
    }

    /**
     * Resizes the bitarray and updates all object fields
     * to match the new size. Resizes to the smallest
     * effective size that is at least as big as the
     * specified newSize argument.
     * @param newSize The new desired size of the array. The new
     *                size will be between [newSize..newSize + 63] bits.
     */
    private void expand(int newSize) {
        int newLength = computeNeededLength(newSize);
        newSize = newLength * DATA_WIDTH;

        long[] bitArr = new long[newLength];
        for (int i = 0; i <= this.calcIndexForArray(this.getMostSignificantBit()); i++) {
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

    /**
     * Computes the needed length for the array to store size amount of bits.
     * @param size The size of the bitarray, i.e. the amount of bits to store.
     * @return The minimum size of the array to store size bits.
     */
    private int computeNeededLength(int size) {
        return ((size - 1) / DATA_WIDTH) + 1;
    }

    /**
     * Computes the correct index of the array. That is the index for
     * the long that holds the specified bit.
     * @param bitIndex Index of the desired bit.
     * @return The index of the array that holds the wanted bit.
     */
    private int calcIndexForArray(int bitIndex) {
        return bitIndex / DATA_WIDTH;
    }

    /**
     * Computes the index of the bit in the primitive long.
     * @param bitIndex Index of the desired bit.
     * @return The index of the long that holds the wanted bit.
     */
    private int calcIndexForLong(int bitIndex) {
        return bitIndex % DATA_WIDTH;
    }

    /**
     * Helper method that checks the index is in bounds for this instance of BitArray.
     * If the index is bigger than what this instance currently has room for,
     * calculates a new size and enlarges the array of bits.
     * @param bitIndex Index of the desired bit.
     * @return true, if the index is a valid one and false otherwise.
     * @throws IndexOutOfBoundsException If bitIndex is negative or out of bounds for the instance of this object.
     */
    private boolean isValidIndex(int bitIndex) throws IndexOutOfBoundsException {
        if (bitIndex < 0) {
            // @TODO: Maybe we need negative indexing from the end, if so, calculate the correct index here
            throw new IndexOutOfBoundsException(
                    "Index " + bitIndex + " out of bounds for this Bitarray with the size of " + this.getSize()
            );
        }

        if (bitIndex < this.getSize()) {
            return true;
        }

        // @TODO: Add check for index growing too large
        int newSize = this.getSize();
        while (newSize <= bitIndex) {
            newSize *= 2;
        }
        this.expand(newSize);


        return bitIndex < this.getSize() && bitIndex >= 0;
    }
}
