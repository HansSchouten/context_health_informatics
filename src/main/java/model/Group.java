package model;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class represents a group of files.
 * @author Hans Schouten
 *
 */
public class Group extends HashMap<String, RecordList> {

    /**
     * Serial version ID.
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
	 * This variable contains the primary column of the group.
	 */
	protected String primary;

	/**
	 * This variable contains a reader that reads the files.
	 */
	protected Reader reader;

	/**
	 * Construct a group of files.
	 * @param inputName		the name of this group
	 * @param dlmtr		    the delimiter used to distinguish the columns
	 * @param cols     		the columns from left to right
	 * @param primaryKey	the property that represents the primary key
	 */
	public Group(String inputName, String dlmtr, Column[] cols, String primaryKey) {
		name = inputName;
		delimiter = dlmtr;
		columns = cols;
		primary = primaryKey;
		reader = new Reader(columns, delimiter);
	}

	/**
	 * Construct a basic group.
	 * @param inputName      - Name of the group.
	 */
	public Group(String inputName) {
		this.name = inputName;
	}

	/**
	 * Add a file to this group.
	 * @param filePath     - Path to the file.
	 * @throws IOException - Thrown when reading the file goes wrong.
	 */
	public void addFile(String filePath) throws IOException {
		put(filePath, reader.read(filePath));
	}

	/**
	 * This method returns the name of the group.
	 * @return     - Name of the group.
	 */
	public String getName() {
		return name;
	}

	/**
	 * This method returns the delimiter of the group.
	 * @return     - Delimiter of the group
	 */
	public String getDelimiter() {
		return delimiter;
	}

	/**
	 * This function returns all the column names of the columns.
	 * @return		- Array containing all the column names of the columns.
	 */
	public String[] getColumnNames() {
		String[] columnNames = new String[columns.length];

		for (int i = 0; i < columns.length; i++)
			columnNames[i] = columns[i].name;

		return columnNames;
	}

	/**
	 * This method returns the primary key of the group.
	 * @return         - Primary key.
	 */
	public String getPrimary() {
		return primary;
	}

	/**
	 * Group all records by the specified primary key.
	 * @return         - Groups sorted on primary key.
	 */
	public HashMap<String, RecordList> groupByPrimary() {
		if (primary == null)
			return groupByFile();
		else
			return groupByColumn();
	}

	/**
	 * Create a structure that contains for each file all its records.
	 * Using an id that is extracted from the filename as the key.
	 * @return         - All the recordlists group by file.
	 */
	protected HashMap<String, RecordList> groupByFile() {
		HashMap<String, RecordList> res = new HashMap<String, RecordList>();

		for (String filePath : this.keySet()) {
			String id = idFromFilepath(filePath);
			res.put(id, this.get(filePath));
		}
		return res;
	}

	/**
	 * Return the first integer from the filename of the given file.
	 * @param filePath - Path to the file.
	 * @return         - Id that goes with the file path.
	 */
	protected String idFromFilepath(String filePath) {
		String fileName = Paths.get(filePath).getFileName().toString();
		Matcher matcher = Pattern.compile("\\d+").matcher(fileName);
		matcher.find();
		return matcher.group();
	}

	/**
	 * Create a recordList for each unique value in the primary key column.
	 * @return     - Hashmap that groups the recordslist by columns.
	 */
	protected HashMap<String, RecordList> groupByColumn() {
		HashMap<String, RecordList> res = new HashMap<String, RecordList>();

		for (String filePath : this.keySet()) {
			for (Record record : this.get(filePath)) {
				RecordField id = (RecordField) record.get(primary);
				if (res.containsKey(id.toString())) {
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
