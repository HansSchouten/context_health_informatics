package analyze.condition;

import static org.junit.Assert.*;

import model.DataFieldInt;
import model.Record;
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
        Record record = new Record(null);
        assertTrue(null == cl.evaluate(record));
    }
    
    @Test
    public void findColumnTerm1Test() throws UnsupportedFormatException {
        ColumnTerm cl = new ColumnTerm("hoi");
        Record record = new Record(null);
        record.put("hoi", new DataFieldInt(10));
        assertTrue(10 == cl.evaluate(record).getIntegerValue());
    }
    
    @Test
    public void findColumnTerm2Test() throws UnsupportedFormatException {
        ColumnTerm cl = new ColumnTerm("hoi");
        Record record = new Record(null);
        record.put("hoi", new DataFieldInt(10));
        assertTrue(10 == cl.evaluate(record).getIntegerValue());
        record.put("hoi", new DataFieldInt(12));
        assertTrue(12 == cl.evaluate(record).getIntegerValue());
    }

}
