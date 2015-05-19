package analyze.parsing;

import computation.ComputationTypeException;

import model.DataField;
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
	 * @param operation		the operation that needs to be performed
	 * @param data			the data to execute the operation on
	 * @return				the result of executing the operation
	 */
	SequentialData parseOperation(String operation, SequentialData data);
	
	/**
	 * Parse the computation on the given data using the given arguments.
	 * @param computation	the operation that needs to be performed
	 * @param data			the data to execute the operation on
	 * @return				the result of executing the computation
	 */
	void parseComputation(String computation, SequentialData data) throws ComputationTypeException, UnsupportedFormatException;
	

	

}
