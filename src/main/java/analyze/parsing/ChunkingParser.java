package analyze.parsing;

import model.SequentialData;

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

}
