package model;

/**
<<<<<<< HEAD
 * This class represents a DataField containing a Boolean.
=======
 * This class represents a DataField containing a Boolean 
>>>>>>> master
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
    public String getStringValue() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof DataFieldBoolean) {

            return ((DataFieldBoolean) o).value == this.value;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return value ? 1 : 0;
    }
}
