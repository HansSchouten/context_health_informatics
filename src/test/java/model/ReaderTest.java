package model;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class ReaderTest {
	
	Column[] columns = 
		{new Column("column1", ColumnCharacteristics.NONE), new Column("column2", ColumnCharacteristics.NONE), new Column("column3", ColumnCharacteristics.NONE)};
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
	
	@Test
	public void testReadComment() throws IOException {
		Column[] columns = 
			{new Column("column1", ColumnCharacteristics.NONE), new Column("column2", ColumnCharacteristics.NONE), new Column("column3", ColumnCharacteristics.COMMENT)};
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
		Column[] columns = 
			{new Column("column1", ColumnCharacteristics.NONE), new Column("column2", ColumnCharacteristics.COMMENT), new Column("column3", ColumnCharacteristics.COMMENT)};
		Reader reader = new Reader(columns, delimiter);
		RecordList recordList = reader.read("src/main/resources/test_input_comment.txt");
		
		// test number of records
		assertEquals(1, recordList.size());
		// test number of columns
		assertEquals(1, recordList.get(0).size());
		assertEquals("2;3;", recordList.get(0).printComments(";"));
		
	}

	@Test(expected = IOException.class)
	public void testReadInvalidPath() throws IOException {
		Reader reader = new Reader(columns, delimiter);
		reader.read("src/main/resources/unknown_file.txt");
	}

	@Test
	public void testReadIgnoreColumn() throws IOException {
		Column[] columns = 
			{new Column("column1", ColumnCharacteristics.NONE), new Column("column2", ColumnCharacteristics.NONE)};
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
		Column column = new Column("test", ColumnCharacteristics.NONE);
		column.setCharactersitic(ColumnCharacteristics.COMMENT);
		assertEquals(ColumnCharacteristics.COMMENT, column.characteristic);
	}

}
