package comppa;

import comppa.domain.Bitarray;
import comppa.domain.Constants;
import comppa.domain.HuffmanNode;
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

        String filenameOutCompressed = filename + "_encoded";
        String filenameOutDecompressed = filename + "_encoded_and_decoded";

        byte[] fileBytes = Filehandler.readFileAsBytes(filename);
        byte[] huffEncoded = huff.compress(fileBytes);

        Filehandler.writeFileFromBytes(filenameOutCompressed, huffEncoded);

        byte[] readEncodedBytes = Filehandler.readFileAsBytes(filenameOutCompressed);
        byte[] decompressedBytes = huff.decompress(readEncodedBytes);

        Filehandler.writeFileFromBytes(filenameOutDecompressed, decompressedBytes);


        System.out.println();
        System.out.println("[INFO] Read1 " + fileBytes.length + " bytes.");
        System.out.println("RESULT");
        System.out.println("------");
        System.out.println("ORIGINAL:        " + Arrays.toString(fileBytes));
        System.out.println("ENCODED/DECODED: " + Arrays.toString(decompressedBytes));
        System.out.println("ENCODED:         " + Arrays.toString(huffEncoded));
        System.out.println();
        System.out.println("------");
        System.out.println("ARRAYS EQUALS: " + Arrays.equals(fileBytes, decompressedBytes));
        System.out.println("ORIGINAL SIZE  : " + fileBytes.length + " bytes");
        System.out.println("COMPDECOMP SIZE: " + decompressedBytes.length + " bytes");
        System.out.println("COMPRESSED SIZE: ~" + huffEncoded.length + " bytes");

        //double compressedSize = Math.ceil((huffEncoded.getMostSignificantBit() + 1) / 8); // In bytes
        //double compressionRatio = 100 * (1 - (compressedSize / fileBytes.length));
        //System.out.printf("COMPRESSION RATIO: ~%5.2f%%%n", compressionRatio);

        System.out.println("");
        System.out.println("file in:  " + filename);
        System.out.println("file out: " + filenameOutDecompressed);
        System.out.println("THESE FILES SHOULD BE IDENTICAL");
    }

}
