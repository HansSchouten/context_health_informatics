package model;

import java.io.IOException;
import java.util.ArrayList;

public class Group extends ArrayList<RecordList> {
	
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

	public String[] getColumns() {
		return columns;
	}

	public String getPrimary() {
		return primary;
	}
	
	
}
