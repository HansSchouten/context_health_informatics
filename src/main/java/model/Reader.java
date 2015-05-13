package model;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * This class is used to read the files that are specified in groups.
 * @author Matthijs
 *
 */
public class Reader {

    /**
     * This variable stores the columns of the current group.
     */
	protected Column[] columns;

	/**
	 * This variable sores the delimiter of this group.
	 */
	protected String delimiter;

	/**
	 * Reader constructor.
	 * @param cols		the columns from left to right
	 * @param dlmtr		the delimiter used to distinguish the columns
	 */
	public Reader(Column[] cols, String dlmtr) {
		columns = cols;
		delimiter = dlmtr;
	}

	/**
	 * Read the given file and return a RecordList representing the file.
	 * @param filePath      - file that needs to be read.
	 * @return              - Recordlist with the representation of the read line.
     * @throws IOException  - When parsing the line goes wrong.
	 */
	public RecordList read(String filePath) throws IOException {

		RecordList recordList = new RecordList(columns);
		BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
	    for (String line; (line = bufferedReader.readLine()) != null;)
	    	parseLine(recordList, line);

	    bufferedReader.close();

		return recordList;
	}

	/**
	 * Parse one line of the file and add the result to the recordList.
	 * @param recordList   - Recordlist the line should be added to.
	 * @param line         - Line to be parsed.
	 */
	protected void parseLine(RecordList recordList, String line) {
    	if (line.contains(delimiter))
	    	recordList.add(this.createRecord(line));
    	else
    		addMetaData(recordList, line);
	}

	/**
	 * Add meta data from the current line to the recordList.
	 * @param recordList   - Recordlist that the data should be added to.
	 * @param line         - Line of metadata.
	 */
	protected void addMetaData(RecordList recordList, String line) {
		String metaData = (String) recordList.getProperty("metadata");
		if (metaData != null)
			metaData += "\n" + line;
		else
			metaData = line;
		recordList.setProperty("metadata", metaData);
	}

	/**
	 * Convert a single line into a Record.
	 * @param line     - line of the record.
	 * @return         - Newly created record.
	 */
	protected Record createRecord(String line) {
		//Need to be changed
		Record record = new Record(DateUtils.t1900toLocalDateTime("42000"));

		String[] parts = line.split(delimiter);

		for (int i = 0; i < columns.length; i++) {
			switch (columns[i].characteristic) {
			case COMMENT:
				record.addCommentToRecord(parts[i]);
				break;
			case INT:
                record.put(
                        columns[i].name, createIntegerField(parts[i]));
                break;
			case DOUBLE:
                record.put(
                        columns[i].name, createDoubleField(parts[i]));
                break;
			default:
				record.put(
				        columns[i].name, new RecordFieldString(parts[i]));
			}
		}
		return record;
	}

	/**
	 * This method creates an integerfield from a string that is read.
	 * @param input    - String containing the number that should be stored.
	 * @return         - Recordfield with the right number.
	 * @throws NumberFormatException    - When conversion is not possible.
	 */
	protected RecordField createIntegerField(String input) throws NumberFormatException {
	    return new RecordFieldInt(Integer.valueOf(input));
	}

	   /**
     * This method creates an floatfield from a string that is read.
     * @param input    - String containing the number that should be stored.
     * @return         - Recordfield with the right number.
     * @throws NumberFormatException    - When conversion is not possible.
     */
    protected RecordField createDoubleField(String input) throws NumberFormatException {
        return new RecordFieldDouble(Double.valueOf(input));
    }
}
