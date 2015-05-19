package analyze.computation;

import java.util.HashMap;

import model.DataField;
import model.DataFieldDouble;
import model.Record;
import model.UnsupportedFormatException;

public abstract class SUM {

	/**
	 * This class calculates the sum of a list of column values
	 * @author Elvan
	 * @throws UnsupportedFormatException 
	 * @throws ComputationTypeException
	 */

	public static DataField run(HashMap<String, DataField> columnValues) throws ComputationTypeException, UnsupportedFormatException {
		
		double sum = 0;
	
		for (DataField value : columnValues.values()) {
			
			sum = sum + value.getDoubleValue();

		}
	
		return new DataFieldDouble(sum);

	}

}
