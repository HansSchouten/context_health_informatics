package model;

import java.io.IOException;
import java.util.Map.Entry;
import java.util.TreeSet;

import model.datafield.DataField;
import analyze.parsing.ParseResult;

/**
 * This class represents data that has been ordered in sequential order.
 * @author Matthijs
 *
 */
public class SequentialData extends TreeSet<Record> implements ParseResult {

    /**
     * Serial Version ID.
     */
    private static final long serialVersionUID = -5826890838002651687L;
    /**
     * variable that contains columns.
     */
    private Column[] columns;

    /**
     * Add a recordList to this group.
     * @param recordList   - Recordlist that should be added.
     */
    public void addRecordList(RecordList recordList) {
        columns = recordList.columns;
        for (Record record : recordList) {
            add(record);
        }
    }

    /**
     * Convert sequential data object to string (structured in columns).
     * @param delimiter    - Delimiter to use for the conversion.
     * @param colnames       - Whether to include the column names at the top of the file.
     * @return             - String representation of the recordlist.
     * @throws IOException - Thrown when stringbuilder fails.
     */
    public String toString(String delimiter, boolean colnames) throws IOException {
        StringBuilder out = new StringBuilder();
        Column[] cols = getColumns();

         if (this.size() != 0) {
             if (colnames) {
                 out.append(getColumnNames(delimiter));
             }

             for (Record record : this) {
                for (Column c : cols) {
                    if (record.containsKey(c.getName())) {
                        Object value = record.get(c.getName()).toString();
                        out.append(value + delimiter);
                    } else {
                        out.append(delimiter);
                    }
                }
                out.setLength(out.length() - 1);
                out.append("\r\n");
            }
         }

         String output = out.toString();
         return output;
    }

    /**
     * Creates an array of all unique columns.
     * @return An array of all unique column names.
     */
    public Column[] getColumns() {
        TreeSet<String> columnSet = new TreeSet<String>();

        for (Record r : this) {
            for (String s : r.keySet()) {
                columnSet.add(s);
            }
        }

        Column[] res = new Column[columnSet.size()];
        int i = 0;
        for (String s : columnSet) {
            res[i] = new Column(s, ColumnType.STRING);
            i++;
        }

        return res;
    }

    /**
     * returns column with specified name.
     * @param name String
     * @return Column column
     */
    public Column getColumn(String name) {
        int index = -1;
        for (int i = 0; i < columns.length; i++) {
            System.out.println(columns[i]);
            if (columns[i].getName().equals(name)) {
                index = i;
                break;
            }
        }

        return columns[index];
    }

    /**
     * Makes a string of the column names.
     * @param delim            - The delimiter between the column names.
     * @return                 - String containing column names.
     */
    public String getColumnNames(String delim) {
        StringBuilder out = new StringBuilder();

        Column[] cols = getColumns();

        for (int i = 0; i < cols.length; i++) {
              out.append(cols[i].getName() + delim);
        }
        out.setLength(out.length() - 1);
        out.append("\r\n");

        return out.toString();
    }

    /**
     * Combine all records in this SequentialData object into one record.
     * This will create a record containing for each unique column name over all records, the first value it encounters
     * @return                  - One record representing this SequentialData object
     */
    public Record flattenSequential() {
        Record record = null;
        for (Record r1: this) {
            if (record == null) {
                record = r1;
            } else {
                addFields(record, r1);
            }
        }
        return record;
    }

    /**
     * Add the values of the columns of the second record that are not yet columns in the first given record.
     * @param record            - The records to which the values will be added
     * @param r1                - The records of which the values will be used
     */
    private void addFields(Record record, Record r1) {
        for (Entry<String, DataField> entry: r1.entrySet()) {
            if (!record.containsKey(entry.getKey())) {
                record.put(entry.getKey(), entry.getValue());
            }
        }
    }
}
