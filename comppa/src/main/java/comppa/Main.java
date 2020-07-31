package comppa;

import comppa.io.Filehandler;
import comppa.logic.Huffman;

import java.util.*;

/**
 *
 * @author danielko
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Huffman huff = new Huffman();

        String filename = "testi1.txt";
        //String filename = "testi_100.txt";
        //String filename = "simpletest.txt";

        byte[] fileBytes = Filehandler.readFileAsBytes(filename);
        String huffEncoded = huff.compress(fileBytes);
        byte[] decodedBytes = huff.decompress(huffEncoded);

        System.out.println();
        System.out.println("[INFO] Read1 " + fileBytes.length + " bytes.");
        System.out.println("RESULT");
        System.out.println("------");
        System.out.println("ORIGINAL:        " + Arrays.toString(fileBytes));
        System.out.println("ENCODED/DECODED: " + Arrays.toString(decodedBytes));
        System.out.println("ARRAYS EQUALS: " + Arrays.equals(fileBytes, decodedBytes));

        //for (int i = 0; i < fileBytes.length; i++) {
        //    System.out.print((char) fileBytes[i]);
        //}

        //Filehandler.writeFileFromBytes("output_100oikea.txt", fileBytes);
    }


    public static Map<Character, Integer> calculateFrequencies(char[] charArr) {
        HashMap<Character, Integer> freqs = new HashMap();

        for (int i = 0; i < charArr.length; i++) {
            char s = charArr[i];
            freqs.put(s, freqs.getOrDefault(s, 0) + 1);
        }

        System.out.println("[INFO] Frequencies of the symbols in the string are as following:");
        freqs.forEach((k, v) -> {
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
