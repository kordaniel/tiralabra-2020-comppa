package comppa.logic;

import comppa.domain.Constants;
import comppa.domain.HuffmanNode;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

//import java.util.Map;
import java.util.Random;
//import java.util.TreeMap;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

public class HuffmanTest {

    private Huffman huffman;

    private static Random random;
    private static byte[] allBytes;                     // Array containing all possible byte values.
    private static byte[] allBytesReverse;              // Array containing all possible byte values in reversed order.
    private static byte[] fileBytesMock;                // Array containing various bytes, ~uniformly distributed. This
                                                        // can be interpreted as representing the bytes read from a file.
    private static byte[] fileBytesMockReverse;         // Previous array in reversed order.
    private static byte[] fileBytesMockSkewed;          // Array containing various bytes with a skewed distribution.
    private static byte[] fileBytesMockSkewedReverse;   // Previous array in reversed order.

    /**
     * Initialize arrays only one time.
     */
    @BeforeClass
    public static void initializeClass() {
        int seed = 1337;
        random = new Random(seed);

        allBytes = new byte[Constants.BYTE_SIZE];
        allBytesReverse = new byte[Constants.BYTE_SIZE];

        for (int i = 0; i < Constants.BYTE_SIZE; i++) {
            byte aByte = (byte) (i - (Constants.BYTE_SIZE/2));
            allBytes[i] = aByte;
            allBytesReverse[Constants.BYTE_SIZE - 1 - i] = aByte;
        }

        int fileBytesMockFileSize = 1024 * 1024 * 1; // 1 MB

        fileBytesMock = new byte[fileBytesMockFileSize];
        fileBytesMockReverse = new byte[fileBytesMockFileSize];

        for (int i = 0; i < fileBytesMockFileSize; i++) {
            byte aByte = allBytes[random.nextInt(Constants.BYTE_SIZE)];
            fileBytesMock[i] = aByte;
            fileBytesMockReverse[fileBytesMockFileSize - 1 - i] = aByte;
        }

        populateSkewedFileBytes(1024 * 1024 * 3); // 3MB
    }

    /**
     * Set up a new Huffman object for every test.
     */
    @Before
    public void setUp() {
        huffman = new Huffman();
    }

    @Test
    public void testTrieWithAllBytesOnce() {
        byte[] huffEncoded = huffman.compress(allBytes);
        byte[] huffDecoded = huffman.decompress(huffEncoded); // To build the trie from the compressed bytes

        HuffmanNode originalRootNode = huffman.getRootNode();
        HuffmanNode calculatRootNode = huffman.getRootNode2();
        assertTrue(travelTrie(originalRootNode, calculatRootNode));
    }

    @Test
    public void testTrieWithRandomBytes() {
        byte[] huffEncoded = huffman.compress(fileBytesMock);
        byte[] huffDecoded = huffman.decompress(huffEncoded); // To build the trie from the compressed bytes

        HuffmanNode originalRootNode = huffman.getRootNode();
        HuffmanNode calculatRootNode = huffman.getRootNode2();
        assertTrue(travelTrie(originalRootNode, calculatRootNode));
    }

    @Test
    public void testTrieWithAllBytesRandomNormallyDistributed() {
        byte[] huffEncoded = huffman.compress(fileBytesMockSkewed);
        byte[] huffDecoded = huffman.decompress(huffEncoded);

        assertTrue(travelTrie(huffman.getRootNode(), huffman.getRootNode2()));
    }

    @Test
    public void testEncodingDecodingWithAllBytesOnce() {
        byte[] huffDecoded = huffman.decompress(huffman.compress(allBytes));
        assertArrayEquals(allBytes, huffDecoded);
    }

    @Test
    public void testEncodingDecodingWithAllBytesOnceReversed() {
        byte[] huffDecoded = huffman.decompress(huffman.compress(allBytesReverse));
        assertArrayEquals(allBytesReverse, huffDecoded);
    }

    @Test
    public void testEncodingDecodingWithRandomBytes() {
        byte[] huffDecoded = huffman.decompress(huffman.compress(fileBytesMock));
        assertArrayEquals(fileBytesMock, huffDecoded);
    }

    @Test
    public void testEncodingDecodingWithRandomBytesReversed() {
        byte[] huffDecoded = huffman.decompress(huffman.compress(fileBytesMockReverse));
        assertArrayEquals(fileBytesMockReverse, huffDecoded);
    }

    @Test
    public void testEncodingDecodingWithAllBytesRandomNormallyDistributed() {
        byte[] huffDecoded = huffman.decompress(huffman.compress(fileBytesMockSkewed));
        assertArrayEquals(fileBytesMockSkewed, huffDecoded);
    }

    @Test
    public void testEncodingDecodingWithAllBytesRandomNormallyDistributedReversed() {
        byte[] huffDecoded = huffman.decompress(huffman.compress(fileBytesMockSkewedReverse));
        assertArrayEquals(fileBytesMockSkewedReverse, huffDecoded);
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

    /**
     * Helper method to populate the fileBytesMockSkewed arrays. Populates the arrays with bytes
     * so that the different bytes have a normal distribution.
     * @param fileSize The size of the Mocked file in bytes.
     */
    private static void populateSkewedFileBytes(int fileSize) {
        int stdDeviation = Constants.BYTE_SIZE/(2*3); // ~70% of byte vals should be in the range [-42,42]
        // ~95% in the range [-84,84]
        // and ~99% in the range [-126,126]

        fileBytesMockSkewed = new byte[fileSize];
        fileBytesMockSkewedReverse = new byte[fileSize];

        for (int i = 0; i < fileSize; i++) {
            int byteValue;

            do {
                byteValue = (int) Math.round(random.nextGaussian() * stdDeviation);
            } while (byteValue < -128 || byteValue > 127);

            fileBytesMockSkewed[i] = (byte) byteValue;
            fileBytesMockSkewedReverse[fileSize - 1 - i] = (byte) byteValue;
        }

        /* Print distribution of the bytes frequencies, for debugging
        int maxFreq = 0;
        TreeMap<Byte, Integer> jakauma = new TreeMap<>();
        for (int i = 0; i < fileSize; i++) {
            int newFreq = jakauma.getOrDefault(fileBytesMockSkewed[i], 0) + 1;
            jakauma.put(fileBytesMockSkewed[i], newFreq);
            maxFreq = newFreq > maxFreq ? newFreq : maxFreq;
        }
        int divisor = 1;
        while (maxFreq/divisor > 80) {
            divisor += 1;
        }
        for (Map.Entry<Byte, Integer> entry : jakauma.entrySet()) {
            //System.out.println(entry.getKey() + ": " + entry.getValue());
            int barLength = entry.getValue() / divisor;
            String bar = "#";
            for (int i = 0; i < barLength; i++) {
                bar += "#";
            }
            System.out.printf("%4d: %s%n", entry.getKey(), bar);
        }
        */
    }
}
