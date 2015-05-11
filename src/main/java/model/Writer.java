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
	 * Write the recordList to file 
	 * @param outfile een outfile met extensie .txt
	 * @param records de recordlist die moet worden weggeschreven
	 * @param extension extendsion of the file
	 * @throws Exception 
	 */
	public void writeData(RecordList records, String fileName, String extension) {
        if (records.size() != 0) {

        try {
        	if (!extension.startsWith(".")) {
        		extension = "." + extension;
        	}
        	if (!fileName.endsWith(extension)) {
        	    fileName += extension;
        	}
            FileWriter fw = new FileWriter(fileName, false);
            fw.write(records.toString(delimiter));
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
