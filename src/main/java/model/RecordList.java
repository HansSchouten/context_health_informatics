package model;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class represents a list of records.
 * @author Matthijs
 *
 */
public class RecordList extends ArrayList<Record> {

    /**
     * Serial version ID.
     */
	private static final long serialVersionUID = -907389172333757537L;

	/**
	 * This hashmap stores the names of the columns with their properties.
	 */
	protected HashMap<String, Object> properties;

	/**
	 * This variable stores the columns of this recordlist.
	 */
	protected Column[] columns;

	/**
	 * Construct a new RecordList, with the given columns.
	 * @param cols  - Columns of the recordlist.
	 */
	public RecordList(Column[] cols) {
		columns = cols;
		this.properties = new HashMap<String, Object>();
	}

	/**
	 * Set a property of this recordList to the given value.
	 * @param key      - The key for for the item in the hashmap
	 * @param value    - sThe value for the item in the hashmap
	 */
	public void setProperty(String key, Object value) {
		this.properties.put(key, value);
	}

	/**
	 * Return the value of the given property.
	 * @param key      - The key to the property.
	 * @return         - The property corresponding to the key.
	 */
	public Object getProperty(String key) {
		return this.properties.get(key);
	}

	/**
	 * Adds a record to the selected RecordList.
	 * @param rec      - Record that needs to be added.
	 * @return         - The recordlist with the added element.
	 */
	public RecordList addRecord(Record rec) {
		this.add(rec);
		return this;
	}

	/**
	 * Returns the record corresponding to the given index.
	 * @param index    - the index of the record
	 * @return         - The record corresponding to the index.
	 */
	public Record getRecord(int index) {
		return this.get(index);
	}

	/**
	 * Convert record list to string (structured in columns).
	 * @param delimiter    - Delimiter to use for the conversion.
	 * @return             - String representation of the recordlist.
	 * @throws IOException - Thrown when stringbuilder fails.
	 */
	public String toString(String delimiter) throws IOException {
		StringBuilder out = new StringBuilder();

		for (int i = 0; i < columns.length; i++) {
	          out.append(columns[i].getName() + delimiter);
		}

		out.setLength(out.length() - 1);
		out.append("\n ");

		for (int i = 0; i < size(); i++) {

			Record rec = get(i);

			for (int j = 0; j < columns.length; j++) {
				String key = columns[j].getName();
			    Object value = rec.get(key).toString();
			    out.append(value + delimiter);
			}

			out.setLength(out.length() - 1);
			out.append("\n ");
		}

		String output = out.toString();
		return output;
	}
}
