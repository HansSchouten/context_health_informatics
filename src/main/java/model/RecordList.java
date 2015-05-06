package model;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class represents a list of records.
 * @author Matthijs
 *
 */
public class RecordList extends ArrayList<Record> {

	private static final long serialVersionUID = -907389172333757537L;
	private HashMap<String, Object> properties;
	
	/**
	 * RecordList constructor.
	 */
	public RecordList() {
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

}
