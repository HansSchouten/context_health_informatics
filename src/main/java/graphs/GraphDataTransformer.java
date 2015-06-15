package graphs;

import java.util.ArrayList;
import java.util.Iterator;

import analyze.parsing.ParseResult;
import model.ChunkedSequentialData;
import model.Column;
import model.Record;
import model.SequentialData;

/**
 * This class transforms the data, right so it can be passed to the webview.
 * @author Matthijs
 *
 */
public class GraphDataTransformer {

    /** This variable stores the data to draw the graph with. */
    protected SequentialData data;

    /** This variable stores the columns in these data. */
    protected Column[] cols;

    /**
     * Construct a GraphDataController with basic inputcolumns.
     */
    public GraphDataTransformer() {
        cols = new Column[0];
    }

    /**
     * This method sets the data to be transformed.
     * @param newData      - Data to set.
     */
    public void setData(SequentialData newData) {
        data = newData;
        if (data != null) {
            cols = data.getColumns();
        }
    }

    /**
     * This method creates a json objects from the available columns.
     * @param columns       - Columns that are available.
     * @param inputNames    - Names of the inputs, should be equal lenght of columns.
     * @parem view          - String containing how the data should be drawn, per chunk or not.
     * @return              - String containing a JSON list of objects.
     */
    public String getJSONFromColumn(ArrayList<String> columns, ArrayList<String> inputNames, String view) {
        String dataobject = "[";
        if (data instanceof ChunkedSequentialData) {
            dataobject += GetChunkedSequentialData(columns, inputNames, view, (ChunkedSequentialData) data);
        } else {
            dataobject += getJSONForChunk(columns, inputNames, data);
        }
        dataobject += "]";
        return dataobject;
        
    }

    /**
     * This method gets the JSON for one chunck.
     * @param columns       - Columns to get the chunk for.
     * @param inputNames    - Inputnames for the columns to chunk.
     * @param datablock     - Data to chunck.
     * @return              - String containing a JSON representation for this chunk.
     */
    protected String getJSONForChunk(ArrayList<String> columns,
            ArrayList<String> inputNames, SequentialData datablock) {
        StringBuilder dataobject = new StringBuilder();
        
        dataobject.append("[");
        ArrayList<String> dataobjects = new ArrayList<String>();
        for (Iterator<Record> iterator = datablock.iterator(); iterator.hasNext();) {
            String recordObject = getJSONForRecord(iterator.next(), columns, inputNames);
            if (recordObject != null) {
                dataobjects.add(recordObject);
            }
        }
        
        for (int i = 0; i < dataobjects.size(); i++) {
            dataobject.append(dataobjects.get(i));
            if (i != dataobjects.size() - 1) {
                dataobject.append(", ");
            }
        }
        dataobject.append("]");
        
        return dataobject.toString();
    }

    /**
     * This method performs an action on chunked data depending on what it needs to do.
     * @param columns       - Columns that need to appear in the graph.
     * @param inputNames    - Names of the input values of this corresponding column.
     * @param view          - View of how the data should be represented.
     * @param csd           - Chunked data that needs to be returned.
     * @return              - String representation of the JSON of the different files.
     */
    protected String GetChunkedSequentialData(ArrayList<String> columns,
            ArrayList<String> inputNames, String view, ChunkedSequentialData csd) {
        StringBuilder chunkedData = new StringBuilder();
        switch(view) {
        case "All Data":
            SequentialData sd = new SequentialData();
            for (SequentialData data: csd.getChunkedData().values()) {
                sd.addAll(data);
            }
            chunkedData.append(getJSONForChunk(columns, inputNames, sd));
            break;
        case "Per Chunk":
            int i = 0;
            for (SequentialData chunk: csd.getChunkedData().values()) {
                chunkedData.append(getJSONForChunk(columns, inputNames, chunk));
                if (i != csd.getChunkedData().values().size() - 1) {
                    chunkedData.append(", ");
                }
                i++;
            }
        default: break;
        }
        return chunkedData.toString();
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
            if (!next.containsKey(name) && !name.equals("labels") && !name.equals("timestamp")) {
                return null;
            }
        }

        StringBuilder jsonobj = new StringBuilder();
        jsonobj.append("{");
        for (int i = 0; i < columns.size(); i++) {            
            jsonobj.append("\"");
            jsonobj.append(inputNames.get(i));
            jsonobj.append("\" : \"");
            if (columns.get(i).equals("labels")) {
                jsonobj.append(next.printLabels(", "));
            } else if (columns.get(i).equals("timestamp")) {
                jsonobj.append(next.getTimeStamp().toString());
            } else {
                jsonobj.append(next.get(columns.get(i)));
            }
            jsonobj.append("\"");

            if (i != columns.size() - 1) {
                jsonobj.append(", ");
            }
        }
        jsonobj.append("}");
        System.out.println(jsonobj.toString());
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
