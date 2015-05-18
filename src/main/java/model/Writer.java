package model;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import model.SequentialData;

/**
 * This class is used to write the data back to the file.
 * @author Elvan
 *
 */
public class Writer {

    /**
     * This variable stores the delimiter of the file.
     */
	protected String delimiter;

	/**
     * This variable stores the columns of the sequential data object
     */
	protected Column[] columns;
	
    /**
     * Create a new writer object.
     * @param del       - The delimiter of the file.
     */
	public Writer(String del) {
		delimiter = del;
	}
	
	public int test() {
		return 5;
	}

	/**
	 * This method writes the data to the file.
	 * @param records      - Records to be written.
	 * @param fileName     - File to write to.
	 * @param extension    - Extension of the file.
	 */
	public void writeData(SequentialData data, String fileName, String extension, Column[] columns) {
        if (data.size() != 0) {
        	
        try {
        	if (!extension.startsWith(".")) {
        		extension = "." + extension;
        	}
        	if (!fileName.endsWith(extension)) {
        	    fileName += extension;
        	}
   
            FileWriter fw = new FileWriter(fileName, false);
            fw.write(data.toString(delimiter, columns));
            fw.close();
            } catch (FileNotFoundException e) {
                System.out.println("File not found");
            } catch (IOException e) {
            	System.out.println("IO error occurred");
    			e.printStackTrace();
    		}
        }
    }
}
