package analyze.parsing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import model.Column;
import model.ColumnType;
import model.DateColumn;
import model.Reader;
import model.RecordList;
import model.SequentialData;
import model.UnsupportedFormatException;
import model.datafield.DataFieldInt;

import org.junit.Before;
import org.junit.Test;

import analyze.AnalyzeException;

public class ConvertingParserTest {
    private Column[] columns = 
        {new Column("column1", ColumnType.STRING), new Column("column2", ColumnType.STRING), new Column("userID", ColumnType.INT), new Column("value", ColumnType.INT)};
    private String delimiter = ",";
    private SequentialData userData; 
    private Reader reader;

    @Before
    public void setup() throws IOException, UnsupportedFormatException {

        columns[0] = new DateColumn("datum", ColumnType.DATE, "yyMMdd", true);
        columns[0].setType(ColumnType.DATE);
        columns[1] = new DateColumn("tijd", ColumnType.TIME, "HHmm", true);
        columns[1].setType(ColumnType.TIME);
        columns[2].setType(ColumnType.INT);
        columns[3].setType(ColumnType.INT);

        userData = new SequentialData();
      
        
        reader = new Reader(columns, delimiter);
        RecordList recordList = reader.read("src/main/resources/test_input_convert.txt", false);
        userData.addRecordList(recordList);
        
    }

    @Test
    public void testParseConvert() throws AnalyzeException, Exception {
        Parser p = new Parser();
        SequentialData result = (SequentialData) p.parse("CONVERT value", userData);
        
        assertEquals("{datum=2012-02-24, feedback=meting morgen herhalen, grensgebied=5, tijd=16:04, kreatinine status=2, userID=97, value=230}", result.last().toString());
    }
    
    @Test
    public void testParseConvertSize() throws AnalyzeException, Exception {
        Parser p = new Parser();
        SequentialData result = (SequentialData) p.parse("CONVERT value", userData);
        
        assertEquals(10, result.size());
    }
    
    @Test(expected = ParseException.class)
    public void testParseConvertInvalid() throws AnalyzeException, Exception {
        Parser p = new Parser();
        SequentialData result = (SequentialData) p.parse("CONVERT value", new DataFieldInt(3));
        
    }
    
    

   
}
