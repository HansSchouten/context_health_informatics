package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class is used to write the data back to the file.
 * @author Elvan
 *
 */
public final class Writer {

    /**
     * Constructs an empty writer.
     */
    private Writer() {
    }

    /**
     * Writes a file to a given location with a string as content.
     * @param file The file to be written
     * @param text The content of the file
     * @throws IOException - If close goes wrong.
     */
    public static void writeFile(File file, String text) throws IOException {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
            fileWriter.write(text);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileWriter != null) {
                fileWriter.close();
            }
        }
    }
}
