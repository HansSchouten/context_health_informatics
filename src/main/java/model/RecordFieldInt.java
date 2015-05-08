package model;

/**
 * This class represent a record field that contains an integer.
 * @author Matthijs
 *
 */
public class RecordFieldInt implements RecordField {

	protected int value;
	
	/**
	 * Construct a record field containing an integer.
	 * @param v		- integer value of this recordfield
	 */
	public RecordFieldInt(int v) {
		value = v;
	}
	
	public String toString() {
		return String.valueOf(value);
	}
	
	
	
}
