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
	
	/**
	 * This string contains the name of the group.
	 */
	protected String name;
	
	/**
	 * This string contains the delimiter that should be used for the group.
	 */
	protected String delimiter;
	
	/**
	 * This variable contains the columns of the group.
	 */
	protected Column[] columns;
	
	/**
	 * This variable contains the primary column of the group
	 */
	protected String primary;
	
	/**
	 * This variable contains a reader that reads the files. 
	 */
	protected Reader reader;

	/**
	 * Construct a group of files.
	 * @param name			the name of this group
	 * @param delimiter		the delimiter used to distinguish the columns
	 * @param columns		the columns from left to right
	 * @param primary		the property that represents the primary key
	 */
	public Group(String name, String delimiter, Column[] columns, String primary) 
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
	
	public String getName() {
		return name;
	}
	
	public String getDelimiter() {
		return delimiter;
	}

	/**
	 * This function returns all the column names of the columns. 
	 * @return		- Array containing all the column names of the columns.
	 */
	public String[] getColumnNames() {
		String[] columnNames = new String[columns.length];
		
		for(int i = 0; i < columns.length; i++)
			columnNames[i] = columns[i].name;
			
		return columnNames;
		
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
				RecordField id = (RecordField) record.get(primary);
				if(res.containsKey(id.toString())) {
					RecordList list = res.get(id.toString());
					list.add(record);
				} else {
					RecordList list = new RecordList(columns);
					list.add(record);
					res.put(id.toString(), list);
				}
			}
		}
		
		return res;
	}
	
}
