package model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class DataFieldDoubleTest {
	DataFieldDouble test;
	
	@Before
	public void setUp() throws Exception {
		test = new DataFieldDouble(2.0);
	}

	/**
	 * Test the toString method of DataFieldDouble
	 */
	@Test
    public void toStringTest() {
		
		assertEquals("2.0", test.toString());
		
	}
	
	/**
	 * Test if getIntegerValue throws an exception on DataFieldDouble
	 * @throws UnsupportedFormatException 
	 */
	@Test(expected=UnsupportedFormatException.class)
    public void getIntValueTest1() throws UnsupportedFormatException {
		
		test.getIntegerValue();
		
	}
	
	/**
	 * Test if getBooleanValue throws an exception on DataFieldDouble
	 * @throws UnsupportedFormatException 
	 */
	@Test(expected=UnsupportedFormatException.class)
    public void getBooleanValueTest1() throws UnsupportedFormatException {
		
		test.getBooleanValue();
		
	}
	
	/**
	 * Test if getStringValue returns the right string of DataFieldDouble
	 * @throws UnsupportedFormatException 
	 */
	@Test
    public void getStringValueTest1() throws UnsupportedFormatException {
		
		assertEquals("2.0", test.getStringValue());
		
	}
	
	/**
	 * Test if equals method returns true on two equal DataFieldDoubles
	 */
	@Test
    public void equalsTest() throws UnsupportedFormatException {
		DataFieldDouble other = new DataFieldDouble(2.0);
		
		assertTrue(test.equals(other));
		
	}
	
	/**
	 * Test if equals method returns false on two different DataFieldDoubles
	 */
	@Test
    public void notEqualTest() throws UnsupportedFormatException {
		DataFieldDouble other = new DataFieldDouble(4.0);
		
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
