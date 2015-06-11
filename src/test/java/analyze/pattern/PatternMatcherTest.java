package analyze.pattern;

import static org.junit.Assert.*;

import java.util.ArrayList;

import model.DateUtils;
import model.Record;
import model.SequentialData;
import model.datafield.DataFieldDouble;

import org.junit.Before;
import org.junit.Test;

import analyze.AnalyzeException;
import analyze.chunking.ChunkingException;
import analyze.labeling.LabelFactory;
import analyze.parsing.Parser;

public class PatternMatcherTest {

    SequentialData data;
    PatternMatcher p;
    ArrayList<SequentialData> result;

    @Before
    public void setUp() throws Exception {
        data = new SequentialData();
        Record r1, r2, r3, r4, r5, r6;
        r1 = new Record(DateUtils.parseDate("2015/05/18", "yyyy/MM/dd"));
        r1.put("index", new DataFieldDouble(1));
        r1.addLabel(LabelFactory.getInstance().getNewLabel("A").getNumber());

        r2 = new Record(DateUtils.parseDate("2015/05/19", "yyyy/MM/dd"));
        r2.put("index", new DataFieldDouble(2));
        r2.addLabel(LabelFactory.getInstance().getNewLabel("B").getNumber());

        r3 = new Record(DateUtils.parseDate("2015/05/20", "yyyy/MM/dd"));
        r3.put("index", new DataFieldDouble(3));
        r3.addLabel(LabelFactory.getInstance().getNewLabel("C").getNumber());

        r4 = new Record(DateUtils.parseDate("2015/05/21", "yyyy/MM/dd"));
        r4.put("index", new DataFieldDouble(4));
        r4.addLabel(LabelFactory.getInstance().getNewLabel("D").getNumber());

        r5 = new Record(DateUtils.parseDate("2015/05/22", "yyyy/MM/dd"));
        r5.put("index", new DataFieldDouble(5));
        r5.addLabel(LabelFactory.getInstance().getNewLabel("E").getNumber());

        r6 = new Record(DateUtils.parseDate("2015/05/23", "yyyy/MM/dd"));
        r6.put("index", new DataFieldDouble(6));
        r6.addLabel(LabelFactory.getInstance().getNewLabel("F").getNumber());

        data.add(r1);
        data.add(r2);
        data.add(r3);
        data.add(r4);
        data.add(r5);
        data.add(r6);
        p = new PatternMatcher();
    }

    @Test (expected = PatternMatcherException.class)
    public void testInvalidPattern1() throws PatternMatcherException {
        result = p.match("", data);
    }

    @Test (expected = PatternMatcherException.class)
    public void testInvalidPattern2() throws PatternMatcherException {
        result = p.match("WITHIN(7)", data);
    }

    @Test (expected = PatternMatcherException.class)
    public void testInvalidPattern3() throws PatternMatcherException {
        result = p.match("WITHIN(7) LABEL(x)", data);
    }

    @Test (expected = PatternMatcherException.class)
    public void testInvalidPattern4() throws PatternMatcherException {
        result = p.match("LABEL(x) WITHIN(7)", data);
    }

    @Test (expected = PatternMatcherException.class)
    public void testInvalidPattern5() throws PatternMatcherException {
        result = p.match("LABEL(x) WITHIN(7)", data);
    }

    @Test (expected = PatternMatcherException.class)
    public void testInvalidPattern6() throws PatternMatcherException {
        result = p.match("LABEL(x) WITHIN(7) WITHIN(7) LABEL(y)", data);
    }


    @Test
    public void testUnknownPattern1() throws PatternMatcherException {
        result = p.match("LABEL(x)", data);
        assertEquals(0, result.size());
    }

    @Test
    public void testUnknownPattern2() throws PatternMatcherException {
        result = p.match("LABEL(x) LABEL(y)", data);
        assertEquals(0, result.size());
    }

    @Test
    public void testUnknownPattern3() throws PatternMatcherException {
        result = p.match("LABEL(x) WITHIN(7) LABEL(y)", data);
        assertEquals(0, result.size());
    }

    @Test
    public void testUnknownPatternWithin1() throws PatternMatcherException {
        result = p.match("LABEL(A) WITHIN(0) LABEL(B)", data);
        assertEquals(0, result.size());
    }

    @Test
    public void testUnknownPatternWithin2() throws PatternMatcherException {
        result = p.match("LABEL(A) WITHIN(2) LABEL(D)", data);
        assertEquals(0, result.size());
    }

    @Test
    public void testUnknownPatternWithin3() throws PatternMatcherException {
        result = p.match("LABEL(A) WITHIN(3) LABEL(C) WITHIN(1) LABEL(D)", data);
        assertEquals(0, result.size());
    }


    @Test
    public void testKnownPattern1() throws PatternMatcherException {
        result = p.match("LABEL(A)", data);
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).size());
    }

    @Test
    public void testKnownPattern2() throws PatternMatcherException {
        result = p.match("LABEL(A) LABEL(B)", data);
        assertEquals(1, result.size());
        assertEquals(2, result.get(0).size());
    }

    @Test
    public void testKnownPattern3() throws PatternMatcherException {
        result = p.match("LABEL(A) LABEL(B) LABEL(D)", data);
        assertEquals(1, result.size());
        assertEquals(4, result.get(0).size());
    }

    @Test
    public void testKnownPatternWithin1() throws PatternMatcherException {
        result = p.match("LABEL(A) WITHIN(2) LABEL(B)", data);
        assertEquals(1, result.size());
        assertEquals(2, result.get(0).size());
    }

    @Test
    public void testKnownPatternWithin2() throws PatternMatcherException {
        result = p.match("LABEL(A) WITHIN(2) LABEL(B)", data);
        assertEquals(1, result.size());
        assertEquals(2, result.get(0).size());
    }

    @Test
    public void testKnownPatternWithin3() throws PatternMatcherException {
        result = p.match("LABEL(A) WITHIN(3) LABEL(C) WITHIN(2) LABEL(D)", data);
        assertEquals(1, result.size());
    }

    @Test
    public void testKnownPatternWithin4() throws PatternMatcherException {
        result = p.match("LABEL(A) WITHIN(3) LABEL(C) WITHIN(10) LABEL(E)", data);
        assertEquals(1, result.size());
    }

}
