package model;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.Test;


public class DateUtilsTest {

    @Test
    public void t1900toEposchTest() {
        assertEquals(DateUtils.t1900toLocalDateTime(("42131.5")).toString(), "2015-05-07T12:00");
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

    @Test
    public void parseDateSlashTest() throws ParseException {
        assertEquals(DateUtils.parseDate("15/05/2015", "dd/MM/yyyy").toString(), "2015-05-15T00:00");
    }
    
    @Test
    public void parseDateDashTest() throws ParseException {
        assertEquals(DateUtils.parseDate("15-05-2015", "dd-MM-yyyy").toString(), "2015-05-15T00:00");
    }
    
    @Test
    public void parseDateSlashYYTest() throws ParseException {
        assertEquals(DateUtils.parseDate("15/05/15", "dd/MM/yy").toString(), "2015-05-15T00:00");
    }
    
    @Test
    public void parseDateDashYYTest() throws ParseException {
        assertEquals(DateUtils.parseDate("15-05-15", "dd-MM-yy").toString(), "2015-05-15T00:00");
    }

    @Test
    public void parseDateTimeDashTest() throws ParseException {
        assertEquals(DateUtils.parseDateTime("15-05-15 10:08", "yy-MM-dd HH:mm").toString(),
                "2015-05-15T10:08");
    }

    @Test
    public void parseDateTimeSecDashTest() throws ParseException {
        assertEquals(DateUtils.parseDateTime("15-05-15 10:08:08", "yy-MM-dd HH:mm:ss").toString(),
                "2015-05-15T10:08:08");
    }
    @Test
    public void parseDateTimeSlashTest() throws ParseException {
        assertEquals(DateUtils.parseDateTime("15/05/15 10:08", "yy/MM/dd HH:mm").toString(),
                "2015-05-15T10:08");
    }

    @Test
    public void parseDateTimeSecSlashTest() throws ParseException {
        assertEquals(DateUtils.parseDateTime("15/05/15 10:08:08", "yy/MM/dd HH:mm:ss").toString(),
                "2015-05-15T10:08:08");
    }
    
    @Test
    public void parseDateWithoutPrefixZero() throws ParseException {
        assertEquals(DateUtils.parseDate("6-05-15", "d-M-yy").toString(),
                "2015-05-06T00:00");
    }
}
