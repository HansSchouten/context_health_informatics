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
	 * Variable that is used to store the comments that are added to this record. 
	 */
	protected Comments comments;


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
		comments = new Comments();
	}
	
	/**
	 * getTimeStamp
	 */
	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}
	
	/**
	 * Compare two timestamps
	 */
	@Override
    public int compareTo(Record other) {
        if(this.timeStamp.isAfter(other.timeStamp)){
            return 1;
        } else {
            return -1;
        }
    }

	/**
	 * This method adds a comment to a record
	 * @param comment		- String containing the new comment.
	 */
	public void addCommentToRecord(String comment) {
		comments.addComments(comment);
	}
	
	/**
	 * This function returns the comments of the record. 
	 * @param delimiter		- The delimeter used to seperate several comments
	 * @return				- String containing all the comments, empty string if none. 
	 */
	public String printComments(String delimiter) {
		
		return comments.printComments(delimiter);
	}
}
