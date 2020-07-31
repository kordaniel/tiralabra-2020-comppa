package comppa.logic;

import comppa.domain.HuffmanNode;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Huffman {

    // 24 non set bits and 8 set
    private static final int byteToUnsignedMask = 0xFF;
    // Signed 16-bit two's complement integer
    private static final short byteSize = 1 << 8;

    private HuffmanNode rootNode = null;

    public Huffman() {
        //
    }

    public String compress(byte[] input) {
        int[] frequencies = calculateBytesFrequencies(input);
        this.rootNode = buildTrie(frequencies);

        String[] huffmanCode = new String[byteSize];
        buildHuffmanCode(huffmanCode, rootNode, "");
        String huffEncoded = huffmanEncode(input, huffmanCode);

        System.out.println();
        System.out.println("Huffman encoded the input:");
        System.out.println("---------------");
        System.out.println("LENGTH of original bytesArr     : " + input.length);
        System.out.println("LENGTH of bits in original      : " + (input.length * 8));
        System.out.println("LENGTH of bits in huffmanEncoded: " + huffEncoded.length());
        System.out.println("---------------");
        return huffEncoded;
    }

    public byte[] decompress(String huffEncoded) {
        ArrayList<Byte> bytes = new ArrayList<>();

        int indx = -1;
        while (indx < huffEncoded.length() - 2) {
            indx = huffmanDecode(rootNode, indx, huffEncoded, bytes);
        }

        byte[] decodedBytes = new byte[bytes.size()];
        for (int i = 0; i < bytes.size(); i++) {
            decodedBytes[i] = bytes.get(i);
        }

        return decodedBytes;
    }

    private int huffmanDecode(HuffmanNode node, int indx, String huffEncoded, List<Byte> decodedBytes) {
        if (node == null) {
            return indx;
        }

        if (node.isLeaf()) {
            decodedBytes.add(node.getNodeByte());
            return indx;
        }

        indx++;

        if (huffEncoded.charAt(indx) == '0') {
            indx = huffmanDecode(node.getLeft(), indx, huffEncoded, decodedBytes);
        } else if (huffEncoded.charAt(indx) == '1') {
            indx = huffmanDecode(node.getRight(), indx, huffEncoded, decodedBytes);
        } else {
            throw new IllegalArgumentException("Invalid input for huffmanDecoding");
        }

        return indx;
    }

    private String huffmanEncode(byte[] bytes, String[] huffmanCode) {
        StringBuilder huffEncoded = new StringBuilder();

        for (int i = 0; i < bytes.length; i++) {
            String code = huffmanCode[byteToUnsignedInt(bytes[i])];
            huffEncoded.append(code);
        }

        return huffEncoded.toString();
    }

    private void buildHuffmanCode(String[] str, HuffmanNode node,  String s) {
        if (node.isLeaf()) {
            str[byteToUnsignedInt(node.getNodeByte())] = s;
            return;
        }

        buildHuffmanCode(str, node.getLeft(), s + '0');
        buildHuffmanCode(str, node.getRight(), s + '1');
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
        if (i < byteSize) {
            return (byte) i;
        }

        throw new RuntimeException("Attempting to convert a too wide integer into a byte!");
    }
}
