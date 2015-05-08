package model;

import java.time.LocalDateTime;
import java.util.Date;
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
	private LocalDateTime timeStamp;

	/**
	 * Record constructor.
	 */
	public Record(LocalDateTime timeStamp) { 
		this.timeStamp = timeStamp;
	}
	
	/**
	 * getTimeStamp
	 */
	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}
	
	@Override
    public int compareTo(Record other) {
        if(this.timeStamp.isAfter(other.timeStamp)){
            return 1;
        } else {
            return -1;
        }
    }

}
