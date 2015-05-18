package model;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;

import model.chunking.ChunkOnPeriod;
import model.chunking.ChunkOnValue;
import model.chunking.ChunkType;
import model.chunking.Chunker;

import org.junit.Before;
import org.junit.Test;

public class ChunkerTest {

	HashMap<String, SequentialData> linkedGroups;

	@Before
	public void setup() throws IOException {
		linkedGroups = new HashMap<String, SequentialData>();

		Record rec1 = new Record(LocalDateTime.of(2012, 5, 10, 10, 0));
		rec1.put("date", new DataFieldString("2012-05-10"));
		Record rec2 = new Record(LocalDateTime.of(2012, 5, 10, 10, 0));
		rec2.put("date", new DataFieldString("2012-05-10"));
		Record rec3 = new Record(LocalDateTime.of(2012, 5, 10, 10, 0));
		rec3.put("date", new DataFieldString("2012-05-10"));
		Record rec4 = new Record(LocalDateTime.of(2012, 4, 6, 10, 0));
		rec4.put("date", new DataFieldString("2012-04-06"));
		Record rec5 = new Record(LocalDateTime.of(2012, 4, 6, 10, 0));
		rec5.put("date", new DataFieldString("2012-04-06"));

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
		HashMap<Object, SequentialData> chunks = chunker.chunk(linkedGroups.get("1"), chunkType);

		assertEquals(2, chunks.size());
	}

	@Test
	public void testNumberOfRecordsInChunks() {
		ChunkType chunkType = new ChunkOnValue("date");
		Chunker chunker = new Chunker();
		HashMap<Object, SequentialData> chunks = chunker.chunk(linkedGroups.get("1"), chunkType);

		assertEquals(2, chunks.get("2012-04-06").size());
		assertEquals(3, chunks.get("2012-05-10").size());
	}

	@Test
	public void testChunkOnWeek() {
		SequentialData patientData = linkedGroups.get("1");
		ChunkType chunkType = new ChunkOnPeriod(patientData, 7);
		Chunker chunker = new Chunker();
		HashMap<Object, SequentialData> chunks = chunker.chunk(patientData, chunkType);

		assertEquals(2, chunks.get("2012-4-6").size());
		assertEquals(3, chunks.get("2012-5-4").size());
	}

}