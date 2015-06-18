package analyze.comparing;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import model.Column;
import model.ColumnType;
import model.Record;
import model.SequentialData;
import model.UnsupportedFormatException;
import model.datafield.DataFieldDateTime;
import model.datafield.DataFieldDouble;
import model.datafield.DataFieldString;
import analyze.parsing.ParseException;

/**
 * This class represents an object that will do comparisons on data.
 * @author Elvan
 *
 */

public class Comparer {

    /** This variable stores the first sequential data that needs to be compared.
     */
    private SequentialData userData;

    /** This variable stores the first column name that needs to be compared.
     */
    private Column fromColumn;

    /** This variable stores the second column name that needs to be compared.
     */
    private Column toColumn;

     /** Construct a comparison that consists of the columns that need to be compared.
     * @param data            data object containing the data needs to be compared
     * @param column1        first column that needs to be compared
     * @param column2        second column that needs to be compared
     */
    public Comparer(SequentialData data, Column column1, Column column2) {
        userData = data;
        fromColumn = column1;
        toColumn = column2;
    }

    /**
     * This method performs the comparison on the sequential data.
     * @return        resulting differences of the comparison
     * @throws         ParseException                    - something went wrong while parsing
     * @throws         UnsupportedFormatException        - format is not supported
     */
    public SequentialData compare() throws ParseException, UnsupportedFormatException {
            SequentialData result = new SequentialData();

            if ((fromColumn.getType() == ColumnType.DATEandTIME || fromColumn.getType() == ColumnType.DATE)
                    && (toColumn.getType() == ColumnType.DATEandTIME || toColumn.getType() == ColumnType.DATE)) {
                result = calculateTimeDifference(userData, fromColumn, toColumn);
            } else if ((fromColumn.getType() == ColumnType.DOUBLE
                        || fromColumn.getType() == ColumnType.INT)
                        && (toColumn.getType() == ColumnType.DOUBLE
                        || toColumn.getType() == ColumnType.INT)) {
                result = calculateValueDifference(userData, fromColumn, toColumn);
            }

            return result;
    }

    /**
     * This method calculates time differences between two datecolumns.
     * @param data                    - the data that needs to be compared
     * @param fromDate                 - the first date
     * @param toDate                 - the second date
     * @return        resulting differences of the comparison
     * @throws ParseException        - something went wrong while parsing
     */
    public SequentialData calculateTimeDifference(SequentialData data, Column fromDate, Column toDate)
            throws ParseException {
        for (Record record : data) {
            if (record.containsKey(fromDate.getName()) && record.containsKey(toDate.getName())) {

                String difference = compareLocalDateTimes(((DataFieldDateTime)
                        record.get(fromDate.getName())).getDateValue(),
                        ((DataFieldDateTime) record.get(toDate.getName())).getDateValue());

                record.put("Time difference", new DataFieldString(difference));
            }
        }
        return data;
    }

    /**
     * This method calculates differences between values of two columns.
     * @param data            the data that needs to be compared
     * @param actual     the first column
     * @param entered         the second column
     * @return                resulting differences of the comparison
     * @throws         ParseException                    - something went wrong while parsing
     * @throws         UnsupportedFormatException        - format is not supported
     */
    public SequentialData calculateValueDifference(SequentialData data, Column actual, Column entered)
            throws ParseException, UnsupportedFormatException {
        for (Record record : data) {
            if (record.containsKey(actual.getName()) && record.containsKey(entered.getName())) {
                Double from = (Double) record.get(actual.getName()).getDoubleValue();
                Double to = (Double) record.get(entered.getName()).getDoubleValue();
                Double difference = from - to;

                record.put("Value difference", new DataFieldDouble(difference));
            }
        }
        return data;
    }

    /**
     * This method calculates differences between the actual and entered measurements.
     * @param data              the data that needs to be compared
     * @param actual            the first column
     * @param entered           the second column
     * @return                  resulting differences of the comparison
     * @throws ParseException                    - something went wrong while parsing
     * @throws UnsupportedFormatException        - format is not supported
     */
    public SequentialData calculateMeasurementDifference(SequentialData data, Column actual, Column entered)
            throws ParseException, UnsupportedFormatException {
        for (Record record : data) {
            if (record.containsKey(actual.getName()) && record.containsKey(entered.getName())) {
                Double from = (Double) record.get(fromColumn.getName()).getDoubleValue();
                Double to = (Double) record.get(toColumn.getName()).getDoubleValue();
                Double difference = to - from;

                record.put("Measurement difference", new DataFieldDouble(difference));
            }
        }
        return data;
    }

    /** This method calculates the time difference between two LocalDateTimes.
     * Specified in yearsmonthsdays hoursminutes
     * @return                    resulting differences of the comparison
     * @param fromDateTime        first date that needs to be compared
     * @param toDateTime        second date that needs to be compared
     * @throws                     ParseException        something went wrong while parsing
     */
    private String compareLocalDateTimes(LocalDateTime fromDateTime, LocalDateTime toDateTime)
            throws ParseException {

        LocalDateTime tempDateTime = LocalDateTime.from(fromDateTime);

        long years = tempDateTime.until(toDateTime, ChronoUnit.YEARS);
        tempDateTime = tempDateTime.plusYears(years);

        long months = tempDateTime.until(toDateTime, ChronoUnit.MONTHS);
        tempDateTime = tempDateTime.plusMonths(months);

        long days = tempDateTime.until(toDateTime, ChronoUnit.DAYS);
        tempDateTime = tempDateTime.plusDays(days);


        long hours = tempDateTime.until(toDateTime, ChronoUnit.HOURS);
        tempDateTime = tempDateTime.plusHours(hours);

        long minutes = tempDateTime.until(toDateTime, ChronoUnit.MINUTES);
        tempDateTime = tempDateTime.plusMinutes(minutes);

        String result = years + "y" + months + "m" + days + "d " + hours + "h" + minutes + "m";

        if (fromDateTime.isAfter(toDateTime)) {
            result = "-" + result;
        }
        return result;
    }
}
