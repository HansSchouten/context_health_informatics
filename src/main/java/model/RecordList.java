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
     *
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
	 * Constructs a recordlist.
	 * @param columns  - Columns of the recordlist.
	 */
	public RecordList(final Column[] columns) {
		this.columns = columns;
		this.properties = new HashMap<String, Object>();
	}

	/**
	 * Set a property of this recordList to the given value.
	 * @param key      - The key for for the item in the hashmap
	 * @param value    - The value for the item in the hashmap
	 */
	public void setProperty(final String key, final Object value) {
		this.properties.put(key, value);
	}

	/**
	 * Return the value of the given property.
	 * @param key      - Key to the value that is needed.
	 * @return         - Object of the property
	 */
	public Object getProperty(final String key) {
		return this.properties.get(key);
	}

	/**
	 * Convert record list to string (structured in columns).
	 * @param delimiter    - Specifies the delimiter for the to string.
	 * @return             - String representation of recordlist
	 * @throws IOException
	 */
	public String toString(final String delimiter) throws IOException {
		StringBuilder out = new StringBuilder();

		for (int i = 0; i < columns.length; i++) {
	          out.append(columns[i].name + delimiter);
		}

		out.setLength(out.length() - 1);
		out.append("\n ");
		
		for (int i = 0; i < size(); i++) {

			Record rec = get(i);

			for (int j = 0; j < columns.length; j++) {
				String key = columns[j].name;
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
