package model;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * This class contains utils for parsing different data strings.
 */
public class DateUtils {

	/**
	 * Convert a dates since 1900 to LocalDateTime.
	 * @param timeStamp    - Timestamp that needs to be converted.
	 * @return             - Converted timestamp.
	 */
	public static LocalDateTime t1900toLocalDateTime(String timeStamp) {
		double time = Double.parseDouble(timeStamp);
		return LocalDateTime.ofEpochSecond((long) ((time - 25567) * 86400) , 0, ZoneOffset.UTC);
	}

//	/**
//	 *
//	 * @param timeStamp
//	 * @param formatpattern
//	 * @return
//	 * @throws ParseException
//	 */
//	public static LocalDateTime parseDateSimpleFormat(String timeStamp, String formatpattern) throws ParseException {
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatpattern);
//		return LocalDateTime.parse(timeStamp, formatter);
//	}

    /**
     * Convert Date string to LocalDateTime.
     * @param dateStamp        - Date stamp that needs to be converted.
     * @param formatpattern    - Format of the datestamp.
     * @return                 - Converted timestamp.
     * @throws ParseException  - Thrown if reading goes not right.
     */
	public static LocalDateTime parseDate(String dateStamp, String formatpattern) throws ParseException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatpattern);
		return LocalDate.parse(dateStamp, formatter).atStartOfDay();
	}

	/**
	 * Convert Time string to LocalTime.
	 * @param timeStamp        - Date stamp that needs to be converted.
	 * @param formatpattern    - Format of the datestamp.
	 * @return                 - Converted timestamp.
	 * @throws ParseException  - Thrown if reading goes not right.
	 */
	public static LocalTime parseTime(String timeStamp, String formatpattern) throws ParseException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatpattern);
		return LocalTime.parse(timeStamp, formatter);
	}
}
