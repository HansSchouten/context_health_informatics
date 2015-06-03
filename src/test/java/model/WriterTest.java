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
        {new Column("column1", ColumnType.STRING), new Column("column2", ColumnType.STRING), new Column("column3", ColumnType.STRING), new Column("column4", ColumnType.STRING)};
    String delimiter = ",";
    SequentialData userData; 
    SequentialData userData2;
    
    @Before
    public void setup() throws IOException {

        columns[0].setType(ColumnType.DOUBLE);
        columns[1] = new DateColumn("datum", ColumnType.DATE, "yyMMdd", true);
        columns[2] = new DateColumn("tijd", ColumnType.TIME, "HHmm", true);
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
        
        File text = new File("src/main/resources/test_output_writeFile.txt");
    
        Writer.writeFile(text, out);
        
        String written_content = new String(readAllBytes(get("src/main/resources/test_output_writeFile.txt"))); 
        
        String[] lines = written_content.split("\n");
        assertEquals(3, lines.length);
        assertEquals("17.0,person1,2012-05-15,1825", lines[0].trim());
        assertEquals("15.0,person1,2015-05-15,1224", lines[1].trim());
        assertEquals("10.0,person2,2020-05-15,1424", lines[2].trim());
    }
}
