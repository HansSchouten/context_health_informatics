package model;


/**
 * This class represents a recordfield that contains a float.
 * @author Matthijs
 *
 */
public class RecordFieldFloat implements RecordField {
	
	protected double value;

	/**
	 * Construct a record field containing an float.
	 * @param d		- integer value of this float
	 */
	public RecordFieldFloat(double d) {
		value = d;
	}
	
	public String toString() {
		return String.valueOf(value);
	}
	
}
