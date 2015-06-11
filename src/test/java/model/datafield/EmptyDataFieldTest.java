package model.datafield;

import static org.junit.Assert.*;
import model.ColumnType;
import model.UnsupportedFormatException;

import org.junit.Before;
import org.junit.Test;

public class EmptyDataFieldTest {

    EmptyDataField e1;
    
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
    public void getDataTypeTest() {
        assertEquals(ColumnType.STRING, e1.getType());
    }
    
    @Test
    public void equalsTest() {
        EmptyDataField e2 = new EmptyDataField();
        assertTrue(e1.equals(e2));
    }
    
    @Test(expected=UnsupportedFormatException.class)
    public void getBooleanType() throws UnsupportedFormatException {
        e1.getBooleanValue();
    }
    
    @Test(expected=UnsupportedFormatException.class)
    public void getIntegerType() throws UnsupportedFormatException {
        e1.getIntegerValue();
    }

}
