package model.chunking;

import java.time.LocalDateTime;
import model.Record;

public class ChunkOnPeriod implements ChunkType {
	
	protected LocalDateTime firstDate;
	protected int length;
	
	/**
	 * ChunkOnPeriod constructor
	 * @param firstDate
	 * @param length
	 */
	public ChunkOnPeriod(LocalDateTime firstDate, int length) {
		this.firstDate = firstDate;
		this.length = length;
	}

	@Override
	public Object getChunk(Record record) {
		return null;
	}

}
