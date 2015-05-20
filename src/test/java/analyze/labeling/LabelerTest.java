package analyze.labeling;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import model.DataFieldBoolean;
import model.DataFieldInt;
import model.Record;
import model.SequentialData;

import org.junit.Test;

import analyze.AnalyzeException;
import analyze.EmptyDataSetException;

public class LabelerTest {

    @Test (expected = EmptyDataSetException.class)
    public void emptyDataSetTest() throws AnalyzeException {
        Labeler labeler = new Labeler();
        //SequentialData list = new SequentialData();
        labeler.label("hoi", "true", null);
    }
    
    @Test (expected = LabelingException.class)
    public void emptyLabelTest() throws AnalyzeException {
        Labeler labeler = new Labeler();
        SequentialData list = new SequentialData();
        labeler.label("", "true", list);
    }
    
    @Test (expected = LabelingException.class)
    public void emptyLabel1Test() throws AnalyzeException {
        Labeler labeler = new Labeler();
        SequentialData list = new SequentialData();
        labeler.label(null, "true", list);
    }
    
    @Test
    public void labelRecordTest() throws AnalyzeException {
        Labeler labeler = new Labeler();
        SequentialData list = new SequentialData();
        Record record = new Record(LocalDateTime.ofEpochSecond(1430909359, 0, ZoneOffset.UTC));
        list.add(record);
        labeler.label("labelTest", "true", list);
        int number = LabelFactory.getInstance().getNumberOfLabel("labelTest");
        assertTrue(record.containsLabel(number));
    }
    
    @Test
    public void labelRecord1Test() throws AnalyzeException {
        Labeler labeler = new Labeler();
        SequentialData list = new SequentialData();
        Record record = new Record(LocalDateTime.ofEpochSecond(1430909359, 0, ZoneOffset.UTC));
        list.add(record);
        labeler.label("labelTest", "false", list);
        int number = LabelFactory.getInstance().getNumberOfLabel("labelTest");
        assertTrue(!record.containsLabel(number));
    }
    
    @Test
    public void labelRecord2Test() throws AnalyzeException {
        Labeler labeler = new Labeler();
        SequentialData list = new SequentialData();
        Record record = new Record(LocalDateTime.ofEpochSecond(1430909359, 0, ZoneOffset.UTC));
        record.put("hoi", new DataFieldInt(10));
        list.add(record);
        labeler.label("labelTest", "COL(hoi) = 10", list);
        int number = LabelFactory.getInstance().getNumberOfLabel("labelTest");
        assertTrue(record.containsLabel(number));
    }
    
    @Test
    public void labelRecord3Test() throws AnalyzeException {
        Labeler labeler = new Labeler();
        SequentialData list = new SequentialData();
        Record record = new Record(LocalDateTime.ofEpochSecond(1430909359, 0, ZoneOffset.UTC));
        list.add(record);
        labeler.label("labelTest", "COL(hoi) = 10", list);
        int number = LabelFactory.getInstance().getNumberOfLabel("labelTest");
        assertTrue(!record.containsLabel(number));
    }
    
    @Test
    public void labelRecord4Test() throws AnalyzeException {
        Labeler labeler = new Labeler();
        SequentialData list = new SequentialData();
        Record record = new Record(LocalDateTime.ofEpochSecond(1430909359, 0, ZoneOffset.UTC));
        record.put("hoi", new DataFieldInt(5));
        list.add(record);
        Record record1 = new Record(LocalDateTime.ofEpochSecond(1430909359, 0, ZoneOffset.UTC));
        Record record2 = new Record(LocalDateTime.ofEpochSecond(1430909359, 0, ZoneOffset.UTC));
        record2.put("hoi", new DataFieldInt(20));
        list.add(record1);
        list.add(record2);
        labeler.label("labelTest", "COL(hoi) + 10 = 15", list);
        int number = LabelFactory.getInstance().getNumberOfLabel("labelTest");
        assertTrue(record.containsLabel(number));
        assertTrue(!record1.containsLabel(number));
        assertTrue(!record2.containsLabel(number));
    }
    
    @Test
    public void labelRecord5Test() throws AnalyzeException {
        Labeler labeler = new Labeler();
        SequentialData list = new SequentialData();
        Record record = new Record(LocalDateTime.ofEpochSecond(1430909359, 0, ZoneOffset.UTC));
        record.put("hoi", new DataFieldBoolean(false));
        list.add(record);
        Record record1 = new Record(LocalDateTime.ofEpochSecond(1430909359, 0, ZoneOffset.UTC));
        Record record2 = new Record(LocalDateTime.ofEpochSecond(1430909359, 0, ZoneOffset.UTC));
        record2.put("hoi", new DataFieldInt(20));
        list.add(record1);
        list.add(record2);
        labeler.label("labelTest", "COL(hoi) + 10 = 15", list);
        int number = LabelFactory.getInstance().getNumberOfLabel("labelTest");
        assertTrue(!record.containsLabel(number));
        assertTrue(!record1.containsLabel(number));
        assertTrue(!record2.containsLabel(number));
    }
    
    @Test
    public void labelRecord6Test() throws AnalyzeException {
        Labeler labeler = new Labeler();
        SequentialData list = new SequentialData();
        Record record = new Record(LocalDateTime.ofEpochSecond(1430909359, 0, ZoneOffset.UTC));
        record.put("hoi", new DataFieldInt(0));
        list.add(record);
        Record record1 = new Record(LocalDateTime.ofEpochSecond(1430909359, 0, ZoneOffset.UTC));
        Record record2 = new Record(LocalDateTime.ofEpochSecond(1430909359, 0, ZoneOffset.UTC));
        record2.put("hoi", new DataFieldInt(20));
        list.add(record1);
        list.add(record2);
        
        labeler.label("labelTest", "COL(hoi) + 10 = 10", list);
        int number = LabelFactory.getInstance().getNumberOfLabel("labelTest");
        
        assertTrue(record.containsLabel(number));
        assertTrue(!record1.containsLabel(number));
        assertTrue(!record2.containsLabel(number));
        
        labeler.label("labelTest", "(COL(hoi) = 20) = true", list);

        assertTrue(record.containsLabel(number));
        assertTrue(!record1.containsLabel(number));
        assertTrue(record2.containsLabel(number));
    }
    
    @Test
    public void labelRecord7Test() throws AnalyzeException {
        Labeler labeler = new Labeler();
        SequentialData list = new SequentialData();
        Record record = new Record(LocalDateTime.ofEpochSecond(1430909359, 0, ZoneOffset.UTC));
        list.add(record);
        Record record1 = new Record(LocalDateTime.ofEpochSecond(1430909359, 0, ZoneOffset.UTC));
        Record record2 = new Record(LocalDateTime.ofEpochSecond(1430909359, 0, ZoneOffset.UTC));
        record2.put("hoi", new DataFieldInt(20));
        list.add(record1);
        list.add(record2);
        labeler.label("labelTest", "COL(hoi) = 20", list);
        int number = LabelFactory.getInstance().getNumberOfLabel("labelTest");
        assertTrue(!record.containsLabel(number));
        assertTrue(!record1.containsLabel(number));
        assertTrue(record2.containsLabel(number));
        
        labeler.label("labelTest1", "LABELED(labelTest)", list);
        int number1 = LabelFactory.getInstance().getNumberOfLabel("labelTest1");
        
        assertTrue(!record.containsLabel(number1));
        assertTrue(!record1.containsLabel(number1));
        assertTrue(record2.containsLabel(number1));
    }
}
