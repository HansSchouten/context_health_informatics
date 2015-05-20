package analyze.parsing;

import model.SequentialData;
import model.UnsupportedFormatException;

/**
 * This interface defines the outline of a SubParser object.
 * @author Hans Schouten
 *
 */
interface SubParser {

	/**
	 * Parse the operation on the given data using the given arguments.
	 * @param operation						the operation that needs to be performed
	 * @param data							the data to execute the operation on
	 * @return								the result of executing the operation
	 * @throws UnsupportedFormatException	the format is not supported
	 */
	SequentialData parseOperation(String operation, SequentialData data) throws UnsupportedFormatException;

}
