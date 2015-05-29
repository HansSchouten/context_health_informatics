package analyze.parsing;

import static org.junit.Assert.*;
import model.DataFieldDouble;
import model.DateUtils;
import model.Record;
import model.SequentialData;

import org.junit.Before;
import org.junit.Test;

import analyze.AnalyzeException;

public class CommentingParserTest {

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
    public void testParseAllComments() throws AnalyzeException {
        Parser p = new Parser();
        String comment = "this is a comment";
        SequentialData result = p.parse("COMMENT " + comment + " WHERE COL(x) > 0", data);
        assertEquals(comment, r1.printComments(""));
        assertEquals(comment, r2.printComments(""));
        assertEquals(comment, r3.printComments(""));
    }

    @Test
    public void testParseOneComment() throws AnalyzeException {
        Parser p = new Parser();
        String comment = "this is a comment";
        SequentialData result = p.parse("COMMENT " + comment + " WHERE COL(x) = 1.0", data);
        assertEquals(comment, r1.printComments(""));
        assertEquals("", r2.printComments(""));
        assertEquals("", r3.printComments(""));
    }

    @Test
    public void testParseNoComments() throws AnalyzeException {
        Parser p = new Parser();
        String comment = "this is a comment";
        SequentialData result = p.parse("COMMENT " + comment + " WHERE COL(x) > 2.0", data);
        assertEquals("", r1.printComments(""));
        assertEquals("", r2.printComments(""));
        assertEquals("", r3.printComments(""));
    }

}
