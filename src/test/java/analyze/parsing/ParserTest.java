package analyze.parsing;

import static org.junit.Assert.*;
import model.ChunkedSequentialData;
import model.DataFieldDate;
import model.DataFieldDouble;
import model.DateUtils;
import model.Record;
import model.SequentialData;

import org.junit.Before;
import org.junit.Test;

import analyze.AnalyzeException;

public class ParserTest {

    SequentialData data;
    Record r1, r2, r3;

    @Before
    public void setUp() throws Exception {
        data = new SequentialData();
        r1 = new Record(DateUtils.parseDate("2015/05/17", "yyyy/MM/dd"));
        r1.put("level", new DataFieldDouble(10));
        r1.put("date", new DataFieldDate(DateUtils.parseDate("2015/05/17", "yyyy/MM/dd")));
        
        r2 = new Record(DateUtils.parseDate("2015/05/17", "yyyy/MM/dd"));
        r2.put("level", new DataFieldDouble(20));
        r2.put("date", new DataFieldDate(DateUtils.parseDate("2015/05/17", "yyyy/MM/dd")));
        
        r3 = new Record(DateUtils.parseDate("2015/05/19", "yyyy/MM/dd"));
        r3.put("level", new DataFieldDouble(30));
        r3.put("date", new DataFieldDate(DateUtils.parseDate("2015/05/19", "yyyy/MM/dd")));
        
        data.add(r1);
        data.add(r2);
        data.add(r3);
    }

    @Test
    public void testConstructor() {
        Parser parser = new Parser();
        assertTrue(parser instanceof Parser);
    }

    @Test
    public void testParseUnknownOperator() throws AnalyzeException {
        Parser parser = new Parser();
        try {
            parser.parse("UNKNOWN", data);
        } catch (ParseException e) {
            assertEquals("Parse Error: 'UNKNOWN' contains no valid operation", e.getMessage());
        }
    }

    @Test(expected = ParseException.class)
    public void testParseOperatorWithoutOperation() throws AnalyzeException {
        Parser parser = new Parser();
        SequentialData result = parser.parse("COMPARE", data);
        assertTrue(result instanceof ChunkedSequentialData);
    }

    @Test
    public void testParse() throws AnalyzeException {
        Parser parser = new Parser();
        SequentialData result = parser.parse("CHUNK ON date", data);
        assertTrue(result instanceof ChunkedSequentialData);
    }

    @Test
    public void testParseWithoutPipeline() throws AnalyzeException {
        Parser parser = new Parser();
        SequentialData result1 = parser.parse("CHUNK PER 2 DAYS", data);
        assertEquals(2, result1.size());

        SequentialData result2 = parser.parse("COMPUTE AVERAGE(COL(level))", data);
        assertEquals(1, result2.size());
        assertEquals("20.0", result2.pollFirst().get("level").toString());
    }

    @Test
    public void testParseWithPipeline() throws AnalyzeException {
        Parser parser = new Parser();
        SequentialData result = parser.parse("CHUNK PER 2 DAYS\nCOMPUTE AVERAGE(COL(level))", data);
        assertEquals(2, result.size());
        assertEquals(15.0, result.pollFirst().get("level").getDoubleValue(), 0.01);
        assertEquals(30.0, result.pollLast().get("level").getDoubleValue(), 0.01);
    }

    @Test(expected = ParseException.class)
    public void testParseEmptyUsing() throws AnalyzeException {
        Parser parser = new Parser();
        parser.parse("CHUNK PER 2 DAYS USING", data);
    }

    @Test(expected = ParseException.class)
    public void testParseUsingUndefinedVariable() throws AnalyzeException {
        Parser parser = new Parser();
        parser.parse("CHUNK PER 2 DAYS USING $unknown", data);
    }

    @Test(expected = ParseException.class)
    public void testParseVariableNoOperation1() throws AnalyzeException {
        Parser parser = new Parser();
        parser.parse("$X", data);
    }

    @Test(expected = ParseException.class)
    public void testParseVariableNoOperation2() throws AnalyzeException {
        Parser parser = new Parser();
        parser.parse("$X =", data);
    }

    @Test
    public void testParseWithVariable() throws AnalyzeException {
        Parser parser = new Parser();
        SequentialData result = parser.parse("$X = CHUNK PER 2 DAYS", data);
        assertTrue(parser.variables.containsKey("$X"));
        assertEquals(parser.variables.get("$X"), result);
    }

    @Test
    public void testParseWithVariableUsingVariable() throws AnalyzeException {
        Parser parser = new Parser();
        parser.parse("$X = CHUNK PER 2 DAYS", data);
        SequentialData result2 = parser.parse("COMPUTE AVERAGE(COL(level)) USING $X", data);
        assertEquals(2, result2.size());
        assertEquals(15.0, result2.pollFirst().get("level").getDoubleValue(), 0.01);
        assertEquals(30.0, result2.pollLast().get("level").getDoubleValue(), 0.01);
    }

}
