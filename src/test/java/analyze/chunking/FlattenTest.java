package analyze.chunking;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.HashMap;

import model.ChunkedSequentialData;
import model.datafield.DataFieldString;
import model.Record;
import model.SequentialData;

import org.junit.Before;
import org.junit.Test;

public class FlattenTest {
    
    HashMap<String, SequentialData> linkedGroups;
    
    @Before
    public void setUp() throws Exception {
        linkedGroups = new HashMap<String, SequentialData>();

        Record rec1 = new Record(LocalDateTime.of(2012, 5, 10, 10, 0));
        rec1.put("test", new DataFieldString("test"));
        Record rec2 = new Record(LocalDateTime.of(2012, 5, 10, 10, 0));
        rec2.put("test", new DataFieldString("test3"));
        rec2.put("test2", new DataFieldString("test2"));
        Record rec3 = new Record(LocalDateTime.of(2012, 4, 6, 10, 0));
        rec3.put("test", new DataFieldString("test"));

        SequentialData userData = new SequentialData();
        userData.add(rec1);
        userData.add(rec2);
        userData.add(rec3);

        linkedGroups.put("1", userData);
    }

    @Test
    public void testChunkOnWeek() {
        SequentialData patientData = linkedGroups.get("1");
        ChunkType chunkType = new ChunkOnPeriod(patientData, 7);
        Chunker chunker = new Chunker();
        ChunkedSequentialData chunks = (ChunkedSequentialData) chunker.chunk(patientData, chunkType);
        SequentialData flattened = chunks.flatten();

        assertEquals(2, flattened.size());
        assertEquals("test", flattened.pollFirst().get("test").toString());
        Record last = flattened.pollLast();
        assertEquals("test", last.get("test").toString());
        assertEquals("test2", last.get("test2").toString());
    }

}
