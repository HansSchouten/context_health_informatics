package model;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

import org.junit.Before;
import org.junit.Test;

public class WriterTest {

	Column[] columns = 
		{new Column("column1"), new Column("column2"), new Column("column3"), new Column("column4")};
	String delimiter = ",";
	SequentialData userData; 
	SequentialData userData2;
	
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
		RecordList recordList = reader.read("src/main/resources/test_input_writer.txt", false);
		
		userData.addRecordList(recordList);
	}
	
	/**
	 * Tests the write file method that is supposed to write a string to file
	 * @throws IOException
	 */
	@Test
	public void testWriteFile() throws IOException {
		
		String out = userData.toString(delimiter, false);
		
		File f = new File("src/main/resources/test_output_writeFile.txt");

		Writer.writeFile(f, out);
		
		String written_content = new String(readAllBytes(get("src/main/resources/test_output_writeFile.txt")));	
		String read_content = new String(readAllBytes(get("src/main/resources/test_input_writeFile.txt")));
		written_content.substring(0, written_content.length()-1);
		assertEquals(read_content, written_content);
		
	} 
}
