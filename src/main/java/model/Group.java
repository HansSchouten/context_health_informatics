package model;

import java.io.IOException;
import java.util.ArrayList;

/**
 * This class represents a group of files that are all of the same format and can be read in. 
 * @author Matthijs
 *
 */
public class Group extends ArrayList<RecordList> {
	
    /**
     * 
     */
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
	 * Add a file to this group
	 * @param filePath
	 * @throws IOException 
	 */
	public void addFile(String filePath) throws IOException
	{
		add(reader.read(filePath));
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
	
	
}
