package analyze.chunking;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import model.ChunkedSequentialData;
import model.datafield.DataFieldString;
import model.Record;
import model.SequentialData;

import org.junit.Before;
import org.junit.Test;

import analyze.AnalyzeException;
import analyze.parsing.ParseException;
import analyze.parsing.Parser;

public class RemoveTest {

    SequentialData userData;

    @Before
    public void setUp() throws Exception {
        Record rec1 = new Record(LocalDateTime.of(2012, 5, 10, 10, 0));
        rec1.put("col1", new DataFieldString("6"));
        Record rec2 = new Record(LocalDateTime.of(2012, 5, 10, 10, 0));
        rec2.put("col1", new DataFieldString("7"));
        rec2.put("col2", new DataFieldString("2"));
        Record rec3 = new Record(LocalDateTime.of(2012, 4, 6, 10, 0));
        rec3.put("col1", new DataFieldString("3"));

        userData = new SequentialData();
        userData.add(rec1);
        userData.add(rec2);
        userData.add(rec3);
    }

    @Test(expected = ChunkingException.class)
    public void testChunkRemoveFail() throws ChunkingException {
        Chunker chunker = new Chunker();
        chunker.remove(userData);
    }

    @Test
    public void testChunkRemove() throws ChunkingException {
        ChunkType chunkType = new ChunkOnPeriod(userData, 7);
        Chunker chunker = new Chunker();
        ChunkedSequentialData chunks = (ChunkedSequentialData) chunker.chunk(userData, chunkType);
        SequentialData chunkedData = chunker.remove(chunks);
        assertEquals(3, chunkedData.size());
    }

    @Test
    public void testChunkParseRemove() throws Exception {
        Parser parser = new Parser();
        String script = "CHUNK PER 7 DAYS\nCHUNK REMOVE";
        SequentialData unChunkedData = (SequentialData) parser.parse(script, userData);
        assertEquals(3, unChunkedData.size());
    }

}
