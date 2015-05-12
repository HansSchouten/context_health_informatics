package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class represents a single record that is read.
 * @author Matthijs
 *
 */
public class Record extends HashMap<String, RecordField> implements Comparable<Record> {

	/**
	 * Variable that is used to store the comments of this record.
	 */
	protected Comments comments;
	
	/**
	 * Variable that stores all the comments used.
	 */
	ArrayList<Integer> labels;

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
		labels = new ArrayList<Integer>();
	}
	
	/**
	 * getTimeStamp
	 */
	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}
	
	/**
	 * Compare two timestamps.
	 */
	@Override
    public int compareTo(Record other) {
        if (this.timeStamp.isAfter(other.timeStamp)) {
            return 1;
        } else {
            return -1;
        }
    }

	/**
	 * This method adds a comment to a record.
	 * @param comment		- String containing the new comment.
	 */
	public void addCommentToRecord(final String comment) {
		comments.addComments(comment);
	}

	/**
	 * This function returns the comments of the record.
	 * @param delimiter - The delimeter used to seperate several comments
	 * @return			- String containing all the comments,
	 *                     empty string if none.
	 */
	public String printComments(final String delimiter) {
		return comments.printComments(delimiter);
	}
	
	/**
     * This method checks whether a record contains a label.
     * @param labelnumber - Number of the label
     * @return            - true if the label is added to this record, false otherwise.
     */
    public boolean containsLabel(int labelnumber) {
        return labels.contains(labelnumber);
    }

    /**
     * This function adds a label to this record.
     * @param labelnumber  - Label that needs to be added.
     */
    public void addLabel(int labelnumber) {
        labels.add(labelnumber);
    }
}
