package comppa;

import comppa.domain.Bitarray;
import comppa.io.Filehandler;
import comppa.logic.Huffman;

import java.util.ArrayList;
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

        //String filename = "testi1.txt";
        //String filename = "testi_100.txt";
        String filename = "simpletest.txt";

        byte[] fileBytes = Filehandler.readFileAsBytes(filename);

        String huffEncoded = huff.compress(fileBytes);
        Bitarray huffEncodedBArr = huff.compressBArr(fileBytes);

        byte[] decodedBytes = huff.decompress(huffEncoded);
        byte[] decodedBytesBArr = huff.decompressBitArr(huffEncodedBArr);

        System.out.println();
        System.out.println("[INFO] Read1 " + fileBytes.length + " bytes.");
        System.out.println("RESULT");
        System.out.println("------");
        System.out.println("ORIGINAL:        " + Arrays.toString(fileBytes));
        System.out.println("ENCODED/DECODED: " + Arrays.toString(decodedBytes));
        System.out.println("ENC/DEC BARR   : " + Arrays.toString(decodedBytesBArr));
        System.out.println();
        System.out.println("------");
        System.out.println("ARRAYS EQUALS: " + Arrays.equals(fileBytes, decodedBytes));
        System.out.println("ORG/BARR EQLS: " + Arrays.equals(fileBytes, decodedBytesBArr));
        System.out.println("ORIGINAL SIZE  : " + fileBytes.length);
        System.out.println("COMPDECOMP SIZE: " + decodedBytes.length);
        System.out.println("ENCDECBARR SIZE: " + decodedBytesBArr.length);

        //for (int i = 0; i < fileBytes.length; i++) {
        //    System.out.print((char) fileBytes[i]);
        //}

        //Filehandler.writeFileFromBytes("output_100oikea.txt", fileBytes);
    }

}
