package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class RecordFieldStringTest {

    @Test
    public void toStringTest() {
        DataFieldString fl = new DataFieldString("hoi");
        assertEquals(fl.toString(), "hoi");
    }
    
    @Test (expected = UnsupportedFormatException.class)
    public void getBooleanValueTest() throws UnsupportedFormatException {
        DataFieldString fl = new DataFieldString("10");
        fl.getBooleanValue();
    }
   
   @Test (expected = UnsupportedFormatException.class)
   public void getIntValueTest() throws UnsupportedFormatException {
       DataFieldString fl = new DataFieldString("10");
       fl.getIntegerValue();
   }
   
   @Test (expected = UnsupportedFormatException.class)
   public void getDoubleValueTest() throws UnsupportedFormatException {
       DataFieldString fl = new DataFieldString("10");
       fl.getBooleanValue();
   }
   
   @Test
   public void getStringValueTest() throws UnsupportedFormatException {
       DataFieldString fl = new DataFieldString("10");
       assertEquals(fl.toString(), "10");
   }
   
   @Test
   public void equalsTest() {
       DataFieldString fl = new DataFieldString("10");
       DataFieldString fl1 = new DataFieldString("10");
       assertEquals(fl, fl1);
   }
   
   @Test
   public void equalsNotTest() {
       DataFieldString fl = new DataFieldString("10");
       DataFieldString fl1 = new DataFieldString("5");
       assertFalse(fl.equals(fl1));
   }
   
   @Test
   public void equalsNot1Test() {
       DataFieldString fl = new DataFieldString("10");
       DataFieldBoolean fl1 = new DataFieldBoolean(true);
       assertFalse(fl.equals(fl1));
   }
}
