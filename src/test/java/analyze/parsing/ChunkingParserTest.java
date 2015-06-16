package analyze.parsing;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.HashMap;

import model.ChunkedSequentialData;
import model.Column;
import model.ColumnType;
import model.DateColumn;
import model.Reader;
import model.RecordList;
import model.SequentialData;
import model.datafield.DataField;
import model.datafield.EmptyDataField;

import org.junit.Before;
import org.junit.Test;

import analyze.AnalyzeException;
import analyze.chunking.ChunkingException;
import analyze.labeling.LabelFactory;

public class ChunkingParserTest {

    SequentialData userData;

    @Before
    public void setup() throws IOException {
        Column[] columns =
            {new Column("creaLevel", ColumnType.DOUBLE), new Column("unit", ColumnType.STRING), new DateColumn("date", ColumnType.DATE, "d-M-yyyy", true)};

        String delimiter = ",";
        Reader reader = new Reader(columns, delimiter);
        RecordList recordList = reader.read("src/main/resources/chunkertest/admire_4.txt", false);
        recordList.get(0).addLabel(LabelFactory.getInstance().getNewLabel("start").getNumber());
        recordList.get(1).addLabel(LabelFactory.getInstance().getNewLabel("eind").getNumber());

        userData = new SequentialData();
        userData.addRecordList(recordList);
    }

    @Test
    public void constructorTest() {
        ChunkingParser cp = new ChunkingParser();
        assertTrue(cp != null);
    }

    @Test (expected = ChunkingException.class)
    public void noColumnNameTest() throws ChunkingException {
        ChunkingParser cp = new ChunkingParser();
        String operation = "ON";
        cp.parseOperation(operation, userData);
    }

    @Test (expected = ChunkingException.class)
    public void noPeriodTest() throws ChunkingException {
        ChunkingParser cp = new ChunkingParser();
        String operation = "PER";
        cp.parseOperation(operation, userData);
    }


    @Test
    public void chunkOnValueTestNumberOfChunks() throws ChunkingException {
        ChunkingParser cp = new ChunkingParser();
        String operation = "ON COL(date)";
        SequentialData result = cp.parseOperation(operation, userData);

        assertEquals(3, result.size());
    }

    @Test
    public void chunkOnValueTestChunkSizes() throws ChunkingException {
        ChunkingParser cp = new ChunkingParser();
        String operation = "ON COL(date)";

        ChunkedSequentialData result = (ChunkedSequentialData) cp.parseOperation(operation, userData);
        HashMap<Object, SequentialData> chunkedData = result.getChunkedData();

        assertEquals(1, chunkedData.get("2012-04-06").size());
        assertEquals(1, chunkedData.get("2012-04-15").size());
        assertEquals(2, chunkedData.get("2012-04-16").size());
    }


    @Test
    public void chunkPerPeriodTestNumberOfChunks() throws ChunkingException {
        ChunkingParser cp = new ChunkingParser();
        String operation = "PER 7 DAYS";
        SequentialData result = cp.parseOperation(operation, userData);

        assertEquals(2, result.size());
    }

    @Test
    public void chunkPerPeriodValueTestChunkSizes() throws ChunkingException {
        ChunkingParser cp = new ChunkingParser();
        String operation = "PER 7 DAYS";
        ChunkedSequentialData result = (ChunkedSequentialData) cp.parseOperation(operation, userData);
        HashMap<Object, SequentialData> chunkedData = result.getChunkedData();

        assertEquals(1, chunkedData.get("2012-04-06").size());
        assertEquals(3, chunkedData.get("2012-04-13").size());
    }

    @Test
    public void chunkOnPattern() throws ChunkingException {
    	ChunkingParser cp = new ChunkingParser();
        String operation = "PATTERN LABEL(start) WITHIN(30) LABEL(eind)";
        ChunkedSequentialData result = (ChunkedSequentialData) cp.parseOperation(operation, userData);
        HashMap<Object, SequentialData> chunkedData = result.getChunkedData();

        assertEquals(1, chunkedData.size());
    }


    @Test
    public void constrainChunkedDataTest() throws AnalyzeException, Exception {
        Parser parser = new Parser();
        String script = "CHUNK PER 7 DAYS\nFILTER WHERE COL(creaLevel) < 175";
        SequentialData result = (SequentialData) parser.parse(script, userData);

        assertEquals(2, result.size());
    }


    @Test
    public void chunkFlatten() throws AnalyzeException, Exception {
        Parser parser = new Parser();
        String script = "CHUNK PER 7 DAYS\nCHUNK FLATTEN";
        SequentialData result = (SequentialData) parser.parse(script, userData);

        assertEquals(2, result.size());
    }

    @Test
    public void chunkRemove() throws AnalyzeException, Exception {
        Parser parser = new Parser();
        String script = "CHUNK PER 7 DAYS\nCHUNK REMOVE";
        SequentialData result = (SequentialData) parser.parse(script, userData);

        assertEquals(4, result.size());
    }
    
    @Test(expected=ChunkingException.class)
    public void chunkNotPerOn() throws AnalyzeException, Exception {
        Parser parser = new Parser();
        String script = "CHUNK IETS 7 DAYS";
        SequentialData result = (SequentialData) parser.parse(script, userData);
    }
    
    @Test
    public void chunkOnDataField() {
        ChunkingParser parser = new ChunkingParser();
        DataField d1 = new EmptyDataField();
        try {
            parser.parseOperation("x", d1);
            fail("Should have thrown an exception");
        }
        catch (Exception e) {
            assertEquals(e.getMessage(), "Chunking on a single value is not possible");
        }
    }

}
