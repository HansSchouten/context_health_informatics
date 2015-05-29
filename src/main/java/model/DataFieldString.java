package model;

/**
 * This class represents a datafield that contains a string.
 * @author Matthijs
 *
 */
public class DataFieldString implements DataField {

    /**
     * This value stores the string value of the datafield.
     */
    protected String value;

    /**
     * Constructs a recordField with as value a string.
     * @param string        - The value of the created data field.
     */
    public DataFieldString(final String string) {
        value = string;
    }

    /**
     * This method returns a string representation of the datafieldString.
     * @return     - String representation of this data field.
     */
    @Override
    public String toString() {
        return value;
    }

    @Override
    public int getIntegerValue() throws UnsupportedFormatException {
        throw new UnsupportedFormatException("Cannot convert string to int");
    }

    @Override
    public double getDoubleValue() throws UnsupportedFormatException {
        throw new UnsupportedFormatException("Cannot convert string to double");
    }

    @Override
    public boolean getBooleanValue() throws UnsupportedFormatException {
        throw new UnsupportedFormatException("Cannot convert string to boolean");
    }

    @Override
    public String getStringValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof DataFieldString) {
            return ((DataFieldString) o).value.equals(this.value);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return value.length();
    }
}
