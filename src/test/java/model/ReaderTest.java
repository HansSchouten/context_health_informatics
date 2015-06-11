package model;

import static org.junit.Assert.*;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Arrays;

import model.datafield.DataField;
import model.datafield.DataFieldDouble;
import model.datafield.DataFieldInt;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ReaderTest {
    
    Column[] columns = 
        {new Column("column1", ColumnType.STRING), new Column("column2", ColumnType.STRING), new Column("column3", ColumnType.STRING)};
    String delimiter = ",";
    
    @Test
    public void testConstructor() {
        Reader reader = new Reader(columns, delimiter);
        assertNotEquals(reader, null);
    }
    
    @Test
    public void testRead() throws IOException {
        Reader reader = new Reader(columns, delimiter);
        RecordList recordList = reader.read("src/main/resources/test_input.txt", false);

        // test number of records
        assertEquals(2, recordList.size());
        // test number of columns
        assertEquals(3, recordList.get(0).size());
    }

    @Test(expected = IOException.class)
    public void testReadInvalidPath() throws IOException {
        Reader reader = new Reader(columns, delimiter);
        reader.read("src/main/resources/unknown_file.txt", false);
    }
    
    @Test
    public void testReadComment() throws IOException {
        Column column3 = new Column("column3", ColumnType.COMMENT);
        Column[] columns = 
            {new Column("column1", ColumnType.STRING), new Column("column2", ColumnType.STRING), column3};
        Reader reader = new Reader(columns, delimiter);
        RecordList recordList = reader.read("src/main/resources/test_input_comment.txt", false);
        
        // test number of records
        assertEquals(1, recordList.size());
        // test number of columns
        assertEquals(2, recordList.get(0).size());
        assertEquals("3;", recordList.get(0).printComments(";"));
        
    }
    
    @Test
    public void testReadComment1() throws IOException {
        Column column3 = new Column("column3", ColumnType.COMMENT);
        Column column2 = new Column("column2", ColumnType.COMMENT);
        Column[] columns = 
            {new Column("column1", ColumnType.STRING), column2, column3};
        Reader reader = new Reader(columns, delimiter);
        RecordList recordList = reader.read("src/main/resources/test_input_comment.txt", false);
        
        // test number of records
        assertEquals(1, recordList.size());
        // test number of columns
        assertEquals(1, recordList.get(0).size());
        assertEquals("2;3;", recordList.get(0).printComments(";"));
        
    }
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    @Test
    public void testReadIgnoreColumn() throws IOException {
        Column[] columns = 
            {new Column("column1", ColumnType.STRING), new Column("column2", ColumnType.STRING)};
        Reader reader = new Reader(columns, delimiter);
        RecordList recordList = reader.read("src/main/resources/test_input.txt", false);
        
        // test number of records
        assertEquals(2, recordList.size());
        // test number of columns
        assertEquals(2, recordList.get(0).size());
    }
    
    
    @Test
    public void testReadColumnNames() throws IOException {
        Reader reader = new Reader(columns, delimiter);
        RecordList recordList = reader.read("src/main/resources/test_columnnames.txt", true);
        
        // test number of records
        assertEquals(1, recordList.size());
        // test number of columns
        assertEquals(3, recordList.get(0).size());
        
        assertEquals("columnc", columns[2].getName());
    }


    @Test
    public void testReadMetaData() throws IOException {
        Reader reader = new Reader(columns, delimiter);
        RecordList recordList = reader.read("src/main/resources/test_input_metadata.txt", false);
        
        assertEquals("metadata",recordList.getProperty("metadata"));
    }

    @Test
    public void setCharacteristicTest() {
        Column column = new Column("test", ColumnType.STRING);
        column.setType(ColumnType.COMMENT);
        assertEquals(ColumnType.COMMENT, column.characteristic);
    }
    
   @Test
    public void convertToInt() {
        Reader reader = new Reader(columns, delimiter);
        DataField rf = reader.createIntegerField("10");
        assertEquals(rf.toString(), "10");
        assertTrue(rf instanceof DataFieldInt);
    }
   
   @Test (expected = NumberFormatException.class)
   public void convertToIntWrong() {
       Reader reader = new Reader(columns, delimiter);
       reader.createIntegerField("hoi");
   }
   
   @Test
   public void convertToFloat() {
       Reader reader = new Reader(columns, delimiter);
       DataField rf = reader.createDoubleField("10.0");
       assertEquals(rf.toString(), "10.0");
       assertTrue(rf instanceof DataFieldDouble);
   }
   
   @Test
   public void convertToFloat1() {
       Reader reader = new Reader(columns, delimiter);
       DataField rf = reader.createDoubleField("10");
       assertEquals(rf.toString(), "10.0");
       assertTrue(rf instanceof DataFieldDouble);
   }
  
  @Test (expected = NumberFormatException.class)
  public void convertToFloatWrong() {
      Reader reader = new Reader(columns, delimiter);
      reader.createDoubleField("hoi");
  }
  
  @Test
  public void ReaderTestInt1() throws ParseException {
      columns[0].setType(ColumnType.INT);
      Reader reader = new Reader(columns, delimiter);
      Record red = reader.createRecord("10,hoi,doei");
      assertTrue(red.get("column1") instanceof DataFieldInt);
      assertEquals(red.get("column1").toString(), "10");
  }
  
  @Test (expected = NumberFormatException.class)
  public void ReaderTestIntWrong() throws ParseException {
      columns[0].setType(ColumnType.INT);
      Reader reader = new Reader(columns, delimiter);
      reader.createRecord("hoi,hoi,doei");
  }
  
  @Test
  public void ReaderTestFloat1() throws ParseException {
      columns[0].setType(ColumnType.DOUBLE);
      Reader reader = new Reader(columns, delimiter);
      Record red = reader.createRecord("10,hoi,doei");
      assertTrue(red.get("column1") instanceof DataFieldDouble);
      assertEquals(red.get("column1").toString(), "10.0");
  }
  
  @Test (expected = NumberFormatException.class)
  public void ReaderTestfloatWrong() throws ParseException {
      columns[0].setType(ColumnType.DOUBLE);
      Reader reader = new Reader(columns, delimiter);
      reader.createRecord("hoi,hoi,doei");
  }
  
  @Test 
  public void readerTestSortTimeStamp() throws ParseException {
      columns[0] = new DateColumn("datum", ColumnType.DATE, "yyMMdd", true);
      Reader reader = new Reader(columns, delimiter);
      Record rec = reader.createRecord("150515,test,test");
      assertEquals(new Record(DateUtils.parseDate("150515", "yyMMdd")).getTimeStamp(), rec.getTimeStamp());
  }
  
  @Test
  public void getSortTimeStampTest() throws ParseException {
      columns[0].setType(ColumnType.DOUBLE);
      columns[1] = new DateColumn("datum", ColumnType.DATE, "yyMMdd", true);
      columns[2] = new DateColumn("tijd", ColumnType.DATE, "HHmm", true);
      columns[2].setType(ColumnType.TIME);
      Reader reader = new Reader(columns, delimiter);
      assertEquals(reader.createRecord("15.0,150515,1224").getTimeStamp(),
              LocalDateTime.of(2015, 05, 15, 12, 24));
  }
  
  @Test
  public void getSortTimeStampTest2() throws ParseException {
      columns[0].setType(ColumnType.DOUBLE);
      columns[1] = new DateColumn("tijd", ColumnType.TIME, "HHmm", true);
      columns[2] = new DateColumn("datum", ColumnType.DATE, "yyMMdd", true);
      Reader reader = new Reader(columns, delimiter);
      assertEquals(reader.createRecord("15.0,1224,150515").getTimeStamp(),
              LocalDateTime.of(2015, 05, 15, 12, 24));
  }
  
  @Test
  public void getSortTimeStampExcelEpochTest() throws ParseException {
      columns[0] = new DateColumn("datum", ColumnType.DATEandTIME, "Excel epoch", true);
      Reader reader = new Reader(columns, delimiter);
      assertEquals(reader.createRecord("42137.5,test,test").getTimeStamp(),
              LocalDateTime.of(2015, 05, 15, 12, 00));
  }
  
  @Test
  public void getSortTimeStampDateandTimeTest() throws ParseException {
      columns[0] = new DateColumn("datum", ColumnType.DATEandTIME, "dd-MM-yyyy  HH:mm:ss", true);
      Reader reader = new Reader(columns, delimiter);
      assertEquals(reader.createRecord("15-05-2015  12:45:00,test,test").getTimeStamp(),
              LocalDateTime.of(2015, 05, 15, 12, 45));
  }

  @Test
  public void getSortTimeStampNoSortTest() throws ParseException {
      columns[0] = new DateColumn("datum", ColumnType.DATEandTIME, "dd-MM-yyyy  HH:mm:ss", false);
      columns[1] = new DateColumn("datum", ColumnType.DATEandTIME, "dd-MM-yyyy  HH:mm:ss", true);

      Reader reader = new Reader(columns, delimiter);
      assertEquals(reader.createRecord("15-05-2015  12:45:00,15-05-2015  12:46:00,test").getTimeStamp(),
              LocalDateTime.of(2015, 05, 15, 12, 46));
  }

  @Test
  public void excludedTest() throws ParseException {
      columns[0] = new DateColumn("datum", ColumnType.DATEandTIME, "Excel epoch", true);
      columns[1].setExcluded();
      Reader reader = new Reader(columns, delimiter);
      Record record = reader.createRecord("42137.5,1,2");

      assertEquals(2, record.keySet().size());
      assertFalse(record.containsKey("column2"));
      assertTrue(record.containsKey("column3"));
  }
  
  @Test
  public void integerValueIsNotDefinedTest() throws ParseException {
      columns[0] = new Column("column1", ColumnType.INT);
      Reader reader = new Reader(columns, delimiter);
      Record record = reader.createRecord(",test,test2");
      assertEquals(record.size(), 2);
  }
  
  @Test
  public void dateValueIsNotDefinedTest() throws ParseException {
      columns[0] = new DateColumn("column1", ColumnType.DATEandTIME, "Excel epoch", false);
      Reader reader = new Reader(columns, delimiter);
      Record record = reader.createRecord(",test,test2");
      assertEquals(record.size(), 2);
  }

}