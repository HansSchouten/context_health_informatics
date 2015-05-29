package analyze.comparing;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;

import analyze.computation.AVG;
import analyze.computation.COUNT;
import analyze.computation.DEVIATION;
import analyze.computation.MAX;
import analyze.computation.MIN;
import analyze.computation.SQUARED;
import analyze.computation.SUM;
import analyze.computation.VARIANCE;
import model.Column;
import model.ColumnType;
import model.DataField;
import model.DataFieldDate;
import model.DataFieldString;
import model.DateColumn;
import model.DateUtils;
import model.Record;
import model.Reader;
import model.SequentialData;
import model.UnsupportedFormatException;

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
     * @param data			data object containing the data needs to be compared
     * @param column1		first column that needs to be compared
     * @param column2		second column that needs to be compared
     */
    public Comparer(SequentialData data, Column column1, Column column2) {
    	userData = data;
    	fromColumn = column1;
    	toColumn = column2;
    }

    /**
     * This method performs the comparison on the sequential data.
     * @return		resulting differences of the comparison
     * @throws 		ParseException
     */
    public SequentialData compare() throws ParseException {
    		SequentialData result = new SequentialData();

    		if (fromColumn.getType() == ColumnType.DATEandTIME && toColumn.getType() == ColumnType.DATEandTIME) {
    		for (Record record : userData) {
    			if (record.containsKey(fromColumn.getName()) && record.containsKey(toColumn.getName())) {

    			String difference = compareLocalDateTimes(((DataFieldDate)
    					record.get(fromColumn.getName())).getDateValue(),
            			((DataFieldDate) record.get(toColumn.getName())).getDateValue());
            	Record rec;
    			rec = new Record(DateUtils.parseDate(
    						"1111/11/11",
    						"yyyy/mm/DD"));
    			rec.put("Time difference", new DataFieldString(difference));
    			result.add(rec);

    		}
    	}
    }
			return result;
    }

    /** This method calculates the time difference between two LocalDateTimes.
     * Specified in yearsmonthsdays hoursminutes
     * @return					resulting differences of the comparison
     * @param fromDateTime		first date that needs to be compared
     * @param toDateTime		second date that needs to be compared
     * @throws 					ParseException
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

		long seconds = tempDateTime.until(toDateTime, ChronoUnit.SECONDS);

		String result = years + "y" + months + "m" + days + "d " + hours + "h" + minutes + "m";

		return result;

	}




}
