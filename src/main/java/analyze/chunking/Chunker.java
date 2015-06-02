package analyze.chunking;

import model.ChunkedSequentialData;
import model.DataFieldDate;
import model.Record;
import model.SequentialData;

/**
 * This class represents a object that will chunk the data.
 * @author Hans Schouten
 *
 */
public class Chunker {

    /**
     * Chunk the given data.
     * @param data            the data that needs to be chunked
     * @param chunkType        the type of chunking that needs to be done
     * @return                 for each chunk an object containing the records
     */
    public SequentialData chunk(SequentialData data, ChunkType chunkType) {
        ChunkedSequentialData chunks = new ChunkedSequentialData();

        for (Record record : data) {
            Object chunk = chunkType.getChunk(record);
            if(chunk == null) {
            	continue;
            }
            String chunkKey = chunk.toString();
            if (chunk instanceof DataFieldDate) {
                chunkKey = ((DataFieldDate) chunk).toDateString();
            }
            this.storeRecord(chunks, record, chunkKey);
        }

        return chunks;
    }

    /**
     * Store the given record in the given chunk.
     * @param chunks        the chunks to which the given chunk needs to be added
     * @param record        the record that needs to be added to a chunk
     * @param chunk            the chunk this records needs to be stored in
     */
    protected void storeRecord(ChunkedSequentialData chunks, Record record, Object chunk) {
        SequentialData storedData = chunks.get(chunk);
        if (storedData != null) {
            storedData.add(record);
        } else {
            storedData = new SequentialData();
            storedData.add(record);
            chunks.add(chunk, storedData);
        }
    }

}
