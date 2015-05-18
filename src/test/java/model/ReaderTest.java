package model;

import static org.junit.Assert.*;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;

import org.junit.Test;

public class ReaderTest {
	
	Column[] columns = 
		{new Column("column1"), new Column("column2"), new Column("column3")};
	String delimiter = ",";
	
	@Test
	public void testConstructor() {
		Reader reader = new Reader(columns, delimiter);
		assertNotEquals(reader, null);
	}
	
	@Test
	public void testRead() throws IOException {
		Reader reader = new Reader(columns, delimiter);
		RecordList recordList = reader.read("src/main/resources/test_input.txt");

		// test number of records
		assertEquals(2, recordList.size());
		// test number of columns
		assertEquals(3, recordList.get(0).size());
	}

	@Test(expected = IOException.class)
	public void testReadInvalidPath() throws IOException {
		Reader reader = new Reader(columns, delimiter);
		reader.read("src/main/resources/unknown_file.txt");
	}
	
	@Test
	public void testReadComment() throws IOException {
		Column column3 = new Column("column3");
		column3.setType(ColumnType.COMMENT);
		Column[] columns = 
			{new Column("column1"), new Column("column2"), column3};
		Reader reader = new Reader(columns, delimiter);
		RecordList recordList = reader.read("src/main/resources/test_input_comment.txt");
		
		// test number of records
		assertEquals(1, recordList.size());
		// test number of columns
		assertEquals(2, recordList.get(0).size());
		assertEquals("3;", recordList.get(0).printComments(";"));
		
	}
	
	@Test
	public void testReadComment1() throws IOException {
		Column column3 = new Column("column3");
		column3.setType(ColumnType.COMMENT);
		Column column2 = new Column("column2");
		column2.setType(ColumnType.COMMENT);
		Column[] columns = 
			{new Column("column1"), column2, column3};
		Reader reader = new Reader(columns, delimiter);
		RecordList recordList = reader.read("src/main/resources/test_input_comment.txt");
		
		// test number of records
		assertEquals(1, recordList.size());
		// test number of columns
		assertEquals(1, recordList.get(0).size());
		assertEquals("2;3;", recordList.get(0).printComments(";"));
		
	}

	@Test
	public void testReadIgnoreColumn() throws IOException {
		Column[] columns = 
			{new Column("column1"), new Column("column2")};
		Reader reader = new Reader(columns, delimiter);
		RecordList recordList = reader.read("src/main/resources/test_input.txt");
		
		// test number of records
		assertEquals(2, recordList.size());
		// test number of columns
		assertEquals(2, recordList.get(0).size());
	}

	@Test
	public void testReadMetaData() throws IOException {
		Reader reader = new Reader(columns, delimiter);
		RecordList recordList = reader.read("src/main/resources/test_input_metadata.txt");
		
		assertEquals("metadata",recordList.getProperty("metadata"));
	}

	@Test
	public void setCharacteristicTest() {
		Column column = new Column("test");
		column.setType(ColumnType.COMMENT);
		assertEquals(ColumnType.COMMENT, column.characteristic);
	}
	
   @Test
    public void convertToInt() {
        Reader reader = new Reader(columns, delimiter);
        RecordField rf = reader.createIntegerField("10");
        assertEquals(rf.toString(), "10");
        assertTrue(rf instanceof RecordFieldInt);
    }
   
   @Test (expected = NumberFormatException.class)
   public void convertToIntWrong() {
       Reader reader = new Reader(columns, delimiter);
       reader.createIntegerField("hoi");
   }
   
   @Test
   public void convertToFloat() {
       Reader reader = new Reader(columns, delimiter);
       RecordField rf = reader.createDoubleField("10.0");
       assertEquals(rf.toString(), "10.0");
       assertTrue(rf instanceof RecordFieldDouble);
   }
   
   @Test
   public void convertToFloat1() {
       Reader reader = new Reader(columns, delimiter);
       RecordField rf = reader.createDoubleField("10");
       assertEquals(rf.toString(), "10.0");
       assertTrue(rf instanceof RecordFieldDouble);
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
      assertTrue(red.get("column1") instanceof RecordFieldInt);
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
      assertTrue(red.get("column1") instanceof RecordFieldDouble);
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
      columns[0] = new DateColumn("datum", "yyMMdd", true);
      columns[0].setType(ColumnType.DATE);
      Reader reader = new Reader(columns, delimiter);
      Record rec = reader.createRecord("150515,test,test");
      assertEquals(new Record(DateUtils.parseDate("150515", "yyMMdd")).getTimeStamp(), rec.getTimeStamp());
  }
  
  @Test
  public void getSortTimeStampTest() throws ParseException {
	  columns[0].setType(ColumnType.DOUBLE);
	  columns[1] = new DateColumn("datum", "yyMMdd", true);
      columns[1].setType(ColumnType.DATE);
      columns[2] = new DateColumn("tijd", "HHmm", true);
      columns[2].setType(ColumnType.TIME);
      Reader reader = new Reader(columns, delimiter);
      assertEquals(reader.createRecord("15.0,150515,1224").getTimeStamp(),
    		  LocalDateTime.of(2015, 05, 15, 12, 24));
  }
  
  @Test
  public void getSortTimeStampTest2() throws ParseException {
	  columns[0].setType(ColumnType.DOUBLE);
	  columns[1] = new DateColumn("tijd", "HHmm", true);
      columns[1].setType(ColumnType.TIME);
	  columns[2] = new DateColumn("datum", "yyMMdd", true);
      columns[2].setType(ColumnType.DATE);
      Reader reader = new Reader(columns, delimiter);
      assertEquals(reader.createRecord("15.0,1224,150515").getTimeStamp(),
    		  LocalDateTime.of(2015, 05, 15, 12, 24));
  }
  
  @Test
  public void getSortTimeStampExcelEpochTest() throws ParseException {
	  columns[0] = new DateColumn("datum", "Excel epoch", true);
      columns[0].setType(ColumnType.DATEandTIME);
      Reader reader = new Reader(columns, delimiter);
      assertEquals(reader.createRecord("42137.5,test,test").getTimeStamp(),
    		  LocalDateTime.of(2015, 05, 15, 12, 00));
  }
  
  @Test
  public void getSortTimeStampDateandTimeTest() throws ParseException {
	  columns[0] = new DateColumn("datum", "dd-MM-yyyy  HH:mm:ss", true);
      columns[0].setType(ColumnType.DATEandTIME);
      Reader reader = new Reader(columns, delimiter);
      assertEquals(reader.createRecord("15-05-2015  12:45:00,test,test").getTimeStamp(),
    		  LocalDateTime.of(2015, 05, 15, 12, 45));
  }

  @Test
  public void getSortTimeStampNoSortTest() throws ParseException {
	  columns[0] = new DateColumn("datum", "dd-MM-yyyy  HH:mm:ss", false);
      columns[0].setType(ColumnType.DATEandTIME);
	  columns[1] = new DateColumn("datum", "dd-MM-yyyy  HH:mm:ss", true);
      columns[1].setType(ColumnType.DATEandTIME);

      Reader reader = new Reader(columns, delimiter);
      assertEquals(reader.createRecord("15-05-2015  12:45:00,15-05-2015  12:46:00,test").getTimeStamp(),
    		  LocalDateTime.of(2015, 05, 15, 12, 46));
  }

}
