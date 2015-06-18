package analyze.parsing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import model.ChunkedSequentialData;
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
import analyze.chunking.ChunkOnPeriod;
import analyze.chunking.ChunkType;
import analyze.chunking.Chunker;

public class ConvertingParserTest {
    private Column[] columns = 
        {new Column("column1", ColumnType.STRING), new Column("column2", ColumnType.STRING), new Column("userID", ColumnType.INT), new Column("value", ColumnType.INT), new Column("second", ColumnType.INT)};
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
        columns[4].setType(ColumnType.INT);

        userData = new SequentialData();

        reader = new Reader(columns, delimiter);
        RecordList recordList = reader.read("src/main/resources/test_input_convert.txt", false);
        userData.addRecordList(recordList);
        
    }

    @Test
    public void testParseConvert() throws AnalyzeException, Exception {
        Parser p = new Parser();
        SequentialData result = (SequentialData) p.parse("CONVERT COL(value)", userData);
        
        assertEquals("{datum=2012-02-26, feedback=meting morgen herhalen, grensgebied=3, tijd=14:05, kreatinine status=3, userID=97, value=160, second=1}", result.last().toString());
    }
    
    @Test
    public void testParseConvertSize() throws AnalyzeException, Exception {
        Parser p = new Parser();
        SequentialData result = (SequentialData) p.parse("CONVERT COL(value)", userData);
        
        assertEquals(15, result.size());
    }
    
    @Test(expected = ParseException.class)
    public void testParseConvertInvalid() throws AnalyzeException, Exception {
        Parser p = new Parser();
        SequentialData result = (SequentialData) p.parse("CONVERT COL(value)", new DataFieldInt(3));
        
    }

    @Test
    public void testParseConvertSecondMeasurement() throws Exception {
        Parser p = new Parser();
        SequentialData result = (SequentialData) p.parse("CONVERT SECOND MEASUREMENT COL(second)", userData);
        
        ChunkType chunkType = new ChunkOnPeriod(result, 1);
        Chunker chunker = new Chunker();
        ChunkedSequentialData chunks = (ChunkedSequentialData) chunker.chunk(result, chunkType);
        
        assertEquals(false, chunks.get("2011-06-06").last().get("second measurement").getBooleanValue());
    }
    
    @Test
    public void testParseConvertPhase() throws Exception {
        Parser p = new Parser();
        SequentialData result = (SequentialData) p.parse("CONVERT PHASE", userData);
        
        assertEquals(1, result.first().get("phase").getIntegerValue());
        assertEquals(4, result.last().get("phase").getIntegerValue());
    }
    
    @Test
    public void testParseConvertPhase2() throws Exception {
        Parser p = new Parser();
        
        RecordList recordList2 = reader.read("src/main/resources/test_input_convert3.txt", false);
        userData = new SequentialData();
        userData.addRecordList(recordList2);
        
        SequentialData result = (SequentialData) p.parse("CONVERT PHASE", userData);
        
        ChunkType chunkType = new ChunkOnPeriod(result, 1);
        Chunker chunker = new Chunker();
        ChunkedSequentialData chunks = (ChunkedSequentialData) chunker.chunk(result, chunkType);
        
        
        assertEquals(2, chunks.get("2011-07-16").first().get("phase").getIntegerValue());
        assertEquals(3, chunks.get("2011-08-26").first().get("phase").getIntegerValue());
    }
    
    @Test
    public void testParseConvertPhaseCOL() throws Exception {
        Parser p = new Parser();
        
        RecordList recordList2 = reader.read("src/main/resources/test_input_convert3.txt", false);
        userData = new SequentialData();
        userData.addRecordList(recordList2);
        
        SequentialData result = (SequentialData) p.parse("CONVERT PHASE COL(datum)", userData);
        
        ChunkType chunkType = new ChunkOnPeriod(result, 1);
        Chunker chunker = new Chunker();
        ChunkedSequentialData chunks = (ChunkedSequentialData) chunker.chunk(result, chunkType);
        
        
        assertEquals(2, chunks.get("2011-07-16").first().get("phase").getIntegerValue());
        assertEquals(3, chunks.get("2011-08-26").first().get("phase").getIntegerValue());
    }
    
    @Test
    public void testParseConvertSecondMeasurementTrue() throws Exception {
        Parser p = new Parser();
        SequentialData result = (SequentialData) p.parse("CONVERT SECOND MEASUREMENT COL(second)", userData);
        
        assertEquals(true , result.first().get("second measurement").getBooleanValue());
    }
    
    @Test
    public void testParseConvertSecondMeasurementFalse() throws Exception {
        Parser p = new Parser();
        SequentialData result = (SequentialData) p.parse("CONVERT SECOND MEASUREMENT COL(second)", userData);
      
        ChunkType chunkType = new ChunkOnPeriod(result, 1);
        Chunker chunker = new Chunker();
        ChunkedSequentialData chunks = (ChunkedSequentialData) chunker.chunk(result, chunkType);
        
        assertEquals(false, chunks.get("2012-02-06").first().get("second measurement").getBooleanValue());
    }
    
    @Test
    public void testParseConvertReMeasurementTrue() throws Exception {
        Parser p = new Parser();

        SequentialData result = (SequentialData) p.parse("CONVERT REMEASUREMENT COL(value)", userData);
        
        ChunkType chunkType = new ChunkOnPeriod(result, 1);
        Chunker chunker = new Chunker();
        ChunkedSequentialData chunks = (ChunkedSequentialData) chunker.chunk(result, chunkType);

        assertEquals(true, chunks.get("2012-02-24").first().get("remeasurement").getBooleanValue());
       }
    
    @Test
    public void testParseConvertReMeasurementNA() throws Exception {
        Parser p = new Parser();

        SequentialData result = (SequentialData) p.parse("CONVERT REMEASUREMENT COL(value)", userData);
     
        assertEquals(false, result.first().get("remeasurement").getBooleanValue());    
       }
    
    
    @Test
    public void testParseConvertReMeasurementFalse() throws Exception {
        Parser p = new Parser();

        SequentialData result = (SequentialData) p.parse("CONVERT REMEASUREMENT COL(value)", userData);

        assertEquals(false, result.last().get("remeasurement").getBooleanValue());
       }

   
}
