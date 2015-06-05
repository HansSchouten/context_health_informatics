package graphs;

import java.io.File;
import java.util.ArrayList;

import model.Column;
import model.ColumnType;

public class GraphDataTransformer {
    
    public int[] testdata = {10,20,30,40,50,60,70,80};

    public void readFile(File file) {
        // TODO Auto-generated method stub
        
    }
    
    public String getJSONFromColumn(ArrayList<String> columns, ArrayList<String> inputNames) {
        //TODO: implement this.
        String string = "[";
        
        for (int i=0; i < testdata.length; i++) {
            string += "{\"x\" : \"" + testdata[i] + "\"}";
            if (i != testdata.length - 1)
                string += ", ";
        }
        return string + "]";
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
