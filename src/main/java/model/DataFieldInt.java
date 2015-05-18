package model;

/**
 * This class represent a datafield containing a Integer.
 * @author Matthijs
 *
 */
public class DataFieldInt implements DataField {

    /**
     * This variable stores the value of the datafield.
     */
    protected int value;

    /**
     * Construct a DataFieldInt containing an integer.
     * @param val   - Value of the datafield.
     */
    public DataFieldInt(int val) {
        value = val;
    }

    @Override
    public int getIntegerValue() throws UnsupportedFormatException {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public double getDoubleValue() {
        return (double) value;
    }

    @Override
    public boolean getBooleanValue() throws UnsupportedFormatException {
        throw new UnsupportedFormatException("Integer datafield cannot be converted to a boolean.");
    }

    @Override
    public String getStringValue() {
        return String.valueOf(value);
    }
}
