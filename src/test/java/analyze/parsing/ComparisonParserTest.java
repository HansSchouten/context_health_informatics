package analyze.parsing;

import static org.junit.Assert.*;
import model.DateUtils;
import model.Record;
import model.SequentialData;
import model.datafield.DataField;
import model.datafield.DataFieldDouble;

import org.junit.Before;
import org.junit.Test;

import analyze.AnalyzeException;
import analyze.labeling.Label;
import analyze.labeling.LabelFactory;

public class ComparisonParserTest {

    SequentialData data;
    Record r1, r2, r3, r4, r5;
    Parser p;

    @Before
    public void setUp() throws Exception {
        LabelFactory labelFactory = LabelFactory.getInstance();
        Label Y = labelFactory.getNewLabel("Y");
        Label Z = labelFactory.getNewLabel("Z");

        data = new SequentialData();
        r1 = new Record(DateUtils.parseDate("2015/05/18", "yyyy/MM/dd"));
        r1.put("x", new DataFieldDouble(1));
        r2 = new Record(DateUtils.parseDate("2015/05/18", "yyyy/MM/dd"));
        r2.put("x", new DataFieldDouble(2));
        r3 = new Record(DateUtils.parseDate("2015/05/19", "yyyy/MM/dd"));
        r3.put("x", new DataFieldDouble(2));
        r4 = new Record(DateUtils.parseDate("2015/05/20", "yyyy/MM/dd"));
        r4.put("x", new DataFieldDouble(2));
        r5 = new Record(DateUtils.parseDate("2015/05/21", "yyyy/MM/dd"));
        r5.put("x", new DataFieldDouble(2));
        data.add(r1);
        r1.addLabel(Y.getNumber());
        r1.addLabel(Z.getNumber());
        data.add(r2);
        data.add(r3);
        r3.addLabel(Z.getNumber());
        data.add(r4);
        data.add(r5);
        p = new Parser();
    }

    @Test
    public void testParsePatternCount0() throws AnalyzeException, Exception {
        DataField result = (DataField) p.parse("COMPARE PATTERN LABEL(X)", data);
        assertEquals(0, result.getIntegerValue());
    }

    @Test
    public void testParsePatternCount1() throws AnalyzeException, Exception {
        DataField result = (DataField) p.parse("COMPARE PATTERN LABEL(Y)", data);
        assertEquals(1, result.getIntegerValue());
    }

    @Test
    public void testParsePatternCount2() throws AnalyzeException, Exception {
        DataField result = (DataField) p.parse("COMPARE PATTERN LABEL(Z)", data);
        assertEquals(2, result.getIntegerValue());
    }

}
