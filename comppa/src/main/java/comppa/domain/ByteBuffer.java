package comppa.domain;

/**
 * Stub class to build bytes from bits.
 * @author danielko
 */
public class ByteBuffer {

    private int buff;
    private int bitsCount;

    public ByteBuffer() {
        this.buff      = 0;
        this.bitsCount = 0;
    }

    public boolean append(boolean bitIsSet) {
        if (bitsCount == 8) {
            throw new RuntimeException("Trying to append a bit to a full byte!");
        }

        bitsCount++;
        this.buff <<= 1;

        if (bitIsSet) {
            this.buff |= 1;
        }

        return this.bitsCount == 8;
    }

    public byte getCurrentByte() {
        byte currentByte = (byte) this.buff;
        this.buff = 0;
        this.bitsCount = 0;
        return currentByte;
    }

}
