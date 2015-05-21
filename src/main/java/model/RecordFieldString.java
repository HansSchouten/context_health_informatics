package model;

/**
 * This class represents a recordfield that contains a string.
 * @author Matthijs
 *
 */
public class RecordFieldString implements RecordField {

	/**
	 * This value stores the string value of the RecordField.
	 */
	protected String value;

	/**
	 * Constructs a recordField with as value a string.
	 * @param string		- The value of the created recordField.
	 */
	public RecordFieldString(final String string) {
		value = string;
	}

	/**
	 * This method returns a string representation of the recordfieldString.
     * @return     - String representation of this recordfield.
     */
	public String toString() {
		return value;
	}



}