package model;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Reader {
	
	protected String[] columns;
	protected String delimiter;
	
	/**
	 * Reader constructor
	 * @param columns		the columns from left to right
	 * @param delimiter		the delimiter used to distinguish the columns
	 */
	public Reader(String[] columns, String delimiter) 
	{
		this.columns = columns;
		this.delimiter = delimiter;
	}
	
	/**
	 * Read the given file and return a RecordList representing the file
	 * @param filePath
	 * @return
	 * @throws IOException 
	 */
	public RecordList read(String filePath) throws IOException 
	{
		RecordList recordList = new RecordList();
		
		BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
	    for(String line; (line = bufferedReader.readLine()) != null; )
	    	recordList.add(this.getRecord(line));
	    
	    bufferedReader.close();
		
		return recordList;
	}
	
	/**
	 * Convert a single line into a Record
	 * @param line
	 * @return
	 */
	protected Record getRecord(String line) 
	{
		Record record = new Record();
		
		String[] parts = line.split(delimiter);
		for(int i=0; i<columns.length; i++)
			record.put(columns[i], parts[i]);
		
		return record;
	}
	
}