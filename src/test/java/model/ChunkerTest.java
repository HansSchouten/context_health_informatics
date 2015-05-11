package model;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import model.chunking.ChunkOnValue;
import model.chunking.ChunkType;
import model.chunking.Chunker;

import org.junit.Before;
import org.junit.Test;

public class ChunkerTest {
	
	HashMap<String, SequentialData> linkedGroups;
	
	@Before
	public void setup() throws IOException {
		Column[] cols1 = 
			{new Column("patient"), new Column("group"), new Column("date")};
		Group hospital = new Group("Hospital Appointments", ",", cols1, "patient");
		hospital.addFile("src/main/resources/chunkertest/hospital_appointments.txt");
		
		Column[] cols2 = 
			{new Column("creatinine"), new Column("unit"), new Column("date")};
		Group admire = new Group("Statt sensor", ",", cols2, null);
		admire.addFile("src/main/resources/chunkertest/admire_2.txt");
		admire.addFile("src/main/resources/chunkertest/admire_4.txt");
		
		ArrayList<Group> groups = new ArrayList<Group>();
		groups.add(hospital);
		groups.add(admire);
		
		Linker linker = new Linker();
		linkedGroups = linker.link(groups);
	}
	
	@Test
	public void testNumberOfChunks() {
		ChunkType chunkType = new ChunkOnValue("date"); 
		Chunker chunker = new Chunker();
		HashMap<Object, SequentialData> chunks = chunker.chunk(linkedGroups.get("4"), chunkType);

		assertEquals(2, chunks.size());
	}
	
	@Test
	public void testNumberOfRecordsInChunks() {
		ChunkType chunkType = new ChunkOnValue("date"); 
		Chunker chunker = new Chunker();
		HashMap<Object, SequentialData> chunks = chunker.chunk(linkedGroups.get("4"), chunkType);

		assertEquals(2, chunks.get("6-4-2012").size());
		assertEquals(3, chunks.get("10-5-2012").size());
	}
	
}