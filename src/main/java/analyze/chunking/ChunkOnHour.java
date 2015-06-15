package analyze.chunking;

import model.Record;
import model.datafield.DataField;
import model.datafield.DataFieldDateTime;
import model.datafield.DataFieldTime;

/**
 * This class represents an object that will chunk records on the hour.
 * @author Hans Schouten
 *
 */
public class ChunkOnHour implements ChunkType {

    /**
     * The column on which values the data needs to be chunked.
     */
    protected String column;

    /**
     * ChunkValue constructor.
     * @param chunkColumn        the column on which the data needs to be chunked
     */
    public ChunkOnHour(String chunkColumn) {
        this.column = chunkColumn;
    }

    @Override
    public Object getChunk(Record record) {
        DataField dataField = record.get(column);
        if (dataField instanceof DataFieldDateTime) {
            return ((DataFieldDateTime) dataField).getDateValue().getHour();
        } else {
            return ((DataFieldTime) dataField).getTimeValue().getHour();
        }
    }

}
