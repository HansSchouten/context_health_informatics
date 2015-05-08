package model;

import java.util.HashMap;

/**
 * This class represents a single record that is read.
 * @author Matthijs
 *
 */
public class Record extends HashMap<String, Object> implements Comparable<Record> {

	/**
	 * Version ID.
	 */
	private static final long serialVersionUID = 3865260272531927751L;
	private int timeStamp;

	/**
	 * Record constructor.
	 */
	public Record(int timeStamp) { 
		this.timeStamp = timeStamp;
	}
	
	/**
	 * getTimeStamp
	 */
	public int getTimeStamp() {
		return timeStamp;
	}
	
	@Override
    public int compareTo(Record other) {
        if(this.getTimeStamp() > other.getTimeStamp()) {
            return 1;
        } else {
            return -1;
        }
    }

}
