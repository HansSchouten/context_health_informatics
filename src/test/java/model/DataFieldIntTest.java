package model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class DataFieldIntTest {
    DataFieldInt test;
    
    @Before
    public void setUp() throws Exception {
        test = new DataFieldInt(2);
    }

    /**
     * Test the toString method of DataFieldInt
     */
    @Test
    public void toStringTest() {
        
        assertEquals("2", test.toString());
        
    }
    
    /**
     * Test if getBooleanValue throws an exception on DataFieldDouble
     * @throws UnsupportedFormatException 
     */
    @Test(expected=UnsupportedFormatException.class)
    public void getBooleanValueTest11() throws UnsupportedFormatException {
        
        test.getBooleanValue();
        
    }
    
    /**
     * Test if getDoubleValue returns the right double of DataFieldInt
     */
    @Test
    public void getDoubleValueTest() throws UnsupportedFormatException {
        
        assertEquals(2.0, test.getDoubleValue(), 0.01);
        
    }
    
    /**
     * Test if getStringValue returns the right string of DataFieldInt
     */
    @Test
    public void getStringValueTest() throws UnsupportedFormatException {
        
        assertEquals("2", test.getStringValue());
        
    }
    
    /**
     * Test if getIntegerValue returns the right integer of DataFieldInt
     */
    @Test
    public void getIntegerValueTest() throws UnsupportedFormatException {
        
        assertEquals(2, test.getIntegerValue());
        
    }
    
    /**
     * Test if equals returns true on two equal DataFieldDate objects
     */
    @Test
    public void equalsTest() throws UnsupportedFormatException {
        DataFieldInt other = new DataFieldInt(2);
        
        assertTrue(test.equals(other));
        
    }
    
    /**
     * Test if equals returns false on two different DataFieldDate objects
     */
    @Test
    public void notEqualTest() throws UnsupportedFormatException {
        DataFieldDouble other = new DataFieldDouble(4);
        
        assertEquals(false, test.equals(other));
        
    }
    
    /**
     * Test if equals returns false on two different datafields
     */
    @Test
    public void notEqualFieldsTest() throws UnsupportedFormatException {
        DataFieldBoolean other = new DataFieldBoolean(true);
        
        assertEquals(false, test.equals(other));
        
    }
    
    /**
     * Test if the right hashcode is returned
     */
    @Test
    public void hashCodeTest() throws UnsupportedFormatException {
        
        assertEquals(2, test.hashCode());
        
    }
}
