package model.datafield;

import static org.junit.Assert.*;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;

import model.UnsupportedFormatException;
import model.datafield.DataFieldDate;
import model.datafield.DataFieldDateTime;
import model.datafield.DataFieldInt;

import org.junit.Before;
import org.junit.Test;

public class DataFieldDateTest {

    DataFieldDateTime date;
    
    @Before
    public void setUp() throws Exception {
        date = new DataFieldDate(LocalDateTime.now());
    }

    /**
     * Test the toString method of DataFieldDate
     */
    @Test
    public void toStringTest() {

        LocalDateTime now = LocalDateTime.now();
        date = new DataFieldDateTime(now);
        assertEquals(now.toString(), date.toString());
        
    }
    
    /**
     * Test if getDoubleValue throws an exception on DataFieldDate
     * @throws UnsupportedFormatException 
     */
    @Test(expected=UnsupportedFormatException.class)
    public void getDoubleValueTest() throws UnsupportedFormatException {
        
        date.getDoubleValue();
        
    }
    
    /**
     * Test if getIntegerValue throws an exception on DataFieldDate
     * @throws UnsupportedFormatException 
     */
    @Test(expected=UnsupportedFormatException.class)
    public void getIntValueTest() throws UnsupportedFormatException {
        
        date.getIntegerValue();
        
    }
    
    /**
     * Test if getBoolean throws an exception on DataFieldDate
     * @throws UnsupportedFormatException 
     */
    @Test(expected=UnsupportedFormatException.class)
    public void getBooleanValueTest() throws UnsupportedFormatException {
        
        date.getBooleanValue();
        
    }
    
    /**
     * Test if equals returns false on two different datafields
     */
    @Test
    public void notEqualFieldsTest() throws UnsupportedFormatException {
        DataFieldInt other = new DataFieldInt(2);
        
        assertEquals(false, date.equals(other));
        
    }
        

}
