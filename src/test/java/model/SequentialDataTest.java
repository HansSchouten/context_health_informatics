package model;

import static org.junit.Assert.*;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Iterator;

import model.datafield.DataFieldString;

import org.junit.Before;
import org.junit.Test;

public class SequentialDataTest {

    Record record1, record2;
    SequentialData sq, userData;
    Column[] columns = 
        {new Column("column1", ColumnType.STRING), new Column("column2", ColumnType.STRING), new Column("column3", ColumnType.STRING), new Column("column4", ColumnType.STRING)};
    
    @Before
    public void setUp() throws Exception {
        record1 = new Record(LocalDateTime.ofEpochSecond(1430909359, 0, ZoneOffset.UTC));
        record2 = new Record(LocalDateTime.ofEpochSecond(1430909365, 0, ZoneOffset.UTC));
        sq = new SequentialData();
        sq.add(record1);
        sq.add(record2);
        
        columns[0].setType(ColumnType.DOUBLE);
        columns[1] = new DateColumn("datum", ColumnType.DATE, "yyMMdd", true);
        columns[2] = new DateColumn("tijd", ColumnType.TIME, "HHmm", true);
        columns[3].setType(ColumnType.STRING);
        
        userData = new SequentialData();
        
        Reader reader = new Reader(columns, ",");
        RecordList recordList = reader.read("src/main/resources/testfiles/test_input_writer.txt", false);
        
        userData.addRecordList(recordList);
    }

    @Test
    public void testSequential1() {
        assertEquals(sq.iterator().next().getTimeStamp(), record1.getTimeStamp());
    }
    
    @Test
    public void testSequential2() {
        Iterator<Record> iterator = sq.iterator();
        iterator.next();
        assertEquals(iterator.next().getTimeStamp(), record2.getTimeStamp());
    }
    
    @Test
    public void testSequentialNegative() {
        Iterator<Record> iterator = sq.iterator();
        iterator.next();
        assertNotEquals(iterator.next().getTimeStamp(), record1.getTimeStamp());
    }
    
    /**
     * This test tests concatenating adding a RecordList to a sequential datastructure.
     */
    @Test
    public void testConcatToSequentialDataStructures() {
        Record record3 = new Record(LocalDateTime.ofEpochSecond(1430909362, 0, ZoneOffset.UTC));
        Column[] cl = {new Column("test", ColumnType.STRING)};
        RecordList recordList = new RecordList(cl);
        recordList.add(record3);
        
        sq.addRecordList(recordList);
        Iterator<Record> iterator = sq.iterator();
        iterator.next();
        assertEquals(iterator.next().getTimeStamp(), record3.getTimeStamp());
    }
    
    /**
     * Test writer with column names included
     * @throws IOException
     * @throws ParseException 
     */
    @Test
    public void testColumnNames() throws IOException {
        String content = userData.toString(",", true);
        assertEquals("column1,datum,tijd,column4,Comments,Labels\r\n"
                   + "17.0,2012-05-15,18:25,person1,,\r\n"
                   + "15.0,2015-05-15,12:24,person1,,\r\n"
                   + "10.0,2020-05-15,14:24,person2,,\r\n", content);
    }
    
    /**
     * Test writer with full filename (extension included)
     * @throws IOException
     * @throws ParseException 
     */
    @Test
    public void testFullFileName() throws IOException {
        String content = userData.toString(",", false);
        assertEquals("17.0,2012-05-15,18:25,person1,,\r\n"
                    + "15.0,2015-05-15,12:24,person1,,\r\n"
                    + "10.0,2020-05-15,14:24,person2,,\r\n", content);
    }
    
    /**
     * Test writer with empty data
     * This should throw a no such file exception since the output will not be written to file
     * @throws IOException
     * @throws ParseException 
     */
    @Test
    public void testWriterEmptyData() throws IOException {
        SequentialData empty = new SequentialData();
        String content = empty.toString(",", true);
        assertEquals(content, "");
    }
    
    /**
     * Test writer with semicolon as delimiter
     * @throws IOException
     */
    @Test
    public void testToString2() throws IOException {
        
        String out = userData.toString(",", false);
        
        Reader reader2 = new Reader(columns, ",");
        RecordList recordList2 = reader2.read("src/main/resources/testfiles/test_output_writer2.txt", false);
        
        SequentialData userData3 = new SequentialData();
        userData3.addRecordList(recordList2);
        
        String out2 = userData3.toString(",", false);
        
        assertEquals(out, out2);
    }
    
    @Test
    public void testCopyReference() {
        DataFieldString string = new DataFieldString("Test");
        Record r = new Record(LocalDateTime.now());
        r.put("test", string);
        SequentialData sd = new SequentialData();
        sd.add(r);
        SequentialData copy = sd.copy();

        assertNotEquals(sd, copy);
    }

    @Test
    public void testCopyRecord() {
        DataFieldString string = new DataFieldString("Test");
        Record r = new Record(LocalDateTime.now());
        r.put("test", string);
        SequentialData sd = new SequentialData();
        sd.add(r);

        SequentialData copy = sd.copy();

        assertFalse(sd.first() == copy.first());
    }

    @Test
    public void testCopyData() {
        DataFieldString string = new DataFieldString("Test");
        Record r = new Record(LocalDateTime.now());
        r.put("test", string);
        SequentialData sd = new SequentialData();
        sd.add(r);

        SequentialData copy = sd.copy();

        assertEquals(sd.first().get("test").toString(), copy.first().get("test").toString());
    }
}