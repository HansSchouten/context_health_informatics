package analyze.parsing;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import analyze.AnalyzeException;
import analyze.labeling.LabelFactory;
import analyze.labeling.LabelingException;
import static org.junit.Assert.*;
import model.Record;
import model.SequentialData;
import model.datafield.DataFieldBoolean;
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
        cp.parse("LABEL WHERE COL(hoi) + 10 = 15 WITH labelTest \n \n \n label where true with labelTest1", list);
        int number = LabelFactory.getInstance().getNumberOfLabel("labelTest");
        int number1 = LabelFactory.getInstance().getNumberOfLabel("labelTest1");
        assertTrue(!record.containsLabel(number));
        assertTrue(!record1.containsLabel(number));
        assertTrue(!record2.containsLabel(number));
        
        assertTrue(record.containsLabel(number1));
        assertTrue(record1.containsLabel(number1));
        assertTrue(record2.containsLabel(number1));
    }
}
