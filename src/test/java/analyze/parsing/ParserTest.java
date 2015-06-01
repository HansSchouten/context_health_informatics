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
/*
    @Test
    public void testParseWithVariable() throws AnalyzeException {
        System.out.println("".split(" ",2)[0]);
        
        Parser parser = new Parser();
        SequentialData result = parser.parse("$X = CHUNK PER 2 DAYS", data);
        System.out.println(result);
    }
*/
}
