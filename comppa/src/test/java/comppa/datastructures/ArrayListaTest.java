package comppa.datastructures;

import comppa.domain.Constants;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class ArrayListaTest {

    @Rule
    public ExpectedException thrownException = ExpectedException.none();

    private ArrayLista aLista;

    private static Random random;
    private static int expectedBytesSize;
    private static byte[] expectedBytes;

    @BeforeClass
    public static void initializeClass() {
        int seed = 1337;
        random = new Random(seed);

        // 2^15 * 8 + 1 = 262145 => The ArrayLista constructed with default size will be
        //                          expanded 16 times if expectedBytesSize bytes are added to it.
        expectedBytesSize = (2 << 14) * ArrayLista.DEFAULT_SIZE + 1;
        expectedBytes = new byte[expectedBytesSize];

        for (int i = 0; i < expectedBytesSize; i++) {
            expectedBytes[i] = (byte) (random.nextInt(Constants.BYTE_SIZE) - 128);
        }
    }

    @Before
    public void setUp() {
        this.aLista = new ArrayLista();
    }

    @Test
    public void arrayListaIsEmptyAfterCreation() {
        assertTrue(aLista.isEmpty());
    }

    @Test
    public void arrayListaHasCorrectSizeWhenEmpty() {
        assertEquals(0, aLista.size());
    }

    @Test
    public void arrayListaIsNotEmptyAfterAddingElement() {
        aLista.add((byte) 99);
        assertFalse(aLista.isEmpty());
    }

    @Test
    public void arrayListaHasCorrectSizeAfterAddingElement() {
        aLista.add((byte) -1);
        assertEquals(1, aLista.size());
    }

    @Test
    public void getReturnsCorrectElement() {
        aLista.add((byte) -109);
        aLista.add((byte) 1);
        aLista.add((byte) -128);
        aLista.add((byte) 127);
        aLista.add((byte) 0);

        assertEquals(127, aLista.get(3));
        assertEquals(-109, aLista.get(0));
        assertEquals(0, aLista.get(4));
        assertEquals(1, aLista.get(1));
        assertEquals(-128, aLista.get(2));
    }

    @Test
    public void getNegativeIndexThrowsException() {
        thrownException.expect(IndexOutOfBoundsException.class);
        thrownException.expectMessage("Index -1 out of bounds for Arraylista with size of 0");
        aLista.get(-1);
    }

    @Test
    public void getIndexEqualToListSizeRaisesException() {
        thrownException.expect(IndexOutOfBoundsException.class);
        thrownException.expectMessage("Index 2 out of bounds for Arraylista with size of 2");

        aLista.add((byte) 1);
        aLista.add((byte) -1);

        aLista.get(2);
    }

    @Test
    public void getIndexLargerThanListSizeRaisesException() {
        thrownException.expect(IndexOutOfBoundsException.class);
        thrownException.expectMessage("Index 4 out of bounds for Arraylista with size of 3");

        aLista.add((byte) 1);
        aLista.add((byte) -1);
        aLista.add((byte) 127);

        aLista.get(4);
    }

    @Test
    public void largeAmountOfBytesCanBeAddedAndRetrieved() {
        for (int i = 0; i < expectedBytesSize; i++) {
            aLista.add(expectedBytes[i]);

            // Check that size grows while adding bytes
            assertEquals(i + 1, aLista.size());
        }

        for (int i = 0; i < expectedBytesSize; i++) {
            assertEquals(expectedBytes[i], aLista.get(i));
        }
    }
}
