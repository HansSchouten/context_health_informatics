package analyze.computation;

import java.util.HashMap;

import model.DataField;
import model.DataFieldDouble;
import model.Record;
import model.UnsupportedFormatException;

public abstract class AVG {

	/**
	 * This class calculates the average of a list of column values
	 * @author Elvan
	 * @throws UnsupportedFormatException 
	 * @throws ComputationTypeException
	 */

	public static DataField run(HashMap<String, DataField> columnValues) throws UnsupportedFormatException {
		
		double sum = 0;
		
		for (DataField value : columnValues.values()) {
			
			sum = sum + value.getDoubleValue();
			
		}
			
		int count = columnValues.size();
		
		double result = sum / count;

		return new DataFieldDouble(result);

	}

}
