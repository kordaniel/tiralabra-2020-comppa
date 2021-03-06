package comppa.datastructures;

import comppa.domain.Constants;
import comppa.logic.Maath;

/**
 * Dynamically growing Array for storing bytes.
 * @author danielko
 */
public class ArrayLista {

    // The default size of a newly created ArrayLista
    public static final int DEFAULT_SIZE = 8;
    // The default factor used for expanding this Array
    public static final int DEFAULT_GROW_FACTOR = 2;

    // The actual byte values stored in this instance of ArrayLista
    private byte[] data;
    // The current size of this instance of ArrayLista. This variable is used as the index
    // when appending new objects to the end of this ArrayLista.
    private int size;

    /**
     * Constructs a new ArrayLista with default initial size.
     */
    public ArrayLista() {
        this(DEFAULT_SIZE);
    }

    /**
     * Constructs a new ArrayLista with the size specified as the argument.
     * @param initialSize The size for the new object.
     */
    public ArrayLista(int initialSize) {
        this.data = new byte[initialSize];
        this.size = 0;
    }

    /**
     * Appends a new byte to the end of this object.
     * @param b The byte to be appended to the end of this ArrayLista object.
     */
    public void add(byte b) {
        if (this.size() == this.data.length) {
            this.expand();
        }

        this.data[this.size++] = b;
    }

    /**
     * Sets the byte at index i to the value of b.
     * @param i The index of the byte to be set.
     * @param b The value to be stored at the specified index.
     */
    public void set(int i,  byte b) {
        this.expand(i + 1); // Make sure the array length is long enough.

        this.data[i] = b;

        if (i >= this.size()) {
            this.size = ++i;
        }
    }

    /**
     * Returns the byte at the specified index.
     * @param i The index of the byte to be returned.
     * @return The byte value at the specified index.
     * @throws IndexOutOfBoundsException If passed an index that is negative or at
     * least equal to the size of this object.
     */
    public byte get(int i) {
        if (i < 0 || i >= this.size()) {
            throw new IndexOutOfBoundsException(
                    "Index " + i + " out of bounds for Arraylista with size of " + this.size()
            );
        }

        return this.data[i];
    }

    /**
     * Returns a new array object containing all bytes stored in this ArrayLista.
     * @return byte[] array containing all bytes stored in this ArrayLista.
     */
    public byte[] toArray() {
        byte[] bytes = new byte[this.size()];

        for (int i = 0; i < this.size(); i++) {
            bytes[i] = this.data[i];
        }

        return bytes;
    }

    /**
     * The size of this object. That is, how many bytes are stored in this object.
     * @return The size of this object.
     */
    public int size() {
        return this.size;
    }

    /**
     * Checks whether this object is empty or not.
     * @return boolean, true if this object is empty and false otherwise.
     */
    public boolean isEmpty() {
        return this.size() == 0;
    }

    /**
     * The textual representation of the contents in this ArrayLista. Every byte is printed as it's decimal value.
     * @return
     */
    @Override
    public String toString() {
        int strSize = this.size() * 6 + 2; // Reserve room for brackets [] and ", " between bytes
        char[] str = new char[strSize];

        int strIndex = 0;
        str[strIndex++] = '[';

        for (int i = 0; i < this.size(); i++) {
            if (i > 0) {
                str[strIndex++] = ',';
                str[strIndex++] = ' ';
            }

            if (this.data[i] == 0) {
                str[strIndex++] = '0';
                continue;
            }

            int byteVal = this.data[i];

            if (byteVal < 0) {
                str[strIndex++] = '-';
            }

            char[] byteValDigits = new char[3];
            int digitIndex = 3;

            while (byteVal != 0) {
                digitIndex--;
                byteValDigits[digitIndex] = (char) (48 + (Maath.abs(byteVal) % 10));
                byteVal /= 10;
            }

            while (digitIndex < 3) {
                str[strIndex++] = byteValDigits[digitIndex];
                digitIndex++;
            }
        }

        str[strIndex] = ']';

        return new String(str);
    }

    /**
     * Helper method that calculates the correct required size for the array and if needed,
     * allocates space to the array.
     * @param requiredSize
     */
    private void expand(int requiredSize) {
        int newSize = this.data.length;

        while (newSize < requiredSize) {
            newSize = newSize * DEFAULT_GROW_FACTOR < newSize
                    ? Constants.INT_MAX_VAL
                    : newSize * DEFAULT_GROW_FACTOR;
        }

        allocate(newSize);
    }

    /**
     * Helper method that does required checks and calculations for expanding the array with DEFAULT_GROW_FACTOR.
     * @throws OutOfMemoryError If the Array is growing too large.
     */
    private void expand() {
        if (this.size() == Constants.INT_MAX_VAL) {
            throw new OutOfMemoryError("Cannot add more elements to this ArrayLista of length + " + this.size());
        }

        // Try to expand the size by multiplying with DEFAULT_GROW_FACTOR. Check for integer overflow
        // and if the newSize overflows, then expand the array to the max possible size. For most environments
        // it is required to adjust the max heap space for the Java VM to be able to use arrays this big.
        int newSize = this.data.length * DEFAULT_GROW_FACTOR;
        newSize = newSize < this.data.length ? Constants.INT_MAX_VAL : newSize;

        allocate(newSize);
    }

    /**
     * Helper method that instantiates a new byte-array with the size passed as argument and copies all stored bytes
     * into the new array.
     * @param newSize The size of the expanded ArrayLista.
     */
    private void allocate(int newSize) {
        byte[] newArr = new byte[newSize];

        for (int i = 0; i < this.size(); i++) {
            newArr[i] = this.data[i];
        }

        this.data = newArr;
    }

}
