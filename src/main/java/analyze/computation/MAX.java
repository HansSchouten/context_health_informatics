package analyze.computation;

import java.util.HashMap;

import model.DataField;
import model.DataFieldDouble;
import model.UnsupportedFormatException;

/**
 * This class calculates the maximum of a list of column values.
 * @author Elvan
 *
 */
public abstract class MAX {

	/**
	 * Run the computation.
	 * @param columnValues					the values to run the computation on
	 * @return								the result of the computation
	 * @throws UnsupportedFormatException	format is not supported
	 */
	public static DataField run(HashMap<String, DataField> columnValues) throws UnsupportedFormatException {
		double max = 0;
		
		for (DataField value : columnValues.values()) {
			if (value.getDoubleValue() > max) {
				max = value.getDoubleValue();
			}	
		}

		return new DataFieldDouble(max);

	}

}
