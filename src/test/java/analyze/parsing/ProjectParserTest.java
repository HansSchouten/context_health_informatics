package analyze.parsing;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import model.Record;
import model.SequentialData;
import model.datafield.DataFieldBoolean;
import model.datafield.DataFieldInt;

import org.junit.Test;

import analyze.AnalyzeException;

public class ProjectParserTest {

    @Test
    public void patternMatchTest() {
        ProjectParser pp = new ProjectParser();
        assertFalse(pp.pattern.matcher("c").matches());
        assertFalse(pp.pattern.matcher("col hoi").matches());
        assertTrue(pp.pattern.matcher("col(hoi)").matches());
        assertTrue(pp.pattern.matcher("COL(hoi)").matches());
    }

    @Test (expected = ProjectException.class)
    public void projectTest() throws AnalyzeException {
        ProjectParser pp = new ProjectParser();
        pp.parseOperation("col(hoi)", (SequentialData) null);
    }
    
    @Test (expected = ProjectException.class)
    public void project1Test() throws AnalyzeException {
        ProjectParser pp = new ProjectParser();
        pp.parseOperation("col(hoi)", new DataFieldInt(10));
    }
    
    @Test
    public void project2Test() throws AnalyzeException {
        ProjectParser pp = new ProjectParser();
        SequentialData list = new SequentialData();
        Record record = new Record(LocalDateTime.ofEpochSecond(1430909359, 0, ZoneOffset.UTC));
        record.put("hoi", new DataFieldBoolean(false));
        list.add(record);
        Record record1 = new Record(LocalDateTime.ofEpochSecond(1430909359, 0, ZoneOffset.UTC));
        Record record2 = new Record(LocalDateTime.ofEpochSecond(1430909359, 0, ZoneOffset.UTC));
        record2.put("hoi", new DataFieldInt(20));
        list.add(record);
        list.add(record1);
        list.add(record2);
        pp.parseOperation("col(hoi)", list);
        assertFalse(record.containsKey("hoi"));
        assertFalse(record1.containsKey("hoi"));
        assertFalse(record2.containsKey("hoi"));
    }
    
    @Test
    public void project3Test() throws AnalyzeException {
        ProjectParser pp = new ProjectParser();
        SequentialData list = new SequentialData();
        Record record = new Record(LocalDateTime.ofEpochSecond(1430909359, 0, ZoneOffset.UTC));
        record.put("hoi", new DataFieldBoolean(false));
        record.put("doei", new DataFieldBoolean(false));
        list.add(record);
        Record record1 = new Record(LocalDateTime.ofEpochSecond(1430909359, 0, ZoneOffset.UTC));
        record.put("doei", new DataFieldBoolean(false));
        Record record2 = new Record(LocalDateTime.ofEpochSecond(1430909359, 0, ZoneOffset.UTC));
        record2.put("hoi", new DataFieldInt(20));
        list.add(record1);
        list.add(record2);
        pp.parseOperation("col(hoi),col(doei)", list);
        assertFalse(record.containsKey("hoi"));
        assertFalse(record1.containsKey("hoi"));
        assertFalse(record2.containsKey("hoi"));
        
        assertFalse(record.containsKey("doei"));
        assertFalse(record1.containsKey("doei"));
        assertFalse(record2.containsKey("doei"));
    }
    
    @Test
    public void project4Test() throws AnalyzeException {
        ProjectParser pp = new ProjectParser();
        SequentialData list = new SequentialData();
        Record record = new Record(LocalDateTime.ofEpochSecond(1430909359, 0, ZoneOffset.UTC));
        list.add(record);
        Record record1 = new Record(LocalDateTime.ofEpochSecond(1430909359, 0, ZoneOffset.UTC));
        Record record2 = new Record(LocalDateTime.ofEpochSecond(1430909359, 0, ZoneOffset.UTC));
        list.add(record);
        list.add(record1);
        list.add(record2);
        pp.parseOperation("col(hoi),col(doei)", list);
    }
    
    @Test (expected = ProjectException.class)
    public void project5Test() throws AnalyzeException {
        ProjectParser pp = new ProjectParser();
        SequentialData list = new SequentialData();
        Record record = new Record(LocalDateTime.ofEpochSecond(1430909359, 0, ZoneOffset.UTC));
        list.add(record);
        Record record1 = new Record(LocalDateTime.ofEpochSecond(1430909359, 0, ZoneOffset.UTC));
        Record record2 = new Record(LocalDateTime.ofEpochSecond(1430909359, 0, ZoneOffset.UTC));
        list.add(record);
        list.add(record1);
        list.add(record2);
        pp.parseOperation("cls(hoi)", list);
    }
    
    @Test
    public void project6Test() throws Exception {
        Parser pp = new Parser();
        SequentialData list = new SequentialData();
        Record record = new Record(LocalDateTime.ofEpochSecond(1430909359, 0, ZoneOffset.UTC));
        record.put("hoi", new DataFieldBoolean(false));
        record.put("doei", new DataFieldBoolean(false));
        list.add(record);
        Record record1 = new Record(LocalDateTime.ofEpochSecond(1430909359, 0, ZoneOffset.UTC));
        record.put("doei", new DataFieldBoolean(false));
        Record record2 = new Record(LocalDateTime.ofEpochSecond(1430909359, 0, ZoneOffset.UTC));
        record2.put("hoi", new DataFieldInt(20));
        list.add(record1);
        list.add(record2);
        pp.parse("exclude col(hoi), col(doei)", list);
        assertFalse(record.containsKey("hoi"));
        assertFalse(record1.containsKey("hoi"));
        assertFalse(record2.containsKey("hoi"));
        
        assertFalse(record.containsKey("doei"));
        assertFalse(record1.containsKey("doei"));
        assertFalse(record2.containsKey("doei"));
    }
}
