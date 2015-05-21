package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class RecordFieldBooleanTest {

    @Test
    public void toStringTest() {
        DataFieldBoolean fl = new DataFieldBoolean(true);
        assertEquals(fl.toString(), "true");
    }
    
       @Test
        public void getBooleanValueTest() throws UnsupportedFormatException {
            DataFieldBoolean fl = new DataFieldBoolean(true);
            assertTrue(fl.getBooleanValue() == true);
        }
       
       @Test (expected = UnsupportedFormatException.class)
       public void getIntValueTest() throws UnsupportedFormatException {
           DataFieldBoolean fl = new DataFieldBoolean(true);
           fl.getIntegerValue();
       }
       
       @Test (expected = UnsupportedFormatException.class)
       public void getDoubleValueTest() throws UnsupportedFormatException {
           DataFieldBoolean fl = new DataFieldBoolean(true);
           fl.getDoubleValue();
       }
       
       @Test
       public void equalsTest() {
           DataFieldBoolean fl = new DataFieldBoolean(true);
           DataFieldBoolean fl1 = new DataFieldBoolean(true);
           assertEquals(fl, fl1);
       }
       
       @Test
       public void equalsNotTest() {
           DataFieldBoolean fl = new DataFieldBoolean(true);
           DataFieldBoolean fl1 = new DataFieldBoolean(false);
           assertFalse(fl.equals(fl1));
       }
       
       @Test
       public void equalsNot1Test() {
           DataFieldBoolean fl = new DataFieldBoolean(true);
           DataFieldInt fl1 = new DataFieldInt(10);
           assertFalse(fl.equals(fl1));
       }

}
