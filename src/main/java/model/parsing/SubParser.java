package model.parsing;

import model.SequentialData;

/**
 * This interface .
 * @author Hans Schouten
 *
 */
interface SubParser {

	/**
	 * Parse the operation on the given data using the given arguments.
	 * @param arguments		the arguments for performing the operation
	 * @param data			the data to execute the operation on
	 * @return				the result of parsing the operation
	 */
	SequentialData parseOperation(String[] arguments, SequentialData data);

}
