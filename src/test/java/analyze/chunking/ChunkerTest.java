package analyze.chunking;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import analyze.chunking.ChunkOnPeriod;
import analyze.chunking.ChunkOnValue;
import analyze.chunking.ChunkType;
import analyze.chunking.Chunker;
import model.ChunkedSequentialData;
import model.Record;
import model.SequentialData;
import model.datafield.DataFieldDateTime;
import model.datafield.DataFieldString;

import org.junit.Before;
import org.junit.Test;

public class ChunkerTest {

    HashMap<String, SequentialData> linkedGroups;

    @Before
    public void setup() throws IOException {
        linkedGroups = new HashMap<String, SequentialData>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        Record rec1 = new Record(LocalDateTime.of(2012, 5, 10, 10, 0));
        rec1.put("date", new DataFieldString("2012-05-10"));
        rec1.put("dateTime", new DataFieldDateTime(LocalDateTime.parse("2012-05-10 09:20", formatter)));

        Record rec2 = new Record(LocalDateTime.of(2012, 5, 10, 10, 0));
        rec2.put("date", new DataFieldString("2012-05-10"));
        rec2.put("dateTime", new DataFieldDateTime(LocalDateTime.parse("2012-05-10 09:30", formatter)));

        Record rec3 = new Record(LocalDateTime.of(2012, 5, 10, 10, 0));
        rec3.put("date", new DataFieldString("2012-05-10"));
        rec3.put("dateTime", new DataFieldDateTime(LocalDateTime.parse("2012-05-10 10:00", formatter)));

        Record rec4 = new Record(LocalDateTime.of(2012, 4, 6, 10, 0));
        rec4.put("date", new DataFieldString("2012-04-06"));
        rec4.put("dateTime", new DataFieldDateTime(LocalDateTime.parse("2012-04-06 10:00", formatter)));

        Record rec5 = new Record(LocalDateTime.of(2012, 4, 6, 10, 0));
        rec5.put("date", new DataFieldString("2012-04-06"));
        rec5.put("dateTime", new DataFieldDateTime(LocalDateTime.parse("2012-04-06 12:00", formatter)));

        SequentialData userData = new SequentialData();
        userData.add(rec1);
        userData.add(rec2);
        userData.add(rec3);
        userData.add(rec4);
        userData.add(rec5);

        linkedGroups.put("1", userData);
    }

    @Test
    public void testNumberOfChunks() {
        ChunkType chunkType = new ChunkOnValue("date");
        Chunker chunker = new Chunker();
        ChunkedSequentialData chunks = (ChunkedSequentialData) chunker.chunk(linkedGroups.get("1"), chunkType);

        assertEquals(2, chunks.size());
    }

    @Test
    public void testNumberOfRecordsInChunks() {
        ChunkType chunkType = new ChunkOnValue("date");
        Chunker chunker = new Chunker();
        ChunkedSequentialData chunks = (ChunkedSequentialData) chunker.chunk(linkedGroups.get("1"), chunkType);

        assertEquals(2, chunks.get("2012-04-06").size());
        assertEquals(3, chunks.get("2012-05-10").size());
    }

    @Test
    public void testChunkOnWeek() {
        SequentialData patientData = linkedGroups.get("1");
        ChunkType chunkType = new ChunkOnPeriod(patientData, 7);
        Chunker chunker = new Chunker();
        ChunkedSequentialData chunks = (ChunkedSequentialData) chunker.chunk(patientData, chunkType);

        assertEquals(2, chunks.get("2012-04-06").size());
        assertEquals(3, chunks.get("2012-05-04").size());
    }

    @Test
    public void testChunkOnWeekday() {
        SequentialData patientData = linkedGroups.get("1");
        ChunkType chunkType = new ChunkOnWeekday("dateTime");
        Chunker chunker = new Chunker();
        ChunkedSequentialData chunks = (ChunkedSequentialData) chunker.chunk(patientData, chunkType);
        assertEquals(3, chunks.get("THURSDAY").size());
        assertEquals(2, chunks.get("FRIDAY").size());
    }

    @Test
    public void testChunkOnHour() {
        SequentialData patientData = linkedGroups.get("1");
        ChunkType chunkType = new ChunkOnHour("dateTime");
        Chunker chunker = new Chunker();
        ChunkedSequentialData chunks = (ChunkedSequentialData) chunker.chunk(patientData, chunkType);
        assertEquals(2, chunks.get("9").size());
        assertEquals(2, chunks.get("10").size());
        assertEquals(1, chunks.get("12").size());
    }

}