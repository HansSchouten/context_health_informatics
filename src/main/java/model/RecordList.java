package model;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class represents a list of records.
 * @author Matthijs
 *
 */
public class RecordList extends ArrayList<Record> {

    /**
     * Serial version ID.
     */
    private static final long serialVersionUID = -907389172333757537L;

    /**
     * This hashmap stores the names of the columns with their properties.
     */
    protected HashMap<String, Object> properties;

    /**
     * This variable stores the columns of this recordlist.
     */
    protected Column[] columns;

    /**
     * Construct a new RecordList, with the given columns.
     * @param cols  - Columns of the recordlist.
     */
    public RecordList(Column[] cols) {
        columns = cols;
        this.properties = new HashMap<String, Object>();
    }

    /**
     * Set a property of this recordList to the given value.
     * @param key      - The key for for the item in the hashmap
     * @param value    - sThe value for the item in the hashmap
     */
    public void setProperty(String key, Object value) {
        this.properties.put(key, value);
    }

    /**
     * Return the value of the given property.
     * @param key      - The key to the property.
     * @return         - The property corresponding to the key.
     */
    public Object getProperty(String key) {
        return this.properties.get(key);
    }

    /**
     * Adds a record to the selected RecordList.
     * @param rec      - Record that needs to be added.
     * @return         - The recordlist with the added element.
     */
    public RecordList addRecord(Record rec) {
        this.add(rec);
        return this;
    }

    /**
     * Returns the record corresponding to the given index.
     * @param index    - the index of the record.
     * @return         - The record corresponding to the index.
     */
    public Record getRecord(int index) {
        return this.get(index);
    }

    /**
     * Convert this RecordList object into a SequentialData.
     * @return              - A SequentialData object.
     */
    public SequentialData toSequentialData() {
        SequentialData result = new SequentialData();
        for (Record record : this) {
            result.add(record);
        }
        return result;
    }

}
