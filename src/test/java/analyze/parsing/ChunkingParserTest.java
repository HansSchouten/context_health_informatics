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

import org.junit.Before;
import org.junit.Test;

import analyze.AnalyzeException;
import analyze.chunking.ChunkingException;

public class ChunkingParserTest {

	SequentialData userData;

	@Before
	public void setup() throws IOException {
		Column[] columns =
			{new Column("creaLevel"), new Column("unit"), new DateColumn("date", "d-M-yyyy", true)};
		columns[0].setType(ColumnType.DOUBLE);
	    columns[1].setType(ColumnType.STRING);
	    columns[2].setType(ColumnType.DATE);

		String delimiter = ",";
	    Reader reader = new Reader(columns, delimiter);
		RecordList recordList = reader.read("src/main/resources/chunkertest/admire_4.txt", false);

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
        String operation = "ON date";
        SequentialData result = cp.parseOperation(operation, userData);

        assertEquals(3, result.size());
    }

    @Test
    public void chunkOnValueTestChunkSizes() throws ChunkingException {
    	ChunkingParser cp = new ChunkingParser();
        String operation = "ON date";
        ChunkedSequentialData result = (ChunkedSequentialData) cp.parseOperation(operation, userData);
        HashMap<Object, SequentialData> chunkedData = result.getChunkedData();
        System.out.println(chunkedData);
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
    public void constrainChunkedDataTest() throws AnalyzeException {
    	Parser parser = new Parser();
        String script = "CHUNK PER 7 DAYS\nFILTER WHERE COL(creaLevel) < 175";
        SequentialData result = parser.parse(script, userData);

        assertEquals(2, result.size());
    }

}
