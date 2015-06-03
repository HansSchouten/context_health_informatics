package model;

import java.time.LocalDateTime;

/**
 * This class represents a datefield.
 *
 */
public class DataFieldDate extends DataFieldDateTime {

    /**
     * DateField constructor.
     * @param d the local date value.
     */
    public DataFieldDate(LocalDateTime d) {
        super(d);
    }

    /**
     * This method returns a string representation of DateField.
     * @return      - String representation of the record field.
     */
    @Override
    public String toString() {
        return date.toLocalDate().toString();
    }
}
