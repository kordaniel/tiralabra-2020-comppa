package comppa;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;

/**
 *
 * @author danielko
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int n = 10000; // Length of the string

        Random random = new Random();
        PriorityQueue<Node> minQue = new PriorityQueue();

        // String as chararray
        char[] charArray = generateTestString(n, random);
        // Map holding char/freq key-val pairs
        Map<Character, Integer> freqs = calculateFrequencies(charArray);


        for (Map.Entry<Character, Integer> kv : freqs.entrySet()) {
            Node node = new Node();
            node.c = kv.getKey();
            node.frequency = kv.getValue();
            node.left = null;
            node.right = null;
            minQue.add(node);
        }


        Node root = null;
        while (minQue.size() > 1) {
            Node x = minQue.peek();
            minQue.poll();

            Node y = minQue.peek();
            minQue.poll();

            Node f = new Node();
            f.frequency = x.frequency + y.frequency;

            f.c = '-';

            f.left = x;
            f.right = y;

            root = f;

            minQue.add(f);
        }

        System.out.println("");
        Huffman.printCode(root, "");
    }


    public static Map<Character, Integer> calculateFrequencies(char[] charArr) {
        HashMap<Character, Integer> freqs = new HashMap();

        for (int i = 0; i < charArr.length; i++) {
            char s = charArr[i];
            freqs.put(s, freqs.getOrDefault(s, 0)+1);
        }

        System.out.println("[INFO] Frequencies of the symbols in the string are as following:");
        freqs.forEach((k,v) -> {
            System.out.println(k + ": " + v);
        });
        return freqs;
    }
    
    public static char[] generateTestString(int length, Random random) {
        int alphabetLen = 26; // Length of the alphabet
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int randomNum = random.nextInt(alphabetLen + 1); // add 1 to the alphabet for spaces

            if (randomNum == alphabetLen) {
                sb.append(" ");
                continue;
            }

            int character = (random.nextInt(100) < 50)
                            ? 97
                            : 65; // Append upper/lowercase letters with 50% probability
            sb.append((char) (character + randomNum));
        }

        System.out.println("[INFO] Generated a test string with length: " + sb.length());

        char[] charArray = new char[sb.length()];
        sb.getChars(0, sb.length(), charArray, 0);
        return charArray;
    }
}
