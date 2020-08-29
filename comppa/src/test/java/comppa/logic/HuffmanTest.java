package comppa.logic;

import comppa.domain.Constants;
import comppa.domain.HuffmanNode;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertTrue;

public class HuffmanTest {

    private Random random;
    private Huffman huffman;
    private byte[] allBytes;            // Array containing all possible byte values.
    private byte[] fileBytesMock;       // Array containing various bytes, ~uniformly distributed. This can
                                        // be interpreted as representing the bytes read from a file.
    private byte[] fileBytesMockSkewed; // Array containing various bytes with an skewed distribution.

    public HuffmanTest() {
        int seed = 1337;
        random = new Random(seed);

        allBytes = new byte[Constants.BYTE_SIZE];
        for (int i = 0; i < Constants.BYTE_SIZE; i++) {
            allBytes[i] = (byte) (i - (Constants.BYTE_SIZE/2));
        }

        int fileSize = 1024 * 1024 * 1; // 1 MB
        fileBytesMock = new byte[fileSize];
        for (int i = 0; i < fileSize; i++) {
                fileBytesMock[i] = allBytes[random.nextInt(Constants.BYTE_SIZE)];
        }

        populateSkewedFileBytes(fileSize * 3); // 3MB
    }

    private void populateSkewedFileBytes(int fileSize) {
        int stdDeviation = Constants.BYTE_SIZE/(2*3); // ~70% of byte vals should be in the range [-42,42]
                                                      // ~95% in the range [-84,84]
                                                      // and ~99% in the range [-126,126]
        fileBytesMockSkewed = new byte[fileSize];
        for (int i = 0; i < fileSize; i++) {
            int byteValue;
            do {
                byteValue = (int) Math.round(random.nextGaussian() * stdDeviation);
            } while (byteValue < -128 || byteValue > 127);
            fileBytesMockSkewed[i] = (byte) byteValue;
        }

        /* Print distribution of the bytes frequencies, for debugging
        TreeMap<Byte, Integer> jakauma = new TreeMap<>();
        for (int i = 0; i < fileSize; i++) {
            jakauma.put(fileBytesMockSkewed[i], jakauma.getOrDefault(fileBytesMockSkewed[i], 0) + 1);
        }
        for (Map.Entry<Byte, Integer> entry : jakauma.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
         */
    }

    @Before
    public void setUp() {
        huffman = new Huffman();
    }

    @Test
    public void testTrie() {
        byte[] huffEncoded = huffman.compress(allBytes);
        byte[] huffDecoded = huffman.decompress(huffEncoded); // To build the trie from the compressed bytes

        HuffmanNode originalRootNode = huffman.getRootNode();
        HuffmanNode calculatRootNode = huffman.getRootNode2();
        assertTrue(travelTrie(originalRootNode, calculatRootNode));
    }

    @Test
    public void testTrie2() {
        byte[] huffEncoded = huffman.compress(fileBytesMock);
        byte[] huffDecoded = huffman.decompress(huffEncoded); // To build the trie from the compressed bytes

        HuffmanNode originalRootNode = huffman.getRootNode();
        HuffmanNode calculatRootNode = huffman.getRootNode2();
        assertTrue(travelTrie(originalRootNode, calculatRootNode));
    }

    @Test
    public void testTrie3() {
        byte[] huffEncoded = huffman.compress(fileBytesMockSkewed);
        byte[] huffDecoded = huffman.decompress(huffEncoded);

        assertTrue(travelTrie(huffman.getRootNode(), huffman.getRootNode2()));
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
