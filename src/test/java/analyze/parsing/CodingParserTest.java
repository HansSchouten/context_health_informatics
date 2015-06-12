package analyze.parsing;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import analyze.AnalyzeException;
import analyze.labeling.LabelFactory;
import analyze.labeling.LabelingException;
import static org.junit.Assert.*;
import model.DateUtils;
import model.Record;
import model.SequentialData;
import model.datafield.DataFieldBoolean;
import model.datafield.DataFieldDouble;
import model.datafield.DataFieldInt;

import org.junit.Test;

public class CodingParserTest {

    @Test
    public void constructorTest() {
        CodingParser cp = new CodingParser();
        assertTrue(cp.label == null);
        assertTrue(cp.condition == null);
    }
    
    @Test (expected = LabelingException.class)
    public void translateOperationTest() throws AnalyzeException {
        CodingParser cp = new CodingParser();
        String operation = "";
        cp.translateOperation(operation);
    }
    
    @Test (expected = LabelingException.class)
    public void translateOperation1Test() throws AnalyzeException {
        CodingParser cp = new CodingParser();
        cp.translateOperation(null);
    }
    
    @Test
    public void translateOperation2Test() throws AnalyzeException {
        CodingParser cp = new CodingParser();
        String operation = "WHERE hoi WITH doei";
        cp.translateOperation(operation);
        assertEquals("doei", cp.label);
        assertEquals("hoi", cp.condition);
    }
    
    @Test
    public void translateOperation3Test() throws AnalyzeException {
        CodingParser cp = new CodingParser();
        String operation = "WITH doei WHERE hoi ";
        cp.translateOperation(operation);
        assertEquals("doei", cp.label);
        assertEquals("hoi", cp.condition);
    }
    
    @Test (expected = AnalyzeException.class)
    public void translateOperation4Test() throws AnalyzeException {
        CodingParser cp = new CodingParser();
        String operation = "WITH doei";
        cp.parseOperation(operation, new SequentialData());
    }
    
    @Test (expected = LabelingException.class)
    public void translateOperation5Test() throws AnalyzeException {
        CodingParser cp = new CodingParser();
        String operation = "WHERE doei";
        cp.parseOperation(operation, new SequentialData());
    }
    
    @Test (expected = LabelingException.class)
    public void translateOperation6Test() throws AnalyzeException {
        CodingParser cp = new CodingParser();
        String operation = "WITH doei WITH hoi WHERE doei";
        cp.translateOperation(operation);
    }
    
    @Test (expected = LabelingException.class)
    public void translateOperation7Test() throws AnalyzeException {
        CodingParser cp = new CodingParser();
        String operation = "WHERE doei WHERE doei WITH hoi";
        cp.translateOperation(operation);
    }
    
    @Test (expected = LabelingException.class)
    public void translateOperation8Test() throws AnalyzeException {
        CodingParser cp = new CodingParser();
        String operation = "WHERE doei# WHERE doei WITH hoi";
        cp.translateOperation(operation);
    }
    
    @Test
    public void labelTestTest() throws AnalyzeException {
        CodingParser cp = new CodingParser();
        SequentialData list = new SequentialData();
        Record record = new Record(LocalDateTime.ofEpochSecond(1430909359, 0, ZoneOffset.UTC));
        record.put("hoi", new DataFieldBoolean(false));
        list.add(record);
        Record record1 = new Record(LocalDateTime.ofEpochSecond(1430909359, 0, ZoneOffset.UTC));
        Record record2 = new Record(LocalDateTime.ofEpochSecond(1430909359, 0, ZoneOffset.UTC));
        record2.put("hoi", new DataFieldInt(20));
        list.add(record1);
        list.add(record2);
        cp.parseOperation("WITH labelTest WHERE COL(hoi) + 10 = 15", list);
        int number = LabelFactory.getInstance().getNumberOfLabel("labelTest");
        assertTrue(!record.containsLabel(number));
        assertTrue(!record1.containsLabel(number));
        assertTrue(!record2.containsLabel(number));
    }
    
    
    @Test
    public void labelTest1Test() throws AnalyzeException {
        CodingParser cp = new CodingParser();
        SequentialData list = new SequentialData();
        Record record = new Record(LocalDateTime.ofEpochSecond(1430909359, 0, ZoneOffset.UTC));
        record.put("hoi", new DataFieldBoolean(false));
        list.add(record);
        Record record1 = new Record(LocalDateTime.ofEpochSecond(1430909359, 0, ZoneOffset.UTC));
        Record record2 = new Record(LocalDateTime.ofEpochSecond(1430909359, 0, ZoneOffset.UTC));
        record2.put("hoi", new DataFieldInt(20));
        list.add(record1);
        list.add(record2);
        cp.parseOperation("WHERE COL(hoi) + 10 = 15 WITH labelTest ", list);
        int number = LabelFactory.getInstance().getNumberOfLabel("labelTest");
        assertTrue(!record.containsLabel(number));
        assertTrue(!record1.containsLabel(number));
        assertTrue(!record2.containsLabel(number));
    }
    
    @Test
    public void parserTest() throws AnalyzeException, Exception {
        SequentialData list = new SequentialData();
        Record record = new Record(LocalDateTime.ofEpochSecond(1430909359, 0, ZoneOffset.UTC));
        record.put("hoi", new DataFieldBoolean(false));
        list.add(record);
        Record record1 = new Record(LocalDateTime.ofEpochSecond(1430909359, 0, ZoneOffset.UTC));
        Record record2 = new Record(LocalDateTime.ofEpochSecond(1430909359, 0, ZoneOffset.UTC));
        record2.put("hoi", new DataFieldInt(20));
        list.add(record1);
        list.add(record2);
        Parser cp = new Parser();
        cp.parse("LABEL WHERE COL(hoi) + 10 = 15 WITH labelTest ", list);
        int number = LabelFactory.getInstance().getNumberOfLabel("labelTest");
        assertTrue(!record.containsLabel(number));
        assertTrue(!record1.containsLabel(number));
        assertTrue(!record2.containsLabel(number));
    }
    
    @Test
    public void parserTestPattern() throws AnalyzeException, Exception {
        SequentialData data = new SequentialData();
        Record r1, r2, r3, r4, r5, r6;
        r1 = new Record(DateUtils.parseDate("2015/05/18", "yyyy/MM/dd"));
        r1.put("index", new DataFieldDouble(1));
        r1.addLabel(LabelFactory.getInstance().getNewLabel("A").getNumber());
        r1.addLabel(LabelFactory.getInstance().getNewLabel("D").getNumber());

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

        Parser cp = new Parser();
        cp.parse("LABEL WITH testlabel AFTER PATTERN LABEL(B) WITHIN(3) LABEL(C) WITHIN(2) LABEL(D)", data);
        int number = LabelFactory.getInstance().getNumberOfLabel("testlabel");
        assertTrue(!r1.containsLabel(number));
        assertTrue(!r2.containsLabel(number));
        assertTrue(!r3.containsLabel(number));
        assertTrue(r4.containsLabel(number));
        assertTrue(!r5.containsLabel(number));
        assertTrue(!r6.containsLabel(number));
    }
}
