package analyze.parsing;

import analyze.computation.*;
import model.*;

/**
 * This class represents an object that will parse computing operations.
 * @author Elvan
 *
 */
public class ComputingParser implements SubParser {

	/**
	 * This variable stores the column name on which the computation is called.
	 */
	protected String colname;

	/**
	 * This variable stores the kind of computation.
	 */
	protected String computation;

	// This does not yet work for multiple columns!

	@Override
	public SequentialData parseOperation(String operation, SequentialData data) throws UnsupportedFormatException {

			String[] splitted = operation.split("\\(", 2);
			computation = splitted[0];
			String column = splitted[1];

			String[] colsplitted = column.split("\\(", 2);
			colname = colsplitted[1];
			colname = colname.substring(0, colname.length() - 2);

			Computer comp = new Computer(computation, colname, data);

			SequentialData result = new SequentialData();
			Record rec;
			try {
				rec = new Record(DateUtils.parseDate(
						"1111/11/11",
						"yyyy/mm/DD"));
				rec.put(colname, comp.compute(computation));

				result.add(rec);
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return result;
	}


}