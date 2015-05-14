package model;

import static org.junit.Assert.*;

import java.text.ParseException;
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
	public void readyyMMddtimestampTest() throws ParseException {
		assertEquals(DateUtils.parseDate("130218", "yyMMdd").toString(), "2013-02-18T00:00");
	}
}
