package computation;

import java.util.HashMap;

import model.DataField;
import model.DataFieldInt;
import model.Record;
import model.UnsupportedFormatException;

public abstract class COUNT {

	/**
	 * This class counts the number of records selected
	 * @author Elvan
	 * @throws UnsupportedFormatException 
	 * @throws ComputationTypeException
	 */

	public static DataField run(HashMap<String, DataField> columnValues) throws ComputationTypeException, UnsupportedFormatException {
		
		int count = columnValues.size();

		return new DataFieldInt(count);

	}

}
