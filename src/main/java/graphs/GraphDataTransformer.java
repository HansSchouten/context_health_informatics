package graphs;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import com.google.gson.Gson;

import model.Column;
import model.ColumnType;
import model.Record;
import model.SequentialData;

public class GraphDataTransformer {
    
    /** This variable stores the data to draw the graph with */
    protected SequentialData data;

    /** This variable stores the columns in these data */
    protected Column[] cols;

    /**
     * Construct a GraphDataController with basic inputcolumns.
     */
    public GraphDataTransformer () {
      //TODO implement right.
        cols = new Column[1];
        cols[1] = new Column("hoi", ColumnType.INT);
    }

    /**
     * This method sets the data to be transformed.
     * @param data      - Data to set.
     */
    public void setData(SequentialData newData) {
        data = newData;
    }

    public String getJSONFromColumn(ArrayList<String> columns, ArrayList<String> inputNames) {
        String string = "[";
        
        for (Iterator<Record> iterator = data.iterator(); iterator.hasNext();) {
            string += getJSONForRecord(iterator.next(), columns, inputNames);
        }

        return string + "]" ;
    }
    
    /**
     * This method creates an JSON object from one record.
     * @param next          - Record to evaluate
     * @param columns       - Columns to find in the record.
     * @param inputNames    - Names of the inputs for the records.
     * @return              - empty string if not correct, else new dataobject.
     */
    private String getJSONForRecord(Record next, ArrayList<String> columns,
            ArrayList<String> inputNames) {

        for (String name : columns) {
            if (!next.containsKey(name)) {
                return "";
            }
        }

        StringBuilder jsonobj = new StringBuilder();
        jsonobj.append("{");
        for (int i = 0; i < columns.size(); i++) {
            jsonobj.append(inputNames.get(i));
            jsonobj.append(" : ");
            jsonobj.append(next.get(columns.get(i)).toString());
            
            if (i != columns.size() - 1) {
                jsonobj.append(", ");
            }
        }
        jsonobj.append("}");
        return jsonobj.toString();
    }

    /**
     * This method should return the datacolumns that are detected.
     * @return      - Columns of the files.
     */
    public Column[] getDataColumns() {
        return cols;
    }
}
