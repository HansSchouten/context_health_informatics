package model;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class ReaderTest {
	
	String[] columns = {"column1", "column2", "column3"};
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
	public void testReadIgnoreColumn() throws IOException {
		String[] columns = {"column1", "column2"};
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

}
