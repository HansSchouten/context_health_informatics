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

		for (DataField value : columnValues.values()) {
			sum = sum + Math.pow(value.getDoubleValue(), 2);
		}

		return new DataFieldDouble(sum);
	}

}
