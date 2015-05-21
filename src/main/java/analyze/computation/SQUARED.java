package analyze.computation;

import java.util.HashMap;

import model.DataField;
import model.DataFieldDouble;
import model.UnsupportedFormatException;

/**
 * This class calculates the sum of squares of a list of column values.
 * @author Elvan
 *
 */
public abstract class SQUARED {

	/**
	 * Run the computation.
	 * @param columnValues					the values to run the computation on
	 * @return								the result of the computation
	 * @throws UnsupportedFormatException	format is not supported
	 */
	public static DataField run(HashMap<String, DataField> columnValues) throws UnsupportedFormatException {
		double sum = 0;
		double sum_squared = 0;
		int count = columnValues.size();

		for (DataField value : columnValues.values()) {
			sum = sum + value.getDoubleValue();
			sum_squared = sum_squared + Math.pow(value.getDoubleValue(), 2);
		}
		
		double result = sum_squared - (Math.pow(sum, 2) / count);
				
		return new DataFieldDouble(result);
	}

}
