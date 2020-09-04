package comppa.domain;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.BitSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

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
        setIOoBExceptionExpectation(-1, Bitarray.DEF_SIZE);
        bitarray.setBit(-1);
    }

    @Test
    public void ensureExceptionIsRaisedWithNegativeIndexForUnsetBit() {
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

    @Test
    public void equalsIsTrueForSameObject() {
        assertTrue(bitarray.equals(bitarray));
    }

    @Test
    public void equalsIsTrueForTwoEmptyObjects() {
        Bitarray other = new Bitarray();
        assertTrue(bitarray.equals(other));
        assertTrue(other.equals(bitarray));
    }

    @Test
    public void equalsIsFalseForNullObject() {
        assertFalse(bitarray.equals(null));
    }

    @Test
    public void equalsIsFalseForOtherClass() {
        assertFalse(bitarray.equals(new BitSet()));
    }

    @Test
    public void equalsIsTrueForTwoIdenticalObjects() {
        Bitarray other = new Bitarray();

        for (int i = 0; i < bitarray.getSize(); i+= 7) {
            bitarray.setBit(i);
            other.setBit(i);
        }
        for (int i = 7; i < 14; i++) {
            bitarray.unsetBit(i);
            other.unsetBit(i);
        }

        assertTrue(bitarray.equals(other));
        assertTrue(other.equals(bitarray));
    }

    @Test
    public void equalsIsFalseForDiffSizeWithSameContent() {
        Bitarray other = new Bitarray(192);

        int[] setBits = {0, 10, 11, 23, 48, 62, 63};
        for (int i : setBits) {
            bitarray.setBit(i);
            other.setBit(i);
        }

        assertFalse(bitarray.equals(other));
        assertFalse(other.equals(bitarray));
    }

    @Test
    public void cloneReturnsNewObject() throws CloneNotSupportedException {
        Bitarray clonedObj = (Bitarray) bitarray.clone();
        assertTrue(clonedObj != bitarray);
    }

    @Test
    public void cloneReturnsBitarray() throws CloneNotSupportedException {
        Bitarray clonedObj = (Bitarray) bitarray.clone();
        assertTrue(clonedObj instanceof Bitarray);
    }

    @Test
    public void cloneReturnsEqualBitarray() throws CloneNotSupportedException {
        bitarray.setBit(0);
        bitarray.setBit(1);
        bitarray.setBit(48);
        bitarray.setBit(63);
        Bitarray clonedObj = (Bitarray) bitarray.clone();
        assertTrue(bitarray.equals(clonedObj));
    }

    @Test
    public void cloneReturnsDeepCopy() throws CloneNotSupportedException {
        bitarray.setBit(1);
        bitarray.setBit(5);
        Bitarray clonedObj = (Bitarray) bitarray.clone();
        assertEquals(bitarray, clonedObj);

        clonedObj.unsetBit(5);
        clonedObj.setBit(2);
        assertNotEquals(bitarray, clonedObj);

        assertTrue(bitarray.getBit(1));
        assertFalse(bitarray.getBit(2));
        assertTrue(bitarray.getBit(5));

        assertTrue(clonedObj.getBit(1));
        assertTrue(clonedObj.getBit(2));
        assertFalse(clonedObj.getBit(5));
    }

    @Test
    public void getReverseReturnsCorrectWhenEmpty() {
        Bitarray reversed = bitarray.getReversed();
        assertEquals(-1, reversed.getMostSignificantBit());
    }

    @Test
    public void getReverseReturnsCorrectSingleBit() {
        bitarray.setBit(0);
        Bitarray reversedSet = bitarray.getReversed();
        assertEquals(0, reversedSet.getMostSignificantBit());
        assertTrue(reversedSet.getBit(0));

        bitarray.unsetBit(0);
        Bitarray reversedUnset = bitarray.getReversed();
        assertEquals(0, reversedUnset.getMostSignificantBit());
        assertFalse(reversedUnset.getBit(0));
    }

    @Test
    public void getReverseReturnCorrectBitsWhenFirstBitIsUnset() {
        Bitarray reversed;

        // set bits: 01010101010
        for (int i = 0; i < 11; i++) {
            if (i % 2 == 0) {
                bitarray.unsetBit(i);
            } else {
                bitarray.setBit(i);
            }

            reversed = bitarray.getReversed();
            assertEquals(i, reversed.getMostSignificantBit());

            for (int j = 0; j <= i; j++) {
                assertEquals(bitarray.getBit(i - j), reversed.getBit(j));
            }
        }
    }

    @Test
    public void getReverseReturnCorrectBitsWhenFirstBitIsSet() {
        Bitarray reversed;

        // set bits: 1010101010
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                bitarray.setBit(i);
            } else {
                bitarray.unsetBit(i);
            }

            reversed = bitarray.getReversed();
            assertEquals(i, reversed.getMostSignificantBit());

            for (int j = 0; j <= i; j++) {
                assertEquals(bitarray.getBit(i - j), reversed.getBit(j));
            }
        }
    }

    private void setIOoBExceptionExpectation(int index, int bitArrSize) {
        thrownException.expect(IndexOutOfBoundsException.class);
        thrownException.expectMessage("Index " + index + " out of bounds for this Bitarray with the size of " + bitArrSize);
    }
}
