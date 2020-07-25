package comppa;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 *
 * @author danielko
 */
public class Huffman {

    private Map<Character, String>     huffmanCode;
    private Map<Character, Integer>    frequencies;
    private PriorityQueue<HuffmanNode> nodes;

    public Huffman() {
        this.huffmanCode = new HashMap<>();
        this.frequencies  = new HashMap<>();
        this.nodes        = new PriorityQueue<>();
    }

    public void encode(HuffmanNode node, String str) {
        if (node == null) {
            return;
        }

        if (node.left == null && node.right == null) {
            this.huffmanCode.put(node.getCharacter(), str);
        }

        encode(node.getLeft(),  str + '0');
        encode(node.getRight(), str + '1');
    }

    public int decode(HuffmanNode node, int indx, StringBuilder sb) {
        if (node == null) {
            return indx;
        }

        if (node.left == null && node.right == null) {
            System.out.print(node.getCharacter());
            return indx;
        }

        indx++;

        if (sb.charAt(indx) == '0') {
            indx = decode(node.getLeft(), indx, sb);
        } else if (sb.charAt(indx) == '1') {
            indx = decode(node.getRight(), indx, sb);
        } else {
            throw new InternalError("Trying to traverse to an node while decoding that should not exist!");
        }

        return indx;
    }

    public void readStr(String str) {
        for (char c : str.toCharArray()) {
            this.frequencies.put(c, frequencies.getOrDefault(c, 0) + 1);
        }

        for (Map.Entry<Character, Integer> entry : this.frequencies.entrySet()) {
            this.nodes.add(
                    new HuffmanNode(entry.getKey(),
                                    entry.getValue())
            );
        }

        while (this.nodes.size() > 1) {
            HuffmanNode left  = this.nodes.poll();
            HuffmanNode right = this.nodes.poll();
            this.nodes.add(
                    new HuffmanNode(
                            '-',
                            left.getFrequency() + right.getFrequency(),
                            left,
                            right)
            );
        }

        HuffmanNode root = this.nodes.peek();
        this.encode(root, "");

        StringBuilder sb = new StringBuilder();
        for (char c : str.toCharArray()) {
            sb.append(this.huffmanCode.get(c));
        }

        System.out.println("ORIGINAL String: " + str);
        System.out.println("HUFFMAN   Codes: " + this.huffmanCode);
        System.out.println("");
        System.out.println("ENCODED  String       : " + sb.toString());
        System.out.println("LENGTH of encoded str : " + sb.toString().length());
        System.out.println("\"Length\" of ORIG str: " + str.length()*16); // Java String size is 2 bytes, or 16 bits for every unicode character in the string
        System.out.println("DECODED  String: ");

        int indx = -1;
        while (indx < sb.length() - 2)  {
            indx = decode(root, indx, sb);
        }
    }

    public static void printCode(Node root, String s) {
        if (root.left == null && root.right == null) {
            if (Character.isLetter(root.c)) {
                while (s.length() < 15) {
                    s = "0"+s;
                }
                System.out.println(root.c + ": " + s);
            }
            return;
        }

        if (s.length() != 0 && s.length() % 4 == 0) {
            printCode(root.left,  s + " 0");
            printCode(root.right, s + " 1");
        } else {
            printCode(root.left,  s + "0");
            printCode(root.right, s + "1");
        }

    }
}
