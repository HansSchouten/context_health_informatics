package analyze.computation;

import java.util.HashMap;

import model.DataField;
import model.DataFieldInt;
import model.UnsupportedFormatException;

/**
 * This class calculates the count of a list of column values.
 * @author Elvan
 *
 */
public abstract class COUNT {

	/**
	 * Run the computation.
	 * @param columnValues					the values to run the computation on
	 * @return								the result of the computation
	 * @throws UnsupportedFormatException	format is not supported
	 */
	public static DataField run(HashMap<String, DataField> columnValues) throws UnsupportedFormatException {
		int count = columnValues.size();

		return new DataFieldInt(count);

	}

}
