package model.chunking;

import model.Record;

public class ChunkOnValue implements ChunkType {
	
	protected String column;
	
	/**
	 * ChunkValue constructor
	 * @param column
	 */
	public ChunkOnValue(String column) {
		this.column = column;
	}

	/**
	 * Return the chunk to which the given record belongs
	 */
	@Override
	public Object getChunk(Record record) {
		return record.get(column);
	}

}
