package model;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class represents a group of files
 * @author Hans Schouten
 *
 */
public class Group extends HashMap<String, RecordList> {
	
	private static final long serialVersionUID = -3381646667484508431L;
	
	protected String name;
	protected String delimiter;
	protected String[] columns;
	protected String primary;
	protected Reader reader;

	/**
	 * Construct a group of files
	 * @param name			the name of this group
	 * @param delimiter		the delimiter used to distinguish the columns
	 * @param columns		the columns from left to right
	 * @param primary		the property that represents the primary key
	 */
	public Group(String name, String delimiter, String[] columns, String primary) 
	{
		this.name = name;
		this.delimiter = delimiter;
		this.columns = columns;
		this.primary = primary;
		this.reader = new Reader(columns, delimiter);
	}
	
	/**
	 * Construct a basic group
	 * @param name
	 */
	public Group(String name) 
	{
		this.name = name;
	}
	
	/**
	 * Add a file to this group
	 * @param filePath
	 * @throws IOException 
	 */
	public void addFile(String filePath) throws IOException
	{
		put(filePath, reader.read(filePath));
	}
	
	/**
	 * Add a recordList to this group
	 * @param filePath
	 * @param recordList
	 */
	public void addRecordList(RecordList recordList)
	{
		put(recordList.getFilePath(), recordList);
	}

	public String getName() {
		return name;
	}

	public String getDelimiter() {
		return delimiter;
	}

	public String[] getColumns() {
		return columns;
	}

	public String getPrimary() {
		return primary;
	}
	
	/**
	 * Group all records by the specified primary key
	 * @return
	 */
	public HashMap<String, RecordList> groupByPrimary() {
		if(primary == null)
			return groupByFile();
		else
			return groupByColumn();
	}
	
	/**
	 * Create a structure that contains for each file all its records
	 * using an id that is extracted from the filename as the key
	 * @return
	 */
	protected HashMap<String, RecordList> groupByFile() {
		HashMap<String, RecordList> res = new HashMap<String, RecordList>();
		
		for(String filePath : this.keySet()) {
			String id = idFromFilepath(filePath);
			res.put(id, this.get(filePath));
		}
		
		return res;
	}
	
	/**
	 * Return the first integer from the filename of the given file
	 * @param filePath
	 * @return
	 */
	protected String idFromFilepath(String filePath) {
		String fileName = Paths.get(filePath).getFileName().toString();
		Matcher matcher = Pattern.compile("\\d+").matcher(fileName);
		matcher.find();
		return matcher.group();
	}	
	
	/**
	 * Create a recordList for each unique value in the primary key column
	 * @return
	 */
	protected HashMap<String, RecordList> groupByColumn() {
		HashMap<String, RecordList> res = new HashMap<String, RecordList>();
		
		for(String filePath : this.keySet()) {
			for(Record record : this.get(filePath)) {
				String id = (String) record.get(primary);
				if(res.containsKey(id)) {
					RecordList list = res.get(id);
					list.add(record);
				} else {
					RecordList list = new RecordList(filePath, columns);
					list.add(record);
					res.put(id, list);
				}
			}
		}
		
		return res;
	}
	
}
