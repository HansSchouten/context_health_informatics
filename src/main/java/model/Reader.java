package model;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
	    while (bufferedReader.ready()) {
	    	parseLine(recordList, bufferedReader.readLine());
	    }
	    bufferedReader.close();

		return recordList;
	}

	/**
	 * Parse one line of the file and add the result to the recordList.
	 * @param recordList   - Recordlist the line should be added to.
	 * @param line         - Line to be parsed.
	 */
	protected void parseLine(RecordList recordList, String line) {
    	if (line.contains(delimiter)) {
			try {
				recordList.add(this.createRecord(line));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				System.out.println("Iets ging fout bij het uitlezen van " + line);
			}
    	} else {
    		addMetaData(recordList, line);
		}
	}

	/**
	 * Add meta data from the current line to the recordList.
	 * @param recordList   - Recordlist that the data should be added to.
	 * @param line         - Line of metadata.
	 */
	protected void addMetaData(RecordList recordList, String line) {
		String metaData = (String) recordList.getProperty("metadata");
		if (metaData != null) {
			metaData += "\n" + line;
		} else {
			metaData = line;
		}
		recordList.setProperty("metadata", metaData);
	}

	/**
	 * Convert a single line into a Record.
	 * @param line     - line of the record.
	 * @return         - Newly created record.
	 * @throws ParseException is thrown as Record can't be created.
	 */
	protected Record createRecord(String line) throws ParseException  {
		//Need to be changed

		String[] parts = line.split(delimiter);

		Record record = new Record(getSortTimeStamp(parts));

		for (int i = 0; i < columns.length; i++) {
			switch (columns[i].characteristic) {
			case COMMENT:
				record.addCommentToRecord(parts[i]);
				break;
			case INT:
                record.put(
                        columns[i].getName(), createIntegerField(parts[i]));
                break;
			case DOUBLE:
                record.put(
                        columns[i].getName(), createDoubleField(parts[i]));
                break;
			default:
				record.put(
				        columns[i].getName(), new DataFieldString(parts[i]));
			}
		}
		return record;
	}

	/**
	 * getSortTimeStamp from field.
	 * @param fields the fields of the record
	 * @return LocalDateTime
	 * @throws ParseException parseexception is thrown as sorttimestamp can't be parsed.
	 */
	private  LocalDateTime getSortTimeStamp(String[] fields) throws ParseException {
		LocalDateTime tmpDate = null;
		LocalTime tmpTime = null;
		for (int i = 0; i < columns.length; i++) {
			if (ColumnType.getDateTypes().contains(columns[i].characteristic)
					&& ((DateColumn) columns[i]).sortOnThisField()) {
				DateColumn dColumn = ((DateColumn) columns[i]);
				if (dColumn.getDateFormat().equals("Excel epoch")) {
					return DateUtils.t1900toLocalDateTime(fields[i]);
				}
				if (dColumn.characteristic == ColumnType.DATEandTIME) {
					return DateUtils.parseDateTime(fields[i], dColumn.getDateFormat());
				}
				if (dColumn.characteristic == ColumnType.DATE) {
					tmpDate = DateUtils.parseDate(fields[i], dColumn.getDateFormat());
					if (tmpTime != null) {
						return DateUtils.addLocalTimeToLocalDateTime(tmpTime, tmpDate);
					}
				}
				if (dColumn.characteristic == ColumnType.TIME) {
					tmpTime = DateUtils.parseTime(fields[i], dColumn.getDateFormat());
					if (tmpDate != null) {
						return DateUtils.addLocalTimeToLocalDateTime(tmpTime, tmpDate);
					}
				}
			}
		}
		return tmpDate;
	}

	/**
	 * This method creates an integerfield from a string that is read.
	 * @param input    - String containing the number that should be stored.
	 * @return         - Recordfield with the right number.
	 * @throws NumberFormatException    - When conversion is not possible.
	 */
	protected DataField createIntegerField(String input) throws NumberFormatException {
	    return new DataFieldInt(Integer.parseInt(input));
	}

	   /**
     * This method creates an floatfield from a string that is read.
     * @param input    - String containing the number that should be stored.
     * @return         - Recordfield with the right number.
     * @throws NumberFormatException    - When conversion is not possible.
     */
    protected DataField createDoubleField(String input) throws NumberFormatException {
        return new DataFieldDouble(Double.parseDouble(input));
    }
}
