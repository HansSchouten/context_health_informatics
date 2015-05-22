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

	/**
	 * Add a recordList to this group.
	 * @param recordList   - Recordlist that should be added.
	 */
	public void addRecordList(RecordList recordList) {
		for (Record record : recordList) {
			add(record);
		}
	}

	/**
	 * Convert sequential data object to string (structured in columns).
	 * @param delimiter    - Delimiter to use for the conversion.
	 * @param columns	   - The columns of all files.
	 * @return             - String representation of the recordlist.
	 * @throws IOException - Thrown when stringbuilder fails.
	 */
	public String toString(String delimiter, Column[] columns) throws IOException {
		StringBuilder out = new StringBuilder();

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
			out.append("\r\n ");
		}
		System.out.println(out);
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
}
