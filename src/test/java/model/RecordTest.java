package model;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;

import org.junit.Test;

public class RecordTest {
    
    @Test
    public void compareToTest() throws ParseException {
        Record c1 = new Record(
                DateUtils.parseDate(
                        "1995/08/04",
                        "yyyy/mm/DD"));
        Record c2 = new Record(
                DateUtils.parseDate(
                        "1995/08/05",
                        "yyyy/mm/DD"));
        assertEquals(c1.compareTo(c2), -1);
    }
    
    @Test
    public void compareToTest2() throws ParseException {
        Record c1 = new Record(DateUtils.parseDate("1995/08/05", "yyyy/mm/DD"));
        Record c2 = new Record(DateUtils.parseDate("1995/08/04", "yyyy/mm/DD"));
        assertEquals(c1.compareTo(c2), 1);
    }

}
