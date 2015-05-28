package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RecordFieldDoubleTest {

	@Test
	public void toStringTest() {
	    DataFieldDouble fl = new DataFieldDouble(10.0);
		assertEquals(fl.toString(), "10.0");
	}
	
   @Test (expected = UnsupportedFormatException.class)
    public void getBooleanValueTest() throws UnsupportedFormatException {
        DataFieldDouble fl = new DataFieldDouble(10.0);
        fl.getBooleanValue();
    }
   
   @Test (expected = UnsupportedFormatException.class)
   public void getIntValueTest() throws UnsupportedFormatException {
       DataFieldDouble fl = new DataFieldDouble(10.0);
       fl.getIntegerValue();
   }
   
   @Test
   public void getDoubleValueTest() throws UnsupportedFormatException {
       DataFieldDouble fl = new DataFieldDouble(10.0);
       assertTrue(fl.getDoubleValue() == 10);
   }
   
   @Test
   public void getStringValueTest() throws UnsupportedFormatException {
       DataFieldDouble fl = new DataFieldDouble(10.0);
       assertEquals(fl.getStringValue(), "10.0");
   }
   
   @Test
   public void equalsTest() {
       DataFieldDouble fl = new DataFieldDouble(10.0);
       DataFieldDouble fl1 = new DataFieldDouble(10.0);
       assertEquals(fl, fl1);
   }
   
   @Test
   public void equalsNotTest() {
       DataFieldDouble fl = new DataFieldDouble(10.0);
       DataFieldDouble fl1 = new DataFieldDouble(5);
       assertFalse(fl.equals(fl1));
   }
   
   @Test
   public void equalsNot1Test() {
       DataFieldDouble fl = new DataFieldDouble(10.0);
       DataFieldBoolean fl1 = new DataFieldBoolean(true);
       assertFalse(fl.equals(fl1));
   }

}
