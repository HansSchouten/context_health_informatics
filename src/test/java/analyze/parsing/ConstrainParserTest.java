package analyze.parsing;

import static org.junit.Assert.*;
import model.DateUtils;
import model.Record;
import model.SequentialData;
import model.datafield.DataFieldDouble;

import org.junit.Before;
import org.junit.Test;

import analyze.AnalyzeException;

public class ConstrainParserTest {

    SequentialData data;
    Record r1, r2, r3;
    Parser p;

    @Before
    public void setUp() throws Exception {
        data = new SequentialData();
        r1 = new Record(DateUtils.parseDate("2015/05/18", "yyyy/MM/dd"));
        r1.put("x", new DataFieldDouble(1));
        r2 = new Record(DateUtils.parseDate("2015/05/18", "yyyy/MM/dd"));
        r2.put("x", new DataFieldDouble(2));
        r3 = new Record(DateUtils.parseDate("2015/05/19", "yyyy/MM/dd"));
        r3.put("x", new DataFieldDouble(2));
        data.add(r1);
        data.add(r2);
        data.add(r3);
        p = new Parser();
    }

    @Test
    public void testParseFilter() throws AnalyzeException {
        Parser p = new Parser();
        SequentialData result = p.parse("FILTER WHERE COL(x) = 1.0", data);
        assertTrue(result.contains(r1));
    }

    @Test
    public void testParseFilter2() throws AnalyzeException {
        Parser p = new Parser();
        SequentialData result = p.parse("FILTER WHERE COL(x) = 1.0", data);
        assertFalse(result.contains(r2));
    }

    @Test
    public void testParseFilterOr() throws AnalyzeException {
        Parser p = new Parser();
        SequentialData result = p.parse("FILTER WHERE ((COL(x) = 1.0) or (COL(x) = 2.0))", data);
        assertTrue(result.contains(r1));
        assertTrue(result.contains(r2));
    }
    
    @Test
    public void testParseFilterAnd() throws AnalyzeException {
        r1.put("y", new DataFieldDouble(2));
        Parser p = new Parser();
        SequentialData result = p.parse("FILTER WHERE ((COL(x) = 1.0) and (COL(y) = 2.0))", data);
        assertTrue(result.contains(r1));
    }
    
    @Test
    public void testParseFilterAndNegative() throws AnalyzeException  {
        r1.put("y", new DataFieldDouble(2));
        
        SequentialData result = p.parse("FILTER WHERE ((COL(x) = 1.0) and (COL(y) = 2.0))", data);
        assertFalse(result.contains(r2));
    }
    
    @Test (expected = AnalyzeException.class)
    public void testParseFailingTestcase() throws AnalyzeException  {
        Parser p = new Parser();
        p.parse("FILTER WHERE ((COL(x) = 1.0)", data);
    }


}
