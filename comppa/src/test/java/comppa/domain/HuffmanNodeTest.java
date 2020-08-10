package comppa.domain;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;

public class HuffmanNodeTest {

    @Rule
    public ExpectedException thrownException = ExpectedException.none();

    private byte[] bytes;
    private int indexForZero;

    @Before
    public void setUp() {
        // Create an array filled with every possible byte value: [-128,127]
        this.bytes = new byte[Constants.BYTE_SIZE];
        for (short i = 0; i < Constants.BYTE_SIZE; i++) {
            this.bytes[i] = (byte) (i-(Constants.BYTE_SIZE/2));
        }

        this.indexForZero = Constants.BYTE_SIZE / 2;
    }

    @Test
    public void leafNodeCanBeCreated() {
        HuffmanNode huffmanNode;
        for (short i = 0; i < Constants.BYTE_SIZE; i++) {
            huffmanNode = new HuffmanNode(bytes[i], i);
            assertTrue(huffmanNode.isLeaf());
            assertNull(huffmanNode.getLeft());
            assertNull(huffmanNode.getRight());
        }
    }

    @Test
    public void constructorThrowsIllegalArgExceptionWithNegativeFrequency() {
        thrownException.expect(IllegalArgumentException.class);
        thrownException.expectMessage("Parameter frequency can not be negative.");
        HuffmanNode huffmanNode = new HuffmanNode(bytes[indexForZero], -1);
    }

    @Test
    public void leafNodeReturnsTheRightByte() {
        HuffmanNode huffmanNode;
        for (short i = 0; i < Constants.BYTE_SIZE; i++) {
            huffmanNode = new HuffmanNode(bytes[i], i);
            assertEquals(bytes[i], huffmanNode.getNodeByte());
        }
    }

    @Test
    public void leafNodeReturnsTheRightFrequency() {
        HuffmanNode huffmanNode;
        for (short i = 0; i < Constants.BYTE_SIZE; i++) {
            int frequency = Constants.INT_MAX_VAL / 255 * i;
            huffmanNode = new HuffmanNode(bytes[i], frequency);
            assertEquals(frequency, huffmanNode.getFrequency());
        }
    }

    @Test
    public void nonLeafNodeCanBeCreated() {
        HuffmanNode leftChild  = new HuffmanNode(bytes[indexForZero-1], 1);
        HuffmanNode rightChild = new HuffmanNode(bytes[indexForZero+1], Constants.INT_MAX_VAL-1);
        HuffmanNode parent     = new HuffmanNode(leftChild.getFrequency() + rightChild.getFrequency(), leftChild, rightChild);

        assertFalse(parent.isLeaf());
        assertEquals(Constants.INT_MAX_VAL, parent.getFrequency());
    }

    @Test
    public void compareToReturnsCorrectResultForTheSmallerToHaveHigherOrder() {
        HuffmanNode lowerFreq  = new HuffmanNode(bytes[2], 1);
        HuffmanNode higherFreq = new HuffmanNode(bytes[100], 2);
        assertEquals(-1, lowerFreq.compareTo(higherFreq));
        assertEquals(1, higherFreq.compareTo(lowerFreq));
    }
}
