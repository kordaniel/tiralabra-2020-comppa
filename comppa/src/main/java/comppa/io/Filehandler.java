package comppa.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * A class holding static methods for manipulating files.
 *
 * @author danielko
 */
public class Filehandler {

    /**
     * Attempts to read all the bytes from the file passed as argument.
     * @param filename The name of the file to be read, not null or empty
     * @return Bytes from the read file, null if error reading
     */
    public static byte[] readFileAsBytes(String filename) {
        byte[] fileBytes = null;

        if (!filenameIsValid(filename)) {
            return fileBytes;
        }

        Path path = Paths.get(filename);

        try {
            fileBytes = Files.readAllBytes(path);
        } catch (IOException err) {
            System.out.println("[ERROR] While attempting to read file: " + err.getMessage());
        }

        return fileBytes;
    }

    /**
     * Writes the content of the bytes array passed as argument to disk in binary format.
     * Attempts to create a new file if the file does not exist.
     * If the file exists, then this function will attempt to overwrite it.
     *
     * @param filename The name of the file to be written, not null or empty
     * @param bytes Array of bytes to be written to the file, not null or empty
     */
    public static void writeFileFromBytes(String filename, byte[] bytes) {
        if (!filenameIsValid(filename)
                || bytes == null
                || bytes.length == 0) {
            return;
        }

        Path path = Paths.get(filename);

        try {
            Files.write(path, bytes, StandardOpenOption.CREATE);
        } catch (IOException err) {
            System.out.println("[ERROR] While attempting to write to file: " + err.getMessage());
        }
    }

    /**
     * Helper function that checks if the filename is a valid name for a file.
     * @param filename The name of the file
     * @return True, if the argument is an valid name for a file. False otherwise.
     */
    private static boolean filenameIsValid(String filename) {
        return filename != null && !filename.isBlank();
    }
}
