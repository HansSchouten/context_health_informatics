package model.datafield;

import java.time.LocalTime;

import model.UnsupportedFormatException;

/**
 * This class represents a timefield.
 *
 */
public class DataFieldTime implements DataField {

    /**
     * This variable stores the date of this Date record.
     */
    private LocalTime time;

    /**
     * Construct a record field containing a date.
     * @param t     - the LocalTime object of this time
     */
    public DataFieldTime(LocalTime t) {
        this.time = t;
    }

    /**
     * This method returns a string representation of this record field.
     * @return      - String representation of the record field.
     */
    @Override
    public String toString() {
        return time.toString();
    }

    @Override
    public int getIntegerValue() throws UnsupportedFormatException {
        throw new UnsupportedFormatException("Time cannot be converted to an integer");
    }

    @Override
    public double getDoubleValue() throws UnsupportedFormatException {
        throw new UnsupportedFormatException("Time cannot be converted to an integer");
    }

    @Override
    public boolean getBooleanValue() throws UnsupportedFormatException {
        throw new UnsupportedFormatException("Time cannot be converted to an double");
    }

    /**
     * Return the LocalDateTime of this date field.
     * @return        - the LocalDateTime
     */
    public LocalTime getTimeValue() {
        return time;
    }
}
