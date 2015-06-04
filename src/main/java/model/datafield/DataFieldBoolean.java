package model.datafield;

import model.UnsupportedFormatException;

/**
 * This class represents a DataField containing a Boolean.
 * @author Matthijs
 *
 */
public class DataFieldBoolean implements DataField {

    /**
     * This variable stores the value of the datafield.
     */
    protected boolean value;

    /**
     * Construct a datafield containing a boolean.
     * @param bool      - value of the datafield.
     */
    public DataFieldBoolean(boolean bool) {
        value = bool;
    }

    @Override
    public int getIntegerValue() throws UnsupportedFormatException {
        throw new UnsupportedFormatException("Cannot convert boolean to integer");
    }

    @Override
    public double getDoubleValue() throws UnsupportedFormatException {
        throw new UnsupportedFormatException("Cannot convert boolean to double");
    }

    @Override
    public boolean getBooleanValue() throws UnsupportedFormatException {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof DataFieldBoolean) {
            return ((DataFieldBoolean) o).value == this.value;
        }
        return false;
    }

    @Override
    public int hashCode() {

        if (value) {
            return 1;
        } else {
            return 0;
        }
    }
}
