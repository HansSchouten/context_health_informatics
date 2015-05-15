package model;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.Test;


public class DateUtilsTest {

	@Test
    public void t1900toEposchTest() {
		assertEquals(DateUtils.t1900toLocalDateTime(("42129.5")).toString(), "2015-05-07T12:00");
	}

	@Test
	public void readHHMMtimestampTest() throws ParseException {
		assertEquals(DateUtils.parseTime("1244", "HHmm").toString(), "12:44");
	}
	
	@Test
	public void parseDateTimeTest() throws ParseException {
		assertEquals(DateUtils.parseDateTime("130218 1240", "yyMMdd HHmm").toString(), "2013-02-18T12:40");
	}

	@Test
	public void readyyMMddtimestampTest() throws ParseException {
		assertEquals(DateUtils.parseDate("130218", "yyMMdd").toString(), "2013-02-18T00:00");
	}

	@Test
	public void addLocalTimeToLocalDateTimeTest() throws ParseException {
		LocalTime time = DateUtils.parseTime("1944", "HHmm");
		LocalDateTime date  = DateUtils.parseDate("2015-05-15", "yyyy-MM-dd");
		assertEquals("2015-05-15T19:44", DateUtils.addLocalTimeToLocalDateTime(time, date).toString());
	}
}
