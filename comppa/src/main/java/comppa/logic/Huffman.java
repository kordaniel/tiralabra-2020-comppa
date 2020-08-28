package comppa.logic;

import comppa.domain.Bitarray;
import comppa.domain.ByteBuffer;
import comppa.domain.Constants;
import comppa.domain.HuffmanNode;

import java.util.*;


/**
 *
 * @author danielko
 */
public class Huffman {

    // 24 non set bits and 8 set
    private static final int byteToUnsignedMask = 0xFF;

    private HuffmanNode rootNode;
    private HuffmanNode rootNode2;

    public Huffman() {
        this.rootNode = null;
        this.rootNode2 = null;
    }

    public Bitarray compress(byte[] input) {
        int[] frequencies = calculateBytesFrequencies(input);
        this.rootNode = buildTrie(frequencies);

        //System.out.println("-------------");

        Bitarray huffEncodedTrie = new Bitarray();
        huffmanEncodeTrie(rootNode, huffEncodedTrie);

        this.rootNode2 = huffmanDecodeTrie(huffEncodedTrie, 0);


        //printTrie(this.rootNode);
        //System.out.println();
        //System.out.println("===========");
        //System.out.println();
        //printTrie(this.rootNode2);
        //for (int i = 0; i < 5; i++) {
        //    System.out.println();
        //}
        System.out.println("-------------");


        return null;
        /*
        Bitarray[] huffmanCodes = new Bitarray[Constants.BYTE_SIZE];
        Bitarray huffmanCode = new Bitarray();

        int depth = -1;
        buildHuffmanCode(depth, huffmanCode, huffmanCodes, rootNode);
        Bitarray huffEncodedBits = huffmanEncode(input, huffmanCodes);

        return huffEncodedBits;
         */
    }

    private void printTrie(HuffmanNode node) {
        if (node.isLeaf()) {
            System.out.println(node);
        } else {
            printTrie(node.getLeft());
            printTrie(node.getRight());
        }
    }

    public byte[] decompress(Bitarray huffEncoded) {
        ArrayList<Byte> bytes = new ArrayList<>();

        int indx = -1;
        while (indx < huffEncoded.getMostSignificantBit() - 1) {
            indx = huffmanDecode(rootNode, indx, huffEncoded, bytes);
        }

        byte[] decodedBytes = new byte[bytes.size()];
        for (int i = 0; i < bytes.size(); i++) {
            decodedBytes[i] = bytes.get(i);
        }

        return decodedBytes;
    }

    /**
     * Getter that returns the huffmancodeTrie
     * @return root node for the Huffmantrie
     */
    public HuffmanNode getRootNode() {
        return this.rootNode;
    }

    public HuffmanNode getRootNode2() {
        return this.rootNode2;
    }

    private int huffmanDecode(HuffmanNode node,  int indx, Bitarray huffEncoded, List<Byte> decodedBytes) {
        if (node == null) {
            return indx;
        }

        if (node.isLeaf()) {
            decodedBytes.add(node.getNodeByte());
            return indx;
        }

        indx++;

        // Travel left
        if (!huffEncoded.getBit(indx)) {
            indx = huffmanDecode(node.getLeft(), indx, huffEncoded, decodedBytes);
        } else { // Travel right
            indx = huffmanDecode(node.getRight(), indx, huffEncoded, decodedBytes);
        }

        return indx;
    }

    private Bitarray huffmanEncode(byte[] bytes, Bitarray[] huffmanCodes) {
        Bitarray huffEncoded = new Bitarray();

        for (int i = 0; i < bytes.length; i++) {
            huffEncoded.appendBits(huffmanCodes[byteToUnsignedInt(bytes[i])]);
        }

        return huffEncoded;
    }

    private void huffmanEncodeTrie(HuffmanNode node, Bitarray encodedTrie) {
        if (node.isLeaf()) {
            encodedTrie.appendBit(true);
            encodedTrie.appendByteBits(node.getNodeByte());
        } else {
            encodedTrie.appendBit(false);
            huffmanEncodeTrie(node.getLeft(), encodedTrie);
            huffmanEncodeTrie(node.getRight(), encodedTrie);
        }
    }

    private int maxIndex = 0;
    private HuffmanNode huffmanDecodeTrie(Bitarray encodedTrie, int index) {
        if (encodedTrie.getBit(index)) {
            // leaf node
            ByteBuffer byteBuffer = new ByteBuffer();

            for (int i = 1; i <= Constants.BYTE_WIDTH; i++) {
                byteBuffer.append(encodedTrie.getBit(index + i));
            }
            maxIndex = index + Constants.BYTE_WIDTH;
            byte currByte = byteBuffer.getCurrentByte();
            return new HuffmanNode(currByte);
        }

        // internal node
        HuffmanNode leftChild  = huffmanDecodeTrie(encodedTrie, index + 1);
        HuffmanNode rightChild = rightChild = huffmanDecodeTrie(encodedTrie, maxIndex + 1);

        return new HuffmanNode(0, leftChild, rightChild);
    }

    private void buildHuffmanCode(int depth, Bitarray huffmanCode,
                                  Bitarray[] huffmanCodes, HuffmanNode node) {
        if (node.isLeaf()) {
            int index = byteToUnsignedInt(node.getNodeByte());
            if (huffmanCodes[index] != null) {
                throw new RuntimeException("Found a bitset that should not exist!!");
            }

            Bitarray nodeHuffCode = null;
            try {
                nodeHuffCode = (Bitarray) huffmanCode.clone();
            } catch (CloneNotSupportedException e) {
                System.out.println(e.getMessage());
            }

            nodeHuffCode.setMostSignificantBit(depth);
            huffmanCodes[index] = nodeHuffCode;

            return;
        }

        depth++;

        // Travel left
        huffmanCode.unsetBit(depth); // This should be an unnecessary operation since we set the most
                                     // significant bit later in the recursion!  ..?
        buildHuffmanCode(depth, huffmanCode, huffmanCodes, node.getLeft());

        // Travel right
        huffmanCode.setBit(depth);
        buildHuffmanCode(depth, huffmanCode, huffmanCodes, node.getRight());
    }

    private HuffmanNode buildTrie(int[] bytesFrequencies) {
        PriorityQueue<HuffmanNode> nodes = new PriorityQueue<>();

        for (short i = 0; i < Constants.BYTE_SIZE; i++) {
            if (bytesFrequencies[i] == 0) {
                continue;
            }
            nodes.add(new HuffmanNode(unsignedIntToByte(i), bytesFrequencies[i]));
        }

        while (nodes.size() > 1) {
            HuffmanNode left = nodes.poll();
            HuffmanNode right = nodes.poll();
            nodes.add(new HuffmanNode(left.getFrequency() + right.getFrequency(), left, right));
        }

        return nodes.poll();
    }

    private int[] calculateBytesFrequencies(byte[] bytes) {
        int[] frequencies = new int[Constants.BYTE_SIZE];

        for (int i = 0; i < bytes.length; i++) {
            frequencies[byteToUnsignedInt(bytes[i])]++;
        }

        return frequencies;
    }

    private int byteToUnsignedInt(byte b) {
        return b & byteToUnsignedMask;
    }

    private byte unsignedIntToByte(int i) {
        if (i >= 0 && i < Constants.BYTE_SIZE) {
            return (byte) i;
        }

        throw new RuntimeException("Attempting to convert a too wide integer into a byte!");
    }
}
