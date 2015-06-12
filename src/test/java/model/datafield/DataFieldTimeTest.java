package model.datafield;

import static org.junit.Assert.*;
import model.ColumnType;
import model.DateUtils;
import model.UnsupportedFormatException;

import org.junit.Before;
import org.junit.Test;

public class DataFieldTimeTest {

    DataFieldTime d1;
    
    @Before
    public void setUp() throws Exception {
        d1 = new DataFieldTime(DateUtils.parseTime("15:44", "HH:mm"));
    }


    /** Test if getIntegerValue throws an exception on DataFieldTime.
     * @throws UnsupportedFormatException
     */
    @Test(expected=UnsupportedFormatException.class)
    public void getIntValueTest() throws UnsupportedFormatException {
        d1.getIntegerValue();
    }

    /** Test if getDoubleValue throws an exception on DataFieldTime.
     * @throws UnsupportedFormatException
     */
    @Test(expected=UnsupportedFormatException.class)
    public void getDoubleValueTest() throws UnsupportedFormatException {
        d1.getDoubleValue();
    }

    /** Test if getBooleanValue throws an exception on DataFieldTime.
     * @throws UnsupportedFormatException
     */
    @Test(expected=UnsupportedFormatException.class)
    public void getBooleanValueTest() throws UnsupportedFormatException {
        d1.getBooleanValue();
    }

    @Test
    public void toStringTest() {
        assertEquals(d1.toString(), "15:44");
    }

    @Test
    public void getTimeValueTest() {
        assertEquals(d1.getTimeValue().toString(), "15:44");
    }
    
    @Test
    public void getTypeTest() {
        assertEquals(d1.getType(), ColumnType.TIME);
    }

}
