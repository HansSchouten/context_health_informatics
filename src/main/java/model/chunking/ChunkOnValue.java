package model.chunking;

import model.Record;

/**
 * This class represents an object that will chunk records on the value of a specific column.
 * @author Hans Schouten
 *
 */
public class ChunkOnValue implements ChunkType {

	/**
	 * The column on which values the data needs to be chunked.
	 */
	protected String column;

	/**
	 * ChunkValue constructor.
	 * @param chunkColumn		the column on which the data needs to be chunked
	 */
	public ChunkOnValue(String chunkColumn) {
		this.column = chunkColumn;
	}

	@Override
	public Object getChunk(Record record) {
		return record.get(column);
	}

}
