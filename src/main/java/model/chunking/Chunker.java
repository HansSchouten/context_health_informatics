package model.chunking;

import model.Record;
import model.SequentialData;

import java.util.HashMap;

public class Chunker {

	/**
	 * Chunker constructor
	 */
	public Chunker() {}
	
	/**
	 * Chunk the given data
	 * @param data
	 * @param column
	 */
	public HashMap<Object, SequentialData> chunk(SequentialData data, ChunkType chunkType) {
		HashMap<Object, SequentialData> chunks = new HashMap<Object, SequentialData>();
		
		for(Record record : data) {
			Object chunk = chunkType.getChunk(record).toString();			
			this.storeRecord(chunks, record, chunk);
		}
		
		return chunks;
	}
	
	/**
	 * Store the given record in the given chunk
	 * @param chunks
	 * @param record
	 * @param chunk
	 */
	protected void storeRecord(HashMap<Object, SequentialData> chunks, Record record, Object chunk) {
		if(chunks.containsKey(chunk)) {
			SequentialData storedData = chunks.get(chunk);
			storedData.add(record);
		} else {
			SequentialData storedData = new SequentialData();
			storedData.add(record);
			chunks.put(chunk, storedData);
		}
	}
	
}
