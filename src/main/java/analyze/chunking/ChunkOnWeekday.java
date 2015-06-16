package analyze.chunking;

import java.time.LocalDateTime;

import model.Record;
import model.datafield.DataField;
import model.datafield.DataFieldDateTime;

/**
 * This class represents an object that will chunk records on the weekday.
 * @author Hans Schouten
 *
 */
public class ChunkOnWeekday implements ChunkType {

    /**
     * The column on which values the data needs to be chunked.
     */
    protected String column;

    /**
     * ChunkValue constructor.
     * @param chunkColumn        the column on which the data needs to be chunked
     */
    public ChunkOnWeekday(String chunkColumn) {
        this.column = chunkColumn;
    }

    @Override
    public Object getChunk(Record record) {
        DataField dataField = record.get(column);
        LocalDateTime date = ((DataFieldDateTime) dataField).getDateValue();
        return date.getDayOfWeek();
    }

}
