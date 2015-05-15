package analyze.condition;

import java.util.HashMap;

import model.DataField;

/**
 * This class stores a columnName, a hashmap contains the value used.
 * @author Matthijs
 *
 */
public class ColumnTerm implements Expression{
    
    /**
     * This variable stores the columnname of the column.
     */
    protected String columnName;
    
    /**
     * Construct a columnTerm which contains a columnName
     * @param colName       - ColumnName of this term
     */
    public ColumnTerm(String colName) {
        columnName = colName;
    }

    @Override
    public DataField evaluate(HashMap<String, DataField> colValues) {
        return colValues.get(columnName);
    }
}
