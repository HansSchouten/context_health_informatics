package model;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

public class Reader {
	
	protected Column[] columns;
	protected String delimiter;
	
	/**
	 * Reader constructor
	 * @param columns		the columns from left to right
	 * @param delimiter		the delimiter used to distinguish the columns
	 */
	public Reader(Column[] columns, String delimiter)
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
		RecordList recordList = new RecordList(columns);
		
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
	    	recordList.add(this.createRecord(line));	
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
	protected Record createRecord(String line) 
	{
		//Need to be changed
		Record record = new Record(DateUtils.t1900toLocalDateTime("42000"));
		
		String[] parts = line.split(delimiter);
		for(int i=0; i<columns.length; i++)
			switch (columns[i].characteristic) {
			case COMMENT:
				record.addCommentToRecord(parts[i]);
				break;
			default:
				record.put(columns[i].name, new RecordFieldString(parts[i]));
			}
		
		return record;
	}
	
}