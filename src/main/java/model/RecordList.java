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

	private static final long serialVersionUID = -907389172333757537L;
	protected HashMap<String, Object> properties;

	protected Column[] columns;

	
	/**
	 * RecordList constructor.
	 */
	public RecordList(Column[] columns) {
		this.columns = columns;
		this.properties = new HashMap<String, Object>();
	}

	/**
	 * Set a property of this recordList to the given value.
	 * @param key The key for for the item in the hashmap
	 * @param value The value for the item in the hashmap
	 */
	public void setProperty(String key, Object value) {
		this.properties.put(key, value);
	}
	
	/**
	 * Return the value of the given property
	 * @param key
	 * @return
	 */
	public Object getProperty(String key) {
		return this.properties.get(key);
	}

	/**
	 * Convert record list to string (structured in columns)
	 * @param RecordList
	 * @return delimiter
	 */
	public String toString(String delimiter) throws IOException {
		StringBuilder out = new StringBuilder();
		
		for(int i=0; i < columns.length; i++) {
	          out.append(columns[i].name + delimiter);
		}
		
		out.setLength(out.length() - 1);
		out.append("\n ");
		
		for(int i=0; i < size(); i++) {
			
			Record rec = get(i);
			
			for(int j=0; j < columns.length; j++) {
				String key = columns[j].name;
			    Object value = rec.get(key).toString();
			    out.append(value + delimiter );
			}
			
			out.setLength(out.length() - 1);
			out.append("\n ");
		}
		
		String output = out.toString();
		return output;
	}

}
