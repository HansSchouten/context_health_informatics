package model.datafield;

import static org.junit.Assert.*;
import model.ColumnType;
import model.UnsupportedFormatException;

import org.junit.Before;
import org.junit.Test;

public class EmptyDataFieldTest {

    EmptyDataField e1;
    
    EmptyDataField e2;
    
    @Before
    public void setUp() throws Exception {
        e1 = new EmptyDataField();
    }

    @Test
    public void testToString() {
        assertEquals(e1.toString(), "Empty datafield");
    }

    @Test
    public void testHashCode() {
        assertEquals(e1.hashCode(), 0);
    }
    
    @Test
    public void getType() {
        assertEquals(ColumnType.STRING, e1.getType());
    }
    
    @Test(expected = UnsupportedFormatException.class)
    public void getBooleanValue() throws UnsupportedFormatException {
        e1.getBooleanValue();
    }
    
    @Test(expected = UnsupportedFormatException.class)
    public void getIntegerValue() throws UnsupportedFormatException {
        e1.getIntegerValue();
    }
    
    @Test
    public void equalsMethod() {
        assertEquals(true, e1.equals(e1));
    }

}
