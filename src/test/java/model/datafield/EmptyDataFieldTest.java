package model.datafield;

import static org.junit.Assert.*;

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

}
