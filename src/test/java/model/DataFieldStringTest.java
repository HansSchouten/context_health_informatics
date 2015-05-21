package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class DataFieldStringTest {
	
	DataFieldString test;
	
	@Before
	public void setUp() throws Exception {
		test = new DataFieldString("string");
	}

	/**
	 * Test the toString method of DataFieldString
	 */
	@Test
    public void toStringTest() {
		
		assertEquals("string", test.toString());
		
	}
	
	/**
	 * Test if getIntegerValue throws an exception on DataFieldString
	 * @throws UnsupportedFormatException 
	 */
	@Test(expected=UnsupportedFormatException.class)
    public void getIntValueTest1() throws UnsupportedFormatException {
		
		test.getIntegerValue();
		
	}
	
	/**
	 * Test if getIntegerValue throws an exception on DataFieldString
	 * @throws UnsupportedFormatException 
	 */
	@Test(expected=UnsupportedFormatException.class)
    public void getDoubleValueTest1() throws UnsupportedFormatException {
		
		test.getDoubleValue();
		
	}
	
	/**
	 * Test if getBooleanValue throws an exception on DataFieldString
	 * @throws UnsupportedFormatException 
	 */
	@Test(expected=UnsupportedFormatException.class)
    public void getBooleanValueTest1() throws UnsupportedFormatException {
		
		test.getBooleanValue();
		
	}
	
	/**
	 * Test if getStringValue returns the right string of DataFieldString
	 * @throws UnsupportedFormatException 
	 */
	@Test
    public void getStringValueTest1() throws UnsupportedFormatException {
		
		assertEquals("string", test.getStringValue());
		
	}
	
	/**
	 * Test if equals method returns true on two equal DataFieldDoubles
	 */
	@Test
    public void equalsTest() throws UnsupportedFormatException {
		DataFieldString other = new DataFieldString("string");
		
		assertTrue(test.equals(other));
		
	}
	
	/**
	 * Test if equals method returns false on two different DataFieldDoubles
	 */
	@Test
    public void notEqualTest() throws UnsupportedFormatException {
		DataFieldString other = new DataFieldString("different");
		
		assertEquals(false, test.equals(other));
		
	}
	
	/**
	 * Test if equals returns false on two different datafields
	 */
	@Test
    public void notEqualFieldsTest() throws UnsupportedFormatException {
		DataFieldInt other = new DataFieldInt(2);
		
		assertEquals(false, test.equals(other));
		
	}
}
