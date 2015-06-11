package analyze.chunking;

import model.ChunkedSequentialData;
import model.Record;
import model.SequentialData;
import model.datafield.DataFieldDate;
import model.datafield.DataFieldString;

/**
 * This class represents a object that will chunk the data.
 * @author Hans Schouten
 *
 */
public class Chunker {

    /**
     * Chunk the given data.
     * @param data              the data that needs to be chunked
     * @param chunkType         the type of chunking that needs to be done
     * @return                  for each chunk an object containing the records
     */
    public SequentialData chunk(SequentialData data, ChunkType chunkType) {
        ChunkedSequentialData chunks = new ChunkedSequentialData();

        for (Record record : data) {
            Object chunk = chunkType.getChunk(record);
            if (chunk == null) {
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
     * @param chunks            the chunks to which the given chunk needs to be added
     * @param record            the record that needs to be added to a chunk
     * @param chunk             the chunk this records needs to be stored in
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

    /**
     * Flatten the given data.
     * this will combine the values of all records in a chunk into a single record
     * @param data                  the data that needs to be flattened
     * @return                      a chunked dataset in which each chunk contains a flattened record
     * @throws ChunkingException    something went wrong while flattening
     */
    public SequentialData flatten(SequentialData data) throws ChunkingException {
        if (!(data instanceof ChunkedSequentialData)) {
            throw new ChunkingException("CHUNK FLATTEN can only be performed on chunked data.");
        }
        return ((ChunkedSequentialData) data).flatten();
    }

    /**
     * Remove all chunks and create one SequentialData containing all records instead.
     * @param data                  the chunked data that needs to be unchunked
     * @return                      a dataset containing all records without chunks
     * @throws ChunkingException    something went wrong while removing the chunks
     */
    public SequentialData remove(SequentialData data) throws ChunkingException {
        if (!(data instanceof ChunkedSequentialData)) {
            throw new ChunkingException("CHUNK REMOVE can only be performed on chunked data.");
        }
        SequentialData result = new SequentialData();
        ChunkedSequentialData chunkedSequentialData = (ChunkedSequentialData) data;
        for (Object chunk : chunkedSequentialData.getChunkedData().keySet()) {
            for (Record record : chunkedSequentialData.get(chunk)) {
                record.put("chunk", new DataFieldString(chunk.toString()));
                result.add(record);
            }
        }
        return result;
    }

}
