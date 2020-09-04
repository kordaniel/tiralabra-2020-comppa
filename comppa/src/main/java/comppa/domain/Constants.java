package comppa.domain;

/**
 * Class that holds static final variables.
 * @author danielko
 */
public class Constants {

    // Width in bits
    public static final byte BYTE_WIDTH = 8;

    // The amount of different values a byte can hold, considering "unsigned" byte
    public static final int  BYTE_SIZE  = 1 << BYTE_WIDTH; // 256
    public static final byte BYTE_MASK = (byte) 1;

    // The max value this datatype can hold, one bit is for the sign so, signed max val
    public static final int INT_MAX_VAL = 0x7FFFFFFF;     // 2^31 - 1
    public static final int INT_MIN_VAL = 0x80000000;
    public static final int INT_WIDTH   = 32;
    public static final int INT_MASK    = 1;

    public static final int  LONG_WIDTH  = 64;

    public static final long UINT_MAX_VAL = 0xFFFFFFFFL; // 2^32 - 1
}
