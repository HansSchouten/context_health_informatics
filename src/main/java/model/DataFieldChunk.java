package model;
/**
 * This class represents a datafield that is a chunk.
 * @author Hans Schouten
 *
 */
public class DataFieldChunk implements DataField {

    /**
     * This value stores the SequentialData value of the datafield.
     */
    protected SequentialData value;

    /**
     * Constructs a recordField with as value a SequentialData object.
     * @param data        	The value of the created data field.
     */
    public DataFieldChunk(final SequentialData data) {
        value = data;
    }

    /**
     * Return the data in this chunk.
     * @return				The data
     */
    public SequentialData getDataValue() {
    	return value;
    }

    /**
     * This method returns a string representation of this record field.
     * @return      		String representation of the record field.
     */
    public String toString() {
        return "data";
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
        return value.toString();
    }

}
