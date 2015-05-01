package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Object;

public class Writer {
	protected static String delimiter;
	private String[] columns;
	
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
	public static void writeData(RecordList records, String fileName) {
        if (records.size() != 0) {

        try {
            File out = new File(fileName);
            FileWriter fw = new FileWriter(fileName, false);
            fw.write(Reader.toString(records, delimiter));
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
