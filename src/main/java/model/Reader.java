package model;
import java.awt.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Reader {
	
	protected static String[] columns;
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
	    	parseLine(recordList, line);
	    
	    bufferedReader.close();
		
		return recordList;
	}
	
	/**
	 * Parse one line of the file and add the result to the recordList
	 * @param recordList
	 * @param line
	 */
	protected void parseLine(RecordList recordList, String line)
	{
    	if(line.contains(delimiter))
	    	recordList.add(this.getRecord(line));	
    	else
    		addMetaData(recordList, line);
	}
	
	/**
	 * Add meta data from the current line to the recordList
	 * @param recordList
	 * @param line
	 */
	protected void addMetaData(RecordList recordList, String line)
	{
		String metaData = (String) recordList.getProperty("metadata");
		if(metaData != null)
			metaData += "\n" + line;
		else
			metaData = line;
		recordList.setProperty("metadata", metaData);
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
	
	/**
	 * Convert record list to string (structured in columns)
	 * @param RecordList
	 * @return delimiter
	 */
	public static String toString(RecordList list, String delimiter) throws IOException {

		StringBuilder out = new StringBuilder();
		
		for(int i=0; i < columns.length; i++) {
	          out.append(columns[i] + delimiter );
	      }
		
		out.setLength(out.length() - 1);
		out.append("\n ");
		
		for(int i=0; i < list.size(); i++) {
			
			Record rec = list.get(i);
			
			for(int j=0; j < columns.length; j++) {
				String key = columns[j];
			    Object value = rec.get(key);
			    out.append(value + delimiter );
			}
			
			out.setLength(out.length() - 1);
			out.append("\n ");
		}
		
		String output = out.toString();
		return output;
	}

}