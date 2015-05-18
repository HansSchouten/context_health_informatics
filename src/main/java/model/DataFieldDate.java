package model;

public class DataFieldDate implements DataField {
    
    /**
     * This method returns a string representation of this record field.
     * @return      - String representation of the record field.
     */
    public String toString() {
        return "date";
    }

    @Override
    public int getIntegerValue() throws UnsupportedFormatException {
        throw new UnsupportedFormatException("Date cannot be converted to an integer");
    }

}
