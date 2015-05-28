package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RecordFieldIntTest {

	@Test
	public void toStringTest() {
	    DataFieldInt fl = new DataFieldInt(10);
		assertEquals(fl.toString(), "10");
	}
	
	   @Test (expected = UnsupportedFormatException.class)
	    public void getBooleanValueTest() throws UnsupportedFormatException {
	        DataFieldInt fl = new DataFieldInt(10);
	        fl.getBooleanValue();
	    }
	   
	   @Test 
	   public void getIntValueTest() throws UnsupportedFormatException {
	       DataFieldInt fl = new DataFieldInt(10);
	       assertEquals(fl.getIntegerValue(), 10);
	   }
	   
	   @Test
	   public void getDoubleValueTest() throws UnsupportedFormatException {
	       DataFieldInt fl = new DataFieldInt(10);
	       assertTrue(fl.getDoubleValue() == 10.0);
	   }
	   
	   @Test
	   public void getStringValueTest() throws UnsupportedFormatException {
	       DataFieldInt fl = new DataFieldInt(10);
	       assertEquals(fl.toString(), "10");
	   }
	   
	   @Test
	   public void equalsTest() {
	       DataFieldInt fl = new DataFieldInt(10);
	       DataFieldInt fl1 = new DataFieldInt(10);
	       assertEquals(fl, fl1);
	   }
	   
	   @Test
	   public void equalsNotTest() {
	       DataFieldInt fl = new DataFieldInt(10);
	       DataFieldInt fl1 = new DataFieldInt(5);
	       assertFalse(fl.equals(fl1));
	   }
	   
       @Test
       public void equalsfTest() {
           DataFieldInt fl = new DataFieldInt(10);
           DataFieldDouble fl1 = new DataFieldDouble(10.0);
           assertEquals(fl, fl1);
       }
       
       @Test
       public void equalsfNotTest() {
           DataFieldInt fl = new DataFieldInt(10);
           DataFieldDouble fl1 = new DataFieldDouble(5.0);
           assertFalse(fl.equals(fl1));
       }
	   
	   @Test
	   public void equalsNot1Test() {
	       DataFieldInt fl = new DataFieldInt(10);
	       DataFieldBoolean fl1 = new DataFieldBoolean(true);
	       assertFalse(fl.equals(fl1));
	   }
}
