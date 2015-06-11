package model;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.LocalTime;

import model.datafield.DataField;
import model.datafield.DataFieldDate;
import model.datafield.DataFieldDateTime;
import model.datafield.DataFieldDouble;
import model.datafield.DataFieldInt;
import model.datafield.DataFieldString;
import model.datafield.DataFieldTime;

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
     * @param cols        the columns from left to right
     * @param dlmtr        the delimiter used to distinguish the columns
     */
    public Reader(Column[] cols, String dlmtr) {
        columns = cols;
        delimiter = dlmtr;
    }

    /**
     * Read the given file and return a RecordList representing the file.
     * @param filePath  - file that needs to be read.
     * @param colnames  - boolean indicating that the colnames should be read.
     * @return          - Recordlist with the representation of the read line.
     * @throws IOException - When parsing the line goes wrong.
     */
    public final RecordList read(final String filePath, Boolean colnames)
            throws IOException {
        String firstLine = "";
        RecordList recordList = new RecordList(columns);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));

        if (colnames) {
            firstLine = bufferedReader.readLine();
        }

        if (firstLine == null) {
            bufferedReader.close();
            throw new IOException("Columns could not be read");
        }

        while (bufferedReader.ready()) {
            parseLine(recordList, bufferedReader.readLine());
        }

        bufferedReader.close();

        readColumnNames(firstLine);

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
                System.out.println("An error occured while reading");
            }
        } else {
            addMetaData(recordList, line);
        }
    }

    /** Parse the first line as column names.
     * @param line         - Line to be parsed.
     */
    protected final void readColumnNames(String line) {
        if (line.contains(delimiter)) {
                String[] parts = line.split(delimiter);
                for (int i = 0; i < parts.length; i++) {
                    columns[i].setName(parts[i]);
                }
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
    public Record createRecord(String line) throws ParseException  {
        String[] parts = line.split("\\" + delimiter, -1);

        Record record = new Record(getSortTimeStamp(parts));

        for (int i = 0; i < columns.length; i++) {
            if (columns[i].isExcluded() || parts[i].equals("")) {
                continue;
            }
            switch (columns[i].characteristic) {
            case TIME:
            case DATEandTIME:
            case DATE:
                DateColumn dColumn = (DateColumn) columns[i];
                record.put(
                        columns[i].getName(),
                        createDataField(parts[i], dColumn)
                        );
                break;
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
     * @throws ParseException This is thrown as sorttimestamp can't be parsed.
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
        return new DataFieldInt(Integer.parseInt(input.trim()));
    }

    /**
     * This method creates a floatfield from a string that is read.
     * @param input    - String containing the number that should be stored.
     * @return         - Recordfield with the right number.
     * @throws NumberFormatException    - When conversion is not possible.
     */
    protected DataField createDoubleField(String input) throws NumberFormatException {
        return new DataFieldDouble(Double.parseDouble(input));
    }

    /**
     * This method creates a date field from the string and the given date format.
     * @param input        - String containing the date
     * @param dColumn    - Date format
     * @return            - Resulting DataFieldDate
     * @throws ParseException        - When conversion is not possible
     */
    private DataField createDataField(String input, DateColumn dColumn) throws ParseException {
        if (dColumn.getDateFormat().equals("Excel epoch")) {
            return new DataFieldDate(DateUtils.t1900toLocalDateTime(input));
        }
        if (dColumn.characteristic == ColumnType.DATEandTIME) {
            return new DataFieldDateTime(DateUtils.parseDateTime(input, dColumn.getDateFormat()));
        }
        if (dColumn.characteristic == ColumnType.DATE) {
            return new DataFieldDate(DateUtils.parseDate(input, dColumn.getDateFormat()));
        }
        if (dColumn.characteristic == ColumnType.TIME) {
            return new DataFieldTime(DateUtils.parseTime(input, dColumn.getDateFormat()));
        }
        return null;
    }

    /**
     * Reads a limited amount of lines from a file.
     * @param path The path to the file.
     * @param lines The amount of lines to be read. Must be larger than 0.
     * If the amount is higher, it returns the content of the whole file.
     * @return The specified amount of lines of the file.
     * @throws IOException When the reader cannot open or read the file.
     */
    public static String readLimited(String path, int lines) throws IOException {
        if (lines < 1) {
            return "";
        }

        StringBuilder res = new StringBuilder();
        FileReader fileReader = new FileReader(path);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String line = "";
        for (int i = 0; i < lines; i++) {
            line = bufferedReader.readLine();
            if (line != null) {
                res.append(line);
                res.append("\n");
            } else {
                break;
            }
        }
        bufferedReader.close();
        return res.toString();
    }
}
