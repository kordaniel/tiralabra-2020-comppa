package comppa.domain;

/**
 * Class for building bytes from single bits. Constructs the bytes
 * by appending bits to the end of the byte. That is every added
 * bit is the new least significant bit.
 * @author danielko
 */
public class ByteBuffer {

    private static final byte BYTE_WIDTH = 8;

    private byte buff;
    private byte bitsCount;

    /**
     * Constructor. Creates an empty ByteBuffer object.
     */
    public ByteBuffer() {
        this.buff      = 0;
        this.bitsCount = 0;
    }

    /**
     * Appends one bit to the right of the byte. If passed bitIsSet
     * argument true, sets the bit to be 1. If false sets the bit to 0.
     * @param bitIsSet Value of the bit to be appended.
     * @return true, if the byte is full, or in other words every bit is appended to it. False otherwise.
     * @throws RuntimeException If trying to append a bit to a full byte.
     */
    public boolean append(boolean bitIsSet) {
        if (bitsCount == BYTE_WIDTH) {
            throw new RuntimeException("Trying to append a bit to a full byte!");
        }

        bitsCount++;
        this.buff <<= 1;

        if (bitIsSet) {
            this.buff |= 1;
        }

        return isFull();
    }

    /**
     * Return the current byte and resets the buffer to be empty.
     * @return The current byte value.
     */
    public byte getCurrentByte() {
        byte currentByte = this.buff;
        this.buff = 0;
        this.bitsCount = 0;
        return currentByte;
    }

    /**
     * Simple getter that returns a boolean depending on whether the byte is full.
     * @return true, if all the bits in this buffer is appended, false otherwise.
     */
    public boolean isFull() {
        return this.bitsCount == 8;
    }

}
