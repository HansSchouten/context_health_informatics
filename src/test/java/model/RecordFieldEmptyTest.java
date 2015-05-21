package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class RecordFieldEmptyTest {
    
   @Test (expected = UnsupportedFormatException.class)
    public void getBooleanValueTest() throws UnsupportedFormatException {
        EmptyDataField fl = new EmptyDataField();
        fl.getBooleanValue();
    }
   
   @Test (expected = UnsupportedFormatException.class)
   public void getIntValueTest() throws UnsupportedFormatException {
       EmptyDataField fl = new EmptyDataField();
       fl.getIntegerValue();
   }
   
   @Test (expected = UnsupportedFormatException.class)
   public void getDoubleValueTest() throws UnsupportedFormatException {
       EmptyDataField fl = new EmptyDataField();
       fl.getDoubleValue();
   }
   
   @Test
   public void getStringValueTest() throws UnsupportedFormatException {
       EmptyDataField fl = new EmptyDataField();
       assertEquals(fl.getStringValue(), "Empty datafield");
   }
   
   @Test
   public void equalsTest() {
       EmptyDataField fl = new EmptyDataField();
       EmptyDataField fl1 = new EmptyDataField();
       assertEquals(fl, fl1);
   }
   
   @Test
   public void equalsNotTest() {
       EmptyDataField fl = new EmptyDataField();
       DataFieldBoolean fl1 = new DataFieldBoolean(true);
       assertFalse(fl.equals(fl1));
   }
}
