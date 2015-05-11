package model;

/**
 * This class represent a record field that contains an integer.
 * @author Matthijs
 *
 */
public class RecordFieldInt implements RecordField {

    /**
     * This variable stores the value of int record.
     */
	protected int value;

	/**
	 * Construct a record field containing an integer.
	 * @param v		- integer value of this recordfield
	 */
	public RecordFieldInt(final int v) {
		value = v;
	}

	/**
	 * This method gives a string representation of this recordfield.
	 */
	public String toString() {
		return String.valueOf(value);
	}
}
