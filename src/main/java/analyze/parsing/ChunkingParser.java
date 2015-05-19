package analyze.parsing;

import computation.ComputationTypeException;

import model.DataField;
import model.SequentialData;
import model.UnsupportedFormatException;

/**
 * This class represents an object that will parse chunking operations.
 * @author Hans Schouten
 *
 */
public class ChunkingParser implements SubParser {

	@Override
	public SequentialData parseOperation(String operation, SequentialData data) {
		String[] splitted = operation.split(" ", 2);
		String operator = splitted[0];
		//String operation = splitted[1];
		
		return data;
	}

	@Override
	public void parseComputation(String computation, SequentialData data)
			throws ComputationTypeException, UnsupportedFormatException {
		// TODO Auto-generated method stub
		return null;
	}

}
