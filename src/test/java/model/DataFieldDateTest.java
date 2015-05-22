package model;

import static org.junit.Assert.*;

import java.io.IOException;
import java.text.ParseException;

import org.junit.Before;
import org.junit.Test;

public class DataFieldDateTest {

	DataFieldDate date;
	
	@Before
	public void setUp() throws Exception {
		date = new DataFieldDate();
	}

	/**
	 * Test the toString method of DataFieldDate
	 */
	@Test
    public void toStringTest() {
		
		assertEquals("date", date.toString());
		
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
	 * Test if getString returns a string on DataFieldDate
	 */
	@Test
    public void getStringValueTest() throws UnsupportedFormatException {
		
		assertEquals("date", date.getStringValue());
		
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
