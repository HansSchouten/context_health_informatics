package model;

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
    
    

}
