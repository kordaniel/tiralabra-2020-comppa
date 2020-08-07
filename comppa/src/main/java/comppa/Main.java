package comppa;

import comppa.domain.Bitarray;
import comppa.io.Filehandler;
import comppa.logic.Huffman;

import java.util.Arrays;

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

        String filename = "testi100.txt";

        if (args.length != 1) {
            System.out.println("Pass name of file to encode + decode as the only argument");
            System.out.println("For example using gradle, run: './gradlew run --args=\"<filename>\"'");
            System.out.println();
            System.out.println("Defaulting back to the file \"" + filename + "\"");
        } else {
            filename = args[0];
            System.out.println("Using file \"" + filename + "\"");
        }

        String filenameOut = filename + "_encoded_and_decoded";

        byte[] fileBytes = Filehandler.readFileAsBytes(filename);

        Bitarray huffEncoded = huff.compress(fileBytes);

        byte[] decodedBytes = huff.decompress(huffEncoded);

        System.out.println();
        System.out.println("[INFO] Read1 " + fileBytes.length + " bytes.");
        System.out.println("RESULT");
        System.out.println("------");
        System.out.println("ORIGINAL:        " + Arrays.toString(fileBytes));
        System.out.println("ENCODED/DECODED: " + Arrays.toString(decodedBytes));
        System.out.println("ENCODED:         " + huffEncoded); // Bits are printed in reverse
        System.out.println();
        System.out.println("------");
        System.out.println("ARRAYS EQUALS: " + Arrays.equals(fileBytes, decodedBytes));
        System.out.println("ORIGINAL SIZE  : " + fileBytes.length + " bytes");
        System.out.println("COMPDECOMP SIZE: " + decodedBytes.length + " bytes");
        System.out.println("COMPRESSED SIZE: ~" + ((huffEncoded.getMostSignificantBit() + 1) / 8.0) + " bytes");

        double compressedSize = Math.ceil((huffEncoded.getMostSignificantBit() + 1) / 8); // In bytes
        double compressionRatio = 100 * (1 - (compressedSize / fileBytes.length));
        System.out.printf("COMPRESSION RATIO: ~%5.2f%%%n", compressionRatio);

        //for (int i = 0; i < fileBytes.length; i++) {
        //    System.out.print((char) fileBytes[i]);
        //}

        Filehandler.writeFileFromBytes(filenameOut, decodedBytes);
        System.out.println("");
        System.out.println("file in:  " + filename);
        System.out.println("file out: " + filenameOut);
        System.out.println("THESE FILES SHOULD BE IDENTICAL");
    }

}
