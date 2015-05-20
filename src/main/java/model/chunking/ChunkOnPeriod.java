package model.chunking;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import model.Record;
import model.SequentialData;

/**
 * This class represents an object that will chunk records on period.
 * @author Hans Schouten
 *
 */
public class ChunkOnPeriod implements ChunkType {

	/**
	 * The starting date of the first period.
	 */
	protected LocalDateTime firstDate;
	/**
	 * The length of each period.
	 */
	protected int length;

	/**
	 * ChunkOnPeriod constructor.
	 * @param patientData		the data that needs to be chunked
	 * @param periodLength		the length of each period
	 */
	public ChunkOnPeriod(SequentialData patientData, int periodLength) {
		this.firstDate = patientData.first().getTimeStamp();
		this.length = periodLength;
	}

	@Override
	public Object getChunk(Record record) {
        long daysFromStart = firstDate.until(record.getTimeStamp(), ChronoUnit.DAYS);
        long dayInPeriod = daysFromStart % length;
        LocalDateTime chunk = record.getTimeStamp().minusDays(dayInPeriod);
		return chunk.getYear() + "-" + chunk.getMonthValue() + "-" + chunk.getDayOfMonth();
	}

}