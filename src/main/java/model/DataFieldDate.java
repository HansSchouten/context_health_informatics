package model;
/**
 * This class represents a datafield that is an integer.
 * @author Matthijs
 *
 */
public class DataFieldDate implements DataField {

    /**
     * This method returns a string representation of this record field.
     * @return      - String representation of the record field.
     */
    @Override
	public String toString() {
        return "date";
    }

    @Override
    public int getIntegerValue() throws UnsupportedFormatException {
        throw new UnsupportedFormatException("Date cannot be converted to an integer");
    }

    @Override
    public double getDoubleValue() throws UnsupportedFormatException {
        throw new UnsupportedFormatException("Date cannot be converted to an integer");
    }

    @Override
    public boolean getBooleanValue() throws UnsupportedFormatException {
        throw new UnsupportedFormatException("Date cannot be converted to an double");
    }

    @Override
    public String getStringValue() {
        return "date";
    }
    //TODO Use this field
}
