package model;


/**
 * This class represents a datafield that contains a double.
 * @author Matthijs
 *
 */
public class DataFieldDouble implements DataField {

    /**
     * This variable stores the value of float record.
     */
    protected Double value;

    /**
     * Construct a record field containing an float.
     * @param d     - integer value of this float
     */
    public DataFieldDouble(double d) {
        value = d;
    }

    /**
     * This method gives a string representation of this record field.
     * @return     - String representation of this recordfield.
     */
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public int getIntegerValue() throws UnsupportedFormatException {
        return value.intValue();
    }
}