package analyze.computation;

import java.util.HashMap;

import model.DataField;
import model.SequentialData;
import model.Record;
import model.UnsupportedFormatException;

public class Computer {
	
	/**
     * This variable stores the values of the columns that are evaluated.
     */
    protected HashMap<String, DataField> columnValues;
	
    /**
     * This variable stores the sequential data that needs to be computed.
     */
    protected SequentialData userData;
    
    /**
     * This variable stores the name of the column the computation is called on.
     */
    protected String column;

    /**
     * Construct a computation that consists of a string.
     * @param computation     			- String containing the computation
     */
    public Computer(String computation, String columname, SequentialData data) {
    	column = columname;
    	userData = data;
        columnValues = new HashMap<String, DataField>(); 
    }
    
    /**
     * This method should evaluate a condition with a given record.
     * @param record    - Record to evaluate with.
     * @return          - True if evaluation is right, false otherwise.
     */
    public void gatherColumnValues(SequentialData userData, String columname) {
        columnValues.clear();
        // zal later vervangen kunnen worden door String patient ID
        int id = 0;
        
    	for (Record record : userData) {
        	columnValues.put(Integer.toString(id), record.get(columname));
        	id = columnValues.size();
        }
    
    }
    
    /**
     * This method performs the computation on the sequential data
     * @return          - Result of the computation
     * @throws UnsupportedFormatException 
     * @throws ComputationTypeException 
     */
    public DataField compute(String computation) throws ComputationTypeException, UnsupportedFormatException {
  
    		gatherColumnValues(userData, column);
    		
    		DataField result;
    		
            switch (computation) {
            case "AVERAGE":
                result = AVG.run(columnValues);
                break;
            case "COUNT":
            	result = COUNT.run(columnValues);
            	break;
            case "SUM":
            	result = SUM.run(columnValues);
            	break;
            default:
            	result = COUNT.run(columnValues);
            	break;
        }

            return result;
    }
    

    
}
