package logic;

import comppa.domain.Constants;
import comppa.domain.HuffmanNode;
import comppa.logic.Huffman;
import jdk.jshell.EvalException;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.junit.Assert.assertTrue;

public class HuffmanTest {

    private Random random;
    private Huffman huffman;
    private byte[] allBytes;  // Array containing all possible byte values.
    private byte[] fileBytesMock; // Array containing various bytes. This can be interpreted as
                                  // representing the bytes read from a file.

    public HuffmanTest() {
        int seed = 1337;
        random = new Random(seed);

        allBytes = new byte[Constants.BYTE_SIZE];
        for (int i = 0; i < Constants.BYTE_SIZE; i++) {
            allBytes[i] = (byte) (i - (Constants.BYTE_SIZE/2));
        }

        int fileSize = 1024 * 1024 * 2; // 2 MB
        fileBytesMock = new byte[fileSize];
        for (int i = 0; i < fileSize; i++) {
                fileBytesMock[i] = allBytes[random.nextInt(Constants.BYTE_SIZE)];

        }
    }

    @Before
    public void setUp() {
        huffman = new Huffman();
    }

    @Test
    public void testTrie() {
        huffman.compress(allBytes);
        HuffmanNode originalRootNode = huffman.getRootNode();
        HuffmanNode calculatRootNode = huffman.getRootNode2();
        assertTrue(travelTrie(originalRootNode, calculatRootNode));
    }

    @Test
    public void testTrie2() {
        huffman.compress(fileBytesMock);
        HuffmanNode originalRootNode = huffman.getRootNode();
        HuffmanNode calculatRootNode = huffman.getRootNode2();
        assertTrue(travelTrie(originalRootNode, calculatRootNode));
    }

    /** Helper method to travel the Trie. Travels both the original Trie that is constructed while compressing
     * the bytes from the file and the Trie that is constructed from the compressed file.
     * @param original The (Initially root) node that has been created while compressing the file.
     * @param constructedFromEncodedFile The (Initally root) node that has been constructed from the compressed file.
     * @return true, if both of the Tries have the same shape (prefix codes) and byte values, false otherwise.
     */
    private boolean travelTrie(HuffmanNode original, HuffmanNode constructedFromEncodedFile) {
        if (original.isLeaf() && constructedFromEncodedFile.isLeaf()) {
            return original.getNodeByte() == constructedFromEncodedFile.getNodeByte();
        }

        if (original.isLeaf() || constructedFromEncodedFile.isLeaf()) {
            return false;
        }

        boolean leftLegEquals  = travelTrie(original.getLeft(), constructedFromEncodedFile.getLeft());
        boolean rightLegEquals = travelTrie(original.getRight(), constructedFromEncodedFile.getRight());

        return leftLegEquals && rightLegEquals;
    }

}
