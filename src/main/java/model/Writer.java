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
	
	/**
     * Makes a string of the column names
     * @param del       - The delimiter of the file.
     */
	public String columnNamesToString(Column[] columns) {
		StringBuilder out = new StringBuilder();
		
		for (int i = 0; i < columns.length; i++) {
	          out.append(columns[i].getName() + delimiter);
		} 
		
		out.setLength(out.length() - 1);
		out.append("\r\n "); 
		
		String output = out.toString();
		
		return output;
	}
	
	/**
	 * This method writes the data to the file.
	 * @param records      - Records to be written.
	 * @param fileName     - File to write to.
	 * @param extension    - Extension of the file.
	 * @param columns      - List of selected columns 
	 * @param colnames	   - If you want the column names included
	 */
	public void writeData(SequentialData data, String fileName, String extension, Column[] columns, Boolean colnames) {
        if (data.size() != 0) {
        	
        try {
        	if (!extension.startsWith(".")) {
        		extension = "." + extension;
        	}
        	if (!fileName.endsWith(extension)) {
        	    fileName += extension;
        	}
   
            FileWriter fw = new FileWriter(fileName, false);
            if(colnames) {
            fw.write(columnNamesToString(columns));	
            }
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
