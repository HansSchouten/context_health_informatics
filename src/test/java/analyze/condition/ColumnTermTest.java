package analyze.condition;

import static org.junit.Assert.*;

import java.util.HashMap;

import model.DataField;
import model.DataFieldInt;
import model.UnsupportedFormatException;

import org.junit.Test;

public class ColumnTermTest {

    @Test
    public void createColumnTermTest() {
        ColumnTerm cl = new ColumnTerm("hoi");
        assertEquals("hoi", cl.columnName);
    }
    
    @Test
    public void findColumnTermTest() {
        ColumnTerm cl = new ColumnTerm("hoi");
        HashMap<String, DataField> hm = new HashMap<String, DataField>();
        assertTrue(null == cl.evaluate(hm));
    }
    
    @Test
    public void findColumnTerm1Test() throws UnsupportedFormatException {
        ColumnTerm cl = new ColumnTerm("hoi");
        HashMap<String, DataField> hm = new HashMap<String, DataField>();
        hm.put("hoi", new DataFieldInt(10));
        assertTrue(10 == cl.evaluate(hm).getIntegerValue());
    }
    
    @Test
    public void findColumnTerm2Test() throws UnsupportedFormatException {
        ColumnTerm cl = new ColumnTerm("hoi");
        HashMap<String, DataField> hm = new HashMap<String, DataField>();
        hm.put("hoi", new DataFieldInt(10));
        assertTrue(10 == cl.evaluate(hm).getIntegerValue());
        hm.put("hoi", new DataFieldInt(12));
        assertTrue(12 == cl.evaluate(hm).getIntegerValue());
    }

}
