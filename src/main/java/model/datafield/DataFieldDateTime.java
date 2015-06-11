package model.datafield;

import java.time.LocalDateTime;

import model.ColumnType;
import model.UnsupportedFormatException;

/**
 * This class represents a datatimefield.
 *
 */
public class DataFieldDateTime implements DataField {

    /**
     * This variable stores the date of this Date record.
     */
    protected LocalDateTime date;

    /**
     * Construct a record field containing a date.
     * @param d     - the LocalDateTime object of this date
     */
    public DataFieldDateTime(LocalDateTime d) {
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
        throw new UnsupportedFormatException("Date/time cannot be converted to an integer");
    }

    @Override
    public double getDoubleValue() throws UnsupportedFormatException {
        throw new UnsupportedFormatException("Date/time cannot be converted to an integer");
    }

    @Override
    public boolean getBooleanValue() throws UnsupportedFormatException {
        throw new UnsupportedFormatException("Date/time cannot be converted to an double");
    }

    /**
     * Return the LocalDateTime of this date field.
     * @return        - the LocalDateTime
     */
    public LocalDateTime getDateValue() {
        return date;
    }

    @Override
    public ColumnType getType() {
        return ColumnType.DATEandTIME;
    }
}
