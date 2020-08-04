package comppa.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class BitarrayTest {

    private Bitarray bitarray;

    @Rule
    public ExpectedException thrownException = ExpectedException.none();

    @Before
    public void setUp() {
        bitarray = new Bitarray();
    }

    @Test
    public void isCreatedWithCorrectDefaultSize() {
        assertEquals(Bitarray.DEF_SIZE, bitarray.getSize());
    }

    /**
     * Ensures that the bitarray is created with the correct
     * amount of bits to satisfy the constructor parameter
     * and does not use more space than absolutely necessary.
     * That is at most 63 bits over the constructor argument
     * is used.
     */
    @Test
    public void isCreatedWithTheMinimumEfficientSize() {
        for (int i = 1; i < 16386; i++) {
            int size = (i % 64 == 0) ? i : ((i / 64)+1) * 64;
            assertEquals(size, new Bitarray(i).getSize());
        }
    }

    @Test
    public void isCreatedWithTheCorrectHugeSize() {
        assertEquals(960_007_872, new Bitarray(960_007_844).getSize());
    }

    @Test
    public void isCreatedWithAllBitsUnset() {
        for (int i = 0; i < bitarray.getSize(); i++) {
            assertEquals(false, bitarray.getBit(i));
        }
    }

    @Test
    public void allBitsCanBeSet() {
        for (int i = 0; i < bitarray.getSize(); i++) {
            bitarray.setBit(i);
            assertEquals(true, bitarray.getBit(i));
        }
    }

    @Test
    public void allBitsCanBeUnset() {
        for (int i = 0; i < bitarray.getSize(); i++) {
            bitarray.setBit(i);
            bitarray.unsetBit(i);
            assertEquals(false, bitarray.getBit(i));
        }
    }

    @Test
    public void mostSignificantBitIsNegativeOneAfterInitialization() {
        assertEquals(-1, bitarray.getMostSignificantBit());
    }

    @Test
    public void settingNewSignificantBitUpdatesMostSignificantBit() {
        for (int i = 0; i < bitarray.getSize(); i++) {
            bitarray.setBit(i);
            assertEquals(i, bitarray.getMostSignificantBit());
        }
    }

    @Test
    public void unsettingNewSignificantBitUpdatesMostSignificantBit() {
        for (int i = 0; i < bitarray.getSize(); i++) {
            bitarray.unsetBit(i);
            assertEquals(i, bitarray.getMostSignificantBit());
        }
    }

    @Test
    public void settingOrUnsettingBitLessThanSignificantBitDoesNotChangeSignificantBit() {
        for (int i = 63; i >= 0; i--) {
            bitarray.setBit(i);
        }
        assertEquals(63, bitarray.getMostSignificantBit());

        for (int i = 63; i >= 0; i--) {
            bitarray.unsetBit(i);
        }

        assertEquals(63, bitarray.getMostSignificantBit());
    }

    @Test
    public void ensureExceptionIsRaisedWithNegativeIndexForGetBit() {
        setIOoBExceptionExpectation(-1, Bitarray.DEF_SIZE);
        bitarray.getBit(-1);
    }

    @Test
    public void ensureExceptionIsRaisedWithTooBigIndexForGetBit() {
        setIOoBExceptionExpectation(Bitarray.DEF_SIZE, Bitarray.DEF_SIZE);
        bitarray.getBit(Bitarray.DEF_SIZE);
    }

    @Test
    public void ensureExceptionIsRaisedWithNegativeIndexForSetBit() {
        // @TODO: If Bitarray is not refactored to be resizable we also
        //        need to test this method for too big indexes.
        setIOoBExceptionExpectation(-1, Bitarray.DEF_SIZE);
        bitarray.setBit(-1);
    }

    @Test
    public void ensureExceptionIsRaisedWithNegativeIndexForUnsetBit() {
        // @TODO: If Bitarray is not refactored to be resizable we also
        //        need to test this method for too big indexes.
        setIOoBExceptionExpectation(-1, Bitarray.DEF_SIZE);
        bitarray.unsetBit(-1);
    }

    @Test
    public void toStringReturnsCorrectInitialString() {
        String expected = "00000000 00000000 00000000 00000000 00000000 00000000 00000000 00000000";
        assertEquals(expected, bitarray.toString());
    }

    @Test
    public void toStringReturnsCorrectStringWithFirstBitSet() {
        String expected = "00000000 00000000 00000000 00000000 00000000 00000000 00000000 00000001";
        bitarray.setBit(0);
        assertEquals(expected, bitarray.toString());
    }

    @Test
    public void toStringReturnsCorrectStringWithLastBitSet() {
        String expected = "10000000 00000000 00000000 00000000 00000000 00000000 00000000 00000000";
        bitarray.setBit(63);
        assertEquals(expected, bitarray.toString());
    }

    private void setIOoBExceptionExpectation(int index, int bitArrSize) {
        thrownException.expect(IndexOutOfBoundsException.class);
        thrownException.expectMessage("Index " + index + " out of bounds for this Bitarray with the size of " + bitArrSize);
    }
}
