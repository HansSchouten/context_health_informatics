package model;

import static org.junit.Assert.*;

import java.io.IOException;

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
/*
	@Test
	public void testReadMetaData() throws IOException {
		Reader reader = new Reader(columns, delimiter);
		RecordList recordList = reader.read("src/main/resources/test_input_metadata.txt");
		
		assertEquals("metadata",recordList.getProperty("metadata"));
	}
	*/
	@Test
	public void setCharacteristicTest() {
		Column column = new Column("test");
		column.setType(ColumnType.COMMENT);
		assertEquals(ColumnType.COMMENT, column.characteristic);
	}

}
