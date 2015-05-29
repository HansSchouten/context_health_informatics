
package model;

import java.io.IOException;
import java.util.TreeSet;

/**
 * This class represents data that has been ordered in sequential order.
 * @author Matthijs
 *
 */
public class SequentialData extends TreeSet<Record> {

    /**
     * Serial Version ID.
     */
	private static final long serialVersionUID = -5826890838002651687L;
	
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
	 * @param colnames	   - Whether to include the column names at the top of the file.
	 * @return             - String representation of the recordlist.
	 * @throws IOException - Thrown when stringbuilder fails.
	 */
	public String toString(String delimiter, boolean colnames) throws IOException {
		StringBuilder out = new StringBuilder();
		Column[] columns = getColumns();

		 if (this.size() != 0) {
			 if (colnames) {
				 out.append(getColumnNames(delimiter));
			 }

			 for (Record record : this) {
				for (Column c : columns) {
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
			res[i] = new Column(s);
			i++;
		}

		return res;
	}
	
	
	public Column getColumn(String name) {

		int index = -1;
		for (int i=0;i< columns.length;i++) {
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
     * @param delim       - The delimiter between the column names.
     * @return 				- String containing column names.
     */
	public String getColumnNames(String delim) {
		StringBuilder out = new StringBuilder();

		Column[] columns = getColumns();

		for (int i = 0; i < columns.length; i++) {
	          out.append(columns[i].getName() + delim);
		}
		out.setLength(out.length() - 1);
		out.append("\r\n");

		return out.toString();
	}
}