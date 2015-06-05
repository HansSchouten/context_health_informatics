package model.datafield;

import model.ColumnType;
import model.UnsupportedFormatException;


/**
 * This class represents a datafield that contains a double.
 * @author Matthijs
 *
 */
public class DataFieldDouble implements DataField {

    /**
     * This variable stores the value of float record.
     */
    protected double value;

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
    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public int getIntegerValue() throws UnsupportedFormatException {
        throw new UnsupportedFormatException("Double cannot be converted to an integer");
    }

    @Override
    public double getDoubleValue() {
        return value;
    }

    @Override
    public boolean getBooleanValue() throws UnsupportedFormatException {
        throw new UnsupportedFormatException("Double cannot be converted to an boolean");
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof DataFieldDouble) {
            return ((DataFieldDouble) o).value == this.value;
        } else if (o instanceof DataFieldInt) {
            return ((DataFieldInt) o).value == this.value;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (int) value;
    }
    

    @Override
    public ColumnType getType() {
        return ColumnType.DOUBLE;
    }
}
