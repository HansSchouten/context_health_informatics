package model;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class WriterTest {

	Column[] columns = 
		{new Column("column1"), new Column("column2"), new Column("column3")};
	String delimiter = ",";
	
	/**
	 * Test writer with comma as delimiter
	 * @throws IOException
	 */
	@Test
	public void testToString() throws IOException {
		Reader reader = new Reader(columns, delimiter);
		RecordList recordList = reader.read("src/main/resources/test_input.txt", false);
		
		String out = recordList.toString(delimiter);
		// test number of records
		assertEquals(2, recordList.size());
		// test number of columns
		assertEquals(3, recordList.get(0).size());
		// test the content of the string
		assertEquals("column1,column2,column3\n 1,2,3\n 4,5,6\n ", out);
	}
	
	/**
	 * Test writer with semicolon as delimiter
	 * @throws IOException
	 */
	@Test
	public void testToString2() throws IOException {
		String delimiter2 = ";";
		Reader reader = new Reader(columns, delimiter);
		RecordList recordList = reader.read("src/main/resources/test_input.txt", false);
		
		String out = recordList.toString(delimiter2);
		// test number of records
		assertEquals(2, recordList.size());
		// test number of columns
		assertEquals(3, recordList.get(0).size());
		// test the content of the string
		assertEquals("column1;column2;column3\n 1;2;3\n 4;5;6\n ", out);
	}
	
	/**
	 * Test if it is actually written to the right file
	 * @throws IOException
	 */
	@Test
	public void testWrite() throws IOException {
		Reader reader = new Reader(columns, delimiter);
		RecordList recordList = reader.read("src/main/resources/test_input.txt", false);
		
		Writer writer = new Writer(delimiter);
		writer.writeData(recordList, "src/main/resources/test_output", ".csv" );
		RecordList recordList2 = reader.read("src/main/resources/test_input.txt", false);
		
		String out = recordList2.toString(delimiter);
		assertEquals("column1,column2,column3\n 1,2,3\n 4,5,6\n ", out);
		
	}

	/**
	 * Test if it can handle invalid extension (without dot)
	 * @throws IOException
	 */
	@Test
	public void testExtension() throws IOException {
		Reader reader = new Reader(columns, delimiter);
		RecordList recordList = reader.read("src/main/resources/test_input.txt", false);
		
		Writer writer = new Writer(delimiter);
		writer.writeData(recordList, "src/main/resources/test_output", "csv" );
		RecordList recordList2 = reader.read("src/main/resources/test_input.txt", false);
		
		String out = recordList2.toString(delimiter);
		assertEquals("column1,column2,column3\n 1,2,3\n 4,5,6\n ", out);
		
	}

}
