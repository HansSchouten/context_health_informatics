package model;

/**
 * This class represents a datafield, that does not have a value.
 * @author Matthijs
 *
 */
public class EmptyDataField implements DataField {

    /**
     * This method constructs an EmptyDatafield, that does not contain anything.
     */
    public EmptyDataField() { }

    @Override
    public int getIntegerValue() throws UnsupportedFormatException {
        throw new UnsupportedFormatException("Empty datafield does not contain anything.");
    }

    @Override
    public double getDoubleValue() throws UnsupportedFormatException {
        throw new UnsupportedFormatException("Empty datafield does not contain anything.");
    }

    @Override
    public boolean getBooleanValue() throws UnsupportedFormatException {
        throw new UnsupportedFormatException("Empty datafield does not contain anything.");
    }

    @Override
    public String getStringValue() {
        return "Empty datafield";
    }

}
