package model;

import java.util.HashMap;

/**
 * This class represents a single record that is read.
 * @author Matthijs
 *
 */
public class Record extends HashMap<String, RecordField> {

	/**
	 * Variable that is used to store the comments of this record.
	 */
	protected Comments comments;

	/**
	 * Version ID.
	 */
	private static final long serialVersionUID = 3865260272531927751L;

	/**
	 * Record constructor.
	 */
	public Record() {
		comments = new Comments();
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
}
