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
    // Signed 16-bit two's complement integer
    private static final short byteSize = 1 << 8;

    private HuffmanNode rootNode;

    public Huffman() {
        this.rootNode = null;
    }

    public Bitarray compress(byte[] input) {
        int[] frequencies = calculateBytesFrequencies(input);
        this.rootNode = buildTrie(frequencies);

        System.out.println("-------------");
        Bitarray huffEncodedTrie = new Bitarray();
        huffmanEncodeTrie(rootNode, huffEncodedTrie);

        HuffmanNode rootNode2 = huffmanDecodeTrie(huffEncodedTrie, 0);


        printTrie(this.rootNode);
        System.out.println();
        System.out.println("===========");
        System.out.println();
        printTrie(rootNode2);
        for (int i = 0; i < 5; i++) {
            System.out.println();
        }
        System.out.println("-------------");
        Bitarray[] huffmanCodes = new Bitarray[byteSize];
        Bitarray huffmanCode = new Bitarray();

        int depth = -1;
        buildHuffmanCode(depth, huffmanCode, huffmanCodes, rootNode);
        Bitarray huffEncodedBits = huffmanEncode(input, huffmanCodes);

        return huffEncodedBits;
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
        if (!node.isLeaf()) {
            encodedTrie.appendBit(false);
            huffmanEncodeTrie(node.getLeft(), encodedTrie);
            huffmanEncodeTrie(node.getRight(), encodedTrie);
        } else {
            //leaf node
            encodedTrie.appendBit(true);
            encodedTrie.appendByteBits(node.getNodeByte());
        }
    }

    private HuffmanNode huffmanDecodeTrie(Bitarray encodedTrie, int index) {
        if (encodedTrie.getBit(index)) {
            // leaf node
            ByteBuffer byteBuffer = new ByteBuffer();

            for (int i = 1; i <= Constants.BYTE_WIDTH; i++) {
                byteBuffer.append(encodedTrie.getBit(index + i));
            }

            byte currByte = byteBuffer.getCurrentByte();
            System.out.println("DECODING TRIE, byte: " + currByte);

            return new HuffmanNode(currByte);
        }

        // internal node
        HuffmanNode leftChild = huffmanDecodeTrie(encodedTrie, index + 1);
        HuffmanNode rightChild = huffmanDecodeTrie(encodedTrie, index + 10);
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

        for (short i = 0; i < byteSize; i++) {
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
        int[] frequencies = new int[byteSize];

        for (int i = 0; i < bytes.length; i++) {
            frequencies[byteToUnsignedInt(bytes[i])]++;
        }

        return frequencies;
    }

    private int byteToUnsignedInt(byte b) {
        return b & byteToUnsignedMask;
    }

    private byte unsignedIntToByte(int i) {
        if (i >= 0 && i < byteSize) {
            return (byte) i;
        }

        throw new RuntimeException("Attempting to convert a too wide integer into a byte!");
    }
}
