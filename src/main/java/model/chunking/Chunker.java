package model.chunking;

import model.Record;
import model.SequentialData;

import java.util.HashMap;

/**
 * This class represents a object that will chunk the data.
 * @author Hans Schouten
 *
 */
public class Chunker {

	/**
	 * Chunk the given data.
	 * @param data			the data that needs to be chunked
	 * @param chunkType		the type of chunking that needs to be done
	 * @return 				for each chunk an object containing the records
	 */
	public HashMap<Object, SequentialData> chunk(SequentialData data, ChunkType chunkType) {
		HashMap<Object, SequentialData> chunks = new HashMap<Object, SequentialData>();

		for (Record record : data) {
			Object chunk = chunkType.getChunk(record).toString();
			this.storeRecord(chunks, record, chunk);
		}

		return chunks;
	}

	/**
	 * Store the given record in the given chunk.
	 * @param chunks		the chunks to which the given chunk needs to be added
	 * @param record		the record that needs to be added to a chunk
	 * @param chunk			the chunk this records needs to be stored in
	 */
	protected void storeRecord(HashMap<Object, SequentialData> chunks, Record record, Object chunk) {
		if (chunks.containsKey(chunk)) {
			SequentialData storedData = chunks.get(chunk);
			storedData.add(record);
		} else {
			SequentialData storedData = new SequentialData();
			storedData.add(record);
			chunks.put(chunk, storedData);
		}
	}

}
