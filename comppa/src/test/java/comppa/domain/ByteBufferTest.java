package comppa.domain;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ByteBufferTest {

    @Rule
    public ExpectedException thrownException = ExpectedException.none();

    private ByteBuffer byteBuffer;

    @Before
    public void setUp() {
        this.byteBuffer = new ByteBuffer();
    }

    @Test
    public void isCreatedEmpty() {
        assertEquals(0, byteBuffer.getCurrentByte());
    }

    @Test
    public void isFullReturnsFalseAfterInitialization() {
        assertFalse(byteBuffer.isFull());
    }

    @Test
    public void appendOneSetBitReturnsCorrectByte() {
        assertFalse(byteBuffer.append(true));
        assertEquals(1, byteBuffer.getCurrentByte());
    }

    @Test
    public void appendingAlteringBitsReturnCorrectByte() {
        // Should return the byte 10101010
        assertFalse(byteBuffer.append(false));
        assertFalse(byteBuffer.append(true));
        assertFalse(byteBuffer.append(false));
        assertFalse(byteBuffer.append(true));
        assertFalse(byteBuffer.append(false));
        assertFalse(byteBuffer.append(true));
        assertFalse(byteBuffer.append(false));
        assertTrue(byteBuffer.append(true));
        assertEquals((byte) 170, byteBuffer.getCurrentByte());
    }

    @Test
    public void appendingFalseValuesReturnEmptyByte() {
        for (int i = 0; i < 7; i++) {
            assertFalse(byteBuffer.append(false));
        }
        assertTrue(byteBuffer.append(false));
        assertEquals((byte) 0, byteBuffer.getCurrentByte());
    }

    @Test
    public void appendingTrueValuesReturnByteWithAllBitsSet() {
        for (int i = 0; i < 7; i++) {
            assertFalse(byteBuffer.append(true));
        }
        assertTrue(byteBuffer.append(true));
        assertEquals((byte) -1, byteBuffer.getCurrentByte());
    }

    @Test
    public void appendingBitsToRepresentValueOneReturnsCorrectByte() {
        assertFalse(byteBuffer.append(true));
        for (int i = 0; i < 6; i++) {
            assertFalse(byteBuffer.append(false));
        }
        assertTrue(byteBuffer.append((false)));
        assertEquals((byte) 1, byteBuffer.getCurrentByte());
    }

    @Test
    public void appendingBitsToRepresentByteMinValReturnsCorrectByte() {
        for (int i = 0; i < 7; i++) {
            assertFalse(byteBuffer.append(false));
        }
        assertTrue(byteBuffer.append(true));
        assertEquals((byte) -128, byteBuffer.getCurrentByte());
    }

    @Test
    public void appendingBitsToRepresentByteMaxValReturnsCorrectByte() {
        for (int i = 0; i < 7; i++) {
            assertFalse(byteBuffer.append(true));
        }
        assertTrue(byteBuffer.append(false));
        assertEquals((byte) 127, byteBuffer.getCurrentByte());
    }

    @Test
    public void appendingToAFullByteThrowsException() {
        thrownException.expect(RuntimeException.class);
        thrownException.expectMessage("Trying to append a bit to a full byte!");

        for (int i = 0; i < 7; i++) {
            assertFalse(byteBuffer.append(true));
        }
        assertTrue(byteBuffer.append(true));

        // Should throw exception
        byteBuffer.append(true);
    }

    @Test
    public void gettingCurrentByteResetsBuffer() {
        assertFalse(byteBuffer.append(false));
        assertFalse(byteBuffer.append(true));
        for (int i = 0; i < 5; i++) {
            assertFalse(byteBuffer.append(false));
        }
        assertTrue(byteBuffer.append(false));

        assertEquals((byte) 2, byteBuffer.getCurrentByte());

        // Should not throw exception after getting current byte
        assertFalse(byteBuffer.append(true));
        assertEquals(1, byteBuffer.getCurrentByte());
    }

}
