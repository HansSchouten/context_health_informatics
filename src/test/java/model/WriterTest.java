package model;

import static org.junit.Assert.assertEquals;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.text.ParseException;

import org.junit.Test;
import org.junit.Before;

public class WriterTest {

	Column[] columns = 
		{new Column("column1"), new Column("column2"), new Column("column3"), new Column("column4")};
	String delimiter = ",";
	SequentialData userData; 
	SequentialData userData2;
	Writer writer;
	
	@Before
	public void setup() throws IOException {

		columns[0].setType(ColumnType.DOUBLE);
		columns[1] = new DateColumn("datum", "yyMMdd", true);
	    columns[1].setType(ColumnType.DATE);
	    columns[2] = new DateColumn("tijd", "HHmm", true);
	    columns[2].setType(ColumnType.TIME);
	    columns[3].setType(ColumnType.STRING);
	    
	    userData = new SequentialData();
	    
	    Reader reader = new Reader(columns, delimiter);
		RecordList recordList = reader.read("src/main/resources/test_input_writer.txt");
		
		userData.addRecordList(recordList);
	
		writer = new Writer(delimiter);
	    
	}
	
	/**
	 * Test toString with comma as delimiter
	 * @throws IOException
	 * @throws ParseException 
	 */
	@Test
	public void testToString() throws IOException, ParseException {

		String out = userData.toString(delimiter, columns);
		// test number of records
		assertEquals(3, userData.size());
		// test the content of the string
		assertEquals("17.0,120515,1825,person1\r\n 15.0,150515,1224,person1\r\n 10.0,200515,1424,person2\r\n ", out);
	}	
	
	/**
	 * Test toString with semicolon as delimiter
	 * @throws IOException
	 * @throws ParseException 
	 */
	@Test
	public void testSemicolon() throws IOException, ParseException {

		String out = userData.toString(";", columns);
		
		assertEquals("17.0;120515;1825;person1\r\n 15.0;150515;1224;person1\r\n 10.0;200515;1424;person2\r\n ", out);
	}
	
	/**
	 * Test writer with column names included
	 * @throws IOException
	 * @throws ParseException 
	 */
	@Test
	public void testColumnNames() throws IOException, ParseException {

		writer.writeData(userData, "src/main/resources/test_output_writer1", ".txt", columns, true);
		String content = new String(readAllBytes(get("src/main/resources/test_output_writer1.txt")));
		assertEquals("column1,datum,tijd,column4\r\n 17.0,120515,1825,person1\r\n 15.0,150515,1224,person1\r\n 10.0,200515,1424,person2\r\n ", content);
		
	}
	
	/**
	 * Test writer with full filename (extension included)
	 * @throws IOException
	 * @throws ParseException 
	 */
	@Test
	public void testFullFileName() throws IOException, ParseException {

		writer.writeData(userData, "src/main/resources/test_output_writer3.txt", ".txt", columns, true);
		String content = new String(readAllBytes(get("src/main/resources/test_output_writer3.txt")));
		assertEquals("column1,datum,tijd,column4\r\n 17.0,120515,1825,person1\r\n 15.0,150515,1224,person1\r\n 10.0,200515,1424,person2\r\n ", content);
		
	}
	
	
	/**
	 * Test writer with empty data
	 * This should throw a no such file exception since the output will not be written to file
	 * @throws IOException
	 * @throws ParseException 
	 */
	@Test(expected=NoSuchFileException.class)
	public void testWriterEmptyData() throws IOException, ParseException {
		SequentialData empty = new SequentialData();
		writer.writeData(empty, "src/main/resources/test_output_writer4.txt", ".txt", columns, true);
		String content = new String(readAllBytes(get("src/main/resources/test_output_writer4.txt")));		
	}
	
//	/**
//	 * Test the writer with an invalid file
//	 * This should throw an IOException since the corresponding file cannot be created
//	 * @throws IOException
//	 * @throws ParseException 
//	 */
//	@Test(expected=IOException.class)
//	public void testWriterUnknownFile() throws IOException, ParseException {
//
//		writer.writeData(userData, "src/main/resources/klk/", ".", columns, true);
//	}
	
	/**
	 * Test writer with semicolon as delimiter
	 * @throws IOException
	 */
	@Test
	public void testToString2() throws IOException {
		
		String out = userData.toString(delimiter, columns);

		writer.writeData(userData, "src/main/resources/test_output_writer2", ".txt", columns, false);
		
		Reader reader2 = new Reader(columns, delimiter);
		RecordList recordList2 = reader2.read("src/main/resources/test_output_writer2.txt");
		
		SequentialData userData3 = new SequentialData();
		userData3.addRecordList(recordList2);
		
		String out2 = userData3.toString(delimiter, columns);
		
		assertEquals(out, out2);
		
	}
	
	/**
	 * Test if it can handle invalid extension (without dot)
	 * @throws IOException
	 */
	@Test
	public void testExtension() throws IOException {
		
		String out = userData.toString(delimiter, columns);

		writer.writeData(userData, "src/main/resources/test_output_writer2", "csv", columns, false);
		
		Reader reader2 = new Reader(columns, delimiter);
		RecordList recordList2 = reader2.read("src/main/resources/test_output_writer2.csv");
		
		SequentialData userData3 = new SequentialData();
		userData3.addRecordList(recordList2);
		
		String out2 = userData3.toString(delimiter, columns);
		
		assertEquals(out, out2);
		
	} 
	
	

}
