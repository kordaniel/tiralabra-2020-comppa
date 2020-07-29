package comppa.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author danielko
 */
public class Filehandler {

    private String filename;

    public Filehandler() {
        this.filename = "testi1.txt";
    }

    public byte[] readFile() {
        File file = new File(this.filename);
        byte[] bytesArr = new byte[(int) file.length()];

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            fis.read(bytesArr);
            fis.close();
        } catch (IOException err) {
            System.out.println("[ERROR] While reading file: " + err.getMessage());
        }

        return bytesArr;
    }
}
