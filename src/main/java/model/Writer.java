package model;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class Writer {
	protected String delimiter;
	protected String[] columns;
	
	/**
	 * Writer constructor
	 * @param file path		path of the output file
	 */
	public Writer(String[] columns, String delimiter)
	{
		this.columns = columns;
		this.delimiter = delimiter;
	}
	
	/**
	 * Write the recordList to file 
	 * @param outfile een outfile met extensie .txt
	 * @param records de recordlist die moet worden weggeschreven
	 * @throws Exception 
	 */
	public void writeData(RecordList records, String fileName, String extension) {
        if (records.size() != 0) {

	        try {
	        	if(!extension.startsWith(".")) {
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
