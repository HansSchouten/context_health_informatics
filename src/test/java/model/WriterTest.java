package model;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class WriterTest {

	String[] columns = {"column1", "column2", "column3"};
	String delimiter = ",";
	
	@Test
	public void testToString() throws IOException {
		String[] columns = {"column1", "column2", "column3"};
		Reader reader = new Reader(columns, delimiter);
		RecordList recordList = reader.read("src/main/resources/test_input.txt");
		
		String out = reader.toString(recordList, delimiter);
		// test number of records
		assertEquals(2, recordList.size());
		// test number of columns
		assertEquals(3, recordList.get(0).size());
		// test the content of the string
		assertEquals("column1,column2,column3\n 1,2,3\n 4,5,6\n ", out);
	}
	
	@Test
	public void testWrite() throws IOException {
		String[] columns = {"column1", "column2", "column3"};
		Reader reader = new Reader(columns, delimiter);
		RecordList recordList = reader.read("src/main/resources/test_input.txt");
		
		Writer writer = new Writer(columns, delimiter);
		writer.writeData(recordList, "src/main/resources/test_output.txt" );
		RecordList recordList2 = reader.read("src/main/resources/test_input.txt");
		
		String out = reader.toString(recordList2, delimiter);
		assertEquals("column1,column2,column3\n 1,2,3\n 4,5,6\n ", out);
		
	}


}
