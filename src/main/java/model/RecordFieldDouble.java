package model;


/**
 * This class represents a recordfield that contains a float.
 * @author Matthijs
 *
 */
public class RecordFieldDouble implements RecordField {

    /**
     * This variable stores the value of float record.
     */
	protected double value;

	/**
	 * Construct a record field containing an float.
	 * @param d		- integer value of this float
	 */
	public RecordFieldDouble(double d) {
		value = d;
	}

	/**
	 * This method gives a string representation of this record field.
	 * @return     - String representation of this recordfield.
	 */
	public String toString() {
		return String.valueOf(value);
	}
}
