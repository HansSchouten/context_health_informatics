package model;

import java.time.LocalDateTime;

/**
 * This class represents a datafield that is an integer.
 * @author Matthijs
 *
 */
public class DataFieldDate implements DataField {

    /**
     * This variable stores the date of this Date record.
     */
    protected LocalDateTime date;

    /**
     * Construct a record field containing a date.
     * @param d     - the LocalDateTime object of this date
     */
    public DataFieldDate(LocalDateTime d) {
        date = d;
    }

    /**
     * This method returns a string representation of this record field.
     * @return      - String representation of the record field.
     */
    @Override
    public String toString() {
        return date.toString();
    }

    /**
     * This method returns a string representation of the date of this record field.
     * @return      - String representation of the record field.
     */
    public String toDateString() {
        return date.toLocalDate().toString();
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

    /**
     * Return the LocalDateTime of this date field.
     * @return        - the LocalDateTime
     */
    public LocalDateTime getDateValue() {
        return date;
    }

    @Override
    public String getStringValue() {
        return this.toString();
    }
}
