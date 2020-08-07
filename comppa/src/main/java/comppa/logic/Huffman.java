package comppa.logic;

import comppa.domain.Bitarray;
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

    public String compress(byte[] input) {
        int[] frequencies = calculateBytesFrequencies(input);
        this.rootNode = buildTrie(frequencies);

        String[] huffmanCode = new String[byteSize];
        Bitarray[] huffmanCodes = new Bitarray[byteSize];
        Bitarray huffCode = new Bitarray();

        int depth = -1;
        buildHuffmanCode(depth, huffCode, huffmanCode, huffmanCodes, rootNode, "");

        String huffEncoded = huffmanEncode(input, huffmanCode);
        Bitarray huffEncodedBits = huffmanEncodeBitArray(input, huffmanCodes);

        System.out.println();
        System.out.println("Huffman encoded the input:");
        System.out.println("---------------");
        System.out.println("LENGTH of original bytesArr     : " + input.length);
        System.out.println("LENGTH of bits in original      : " + (input.length * 8));
        System.out.println("LENGTH of bits in huffmanEncoded: " + huffEncoded.length());
        System.out.println("LENGTH of bits in huffmanEncodedBArr: " + huffEncodedBits.getMostSignificantBit());
        System.out.println("---------------");
        int eriBittienMaara = 0;
        for (int i = 0; i < byteSize; i++) {
            if (huffmanCode[i] == null || huffmanCode[i].isEmpty()) {
                if (huffmanCodes[i] != null) {
                    System.out.println();
                    System.out.println("ERRRRRRRRRRRRRROR");
                    System.out.println(huffmanCode[i]);
                    System.out.println(huffmanCodes[i]);
                }
                continue;
            }
            eriBittienMaara++;
            System.out.println();
            System.out.println(huffmanCode[i]);
            System.out.println(huffmanCodes[i]);
            System.out.print(huffmanCode[i].length() == huffmanCodes[i].getMostSignificantBit()+1);
            System.out.println(" " + huffmanCode[i].length() + " = " + (huffmanCodes[i].getMostSignificantBit()+1));
            if (huffmanCode[i].length() != huffmanCodes[i].getMostSignificantBit()+1) {
                System.out.println("ERRRRRRRRRRRRRRROR");
            } else {
                for (int j = 0; j <= huffmanCodes[i].getMostSignificantBit(); j++) {
                    boolean strBit = huffmanCode[i].charAt(j) == '1' ? true : false;
                    boolean bArrBit = huffmanCodes[i].getBit(j);
                    if (strBit != bArrBit) {
                        System.out.println("EEEEEEEEEEEEERRRRRRRRRRRRRRRRRRRRRRRRRRRRRRROOOOOOOOOOOOOOOOOOOORRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR");
                    }
                }
            }
        }
        System.out.println("IN TOTAL " + eriBittienMaara + " DIFFERENT BYTES");
        System.out.println("---------------");
        System.out.println(("HUFFCODE " + huffCode));

        System.out.println("");
        System.out.println("");
        System.out.println("------------------");
        System.out.println(huffEncodedBits);
        System.out.println(huffEncoded);
        String huffffffffff = "";
        for (int i = 0; i <=huffEncodedBits.getMostSignificantBit(); i++) {
            huffffffffff += huffEncodedBits.getBit(i) ? '1' : '0';
        }
        System.out.println(huffffffffff);
        System.out.println("HUFFENCODED STRINGS MATCH: " + huffEncoded.matches(huffffffffff));

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

    private Bitarray huffmanEncodeBitArray(byte[] bytes, Bitarray[] huffmanCode) {
        Bitarray huffEncoded = new Bitarray();

        for (int i = 0; i < bytes.length; i++) {
            huffEncoded.appendBits(huffmanCode[byteToUnsignedInt(bytes[i])]);
        }

        return huffEncoded;
    }

    private void buildHuffmanCode(int depth, Bitarray huffCode,
                                  String[] str, Bitarray[] huffCodes,
                                  HuffmanNode node,  String s) {
        if (node.isLeaf()) {
            int index = byteToUnsignedInt(node.getNodeByte());
            if (huffCodes[index] != null) {
                throw new RuntimeException("Found a bitset that should not exist!!");
            }

            str[index] = s;
            Bitarray nodeHuffCode = null;
            try {
                nodeHuffCode = (Bitarray) huffCode.clone();
            } catch (CloneNotSupportedException e) {
                System.out.println(e.getMessage());
            }
            nodeHuffCode.setMostSignificantBit(depth);
            huffCodes[index] = nodeHuffCode;

            return;
        }

        depth++;

        huffCode.unsetBit(depth); // This should be an unecessary operation since we set the most significant bit later
                                  // in the recursion!  ..?
        buildHuffmanCode(depth, huffCode, str, huffCodes, node.getLeft(), s + '0');

        huffCode.setBit(depth);
        buildHuffmanCode(depth, huffCode, str, huffCodes, node.getRight(), s + '1');
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
