package model;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class is used write the data back to the file.
 * @author Matthijs
 *
 */
public class Writer {

    /**
     * This variable stores the delimiter of the file.
     */
	protected String delimiter;

    /**
     * Create a new writer object.
     * @param del       - The delimiter of the file.
     */
	public Writer(String del) {
		delimiter = del;
	}

	/**
	 * This method writes the data to the file.
	 * @param records      - Records to be written.
	 * @param fileName     - File to write to.
	 * @param extension    - Extension of the file.
	 * @throws IOException - When close goes wrong.
	 */
	public void writeData(RecordList records, String fileName, String extension) throws IOException {
        if (records.size() != 0) {
            FileWriter fw = null;
            try {
            	if (!extension.startsWith(".")) {
            		extension = "." + extension;
            	}
            	if (!fileName.endsWith(extension)) {
            	    fileName += extension;
            	}
                fw = new FileWriter(fileName, false);
                fw.write(records.toString(delimiter));
                fw.close();
            } catch (FileNotFoundException e) {
                System.out.println("File not found");
            } catch (IOException e) {
            	System.out.println("IO error occurred");
    			e.printStackTrace();
    		} finally {
    		    if (fw != null)
    		        fw.close();
    		}
        }
    }
}
