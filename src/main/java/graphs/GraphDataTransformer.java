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
    public SequentialData data;
    
    public int[] testdata = {10,20,30,40,50,60,70,80};

    public void readFile(File file) {
        // TODO Auto-generated method stub
        
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
        // TODO Auto-generated method stub
        return "";
    }

    /**
     * This method should return the datacolumns that are detected.
     * @return      - Columns of the files.
     */
    public Column[] getDataColumns() {
        //TODO implement right.
        Column[] cols = {new Column("hoi", ColumnType.INT)};
        return cols;
    }

}
