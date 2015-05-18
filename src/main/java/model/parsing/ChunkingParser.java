package model.parsing;

import model.SequentialData;

/**
 * This class represents an object that will parse chunking operations.
 * @author Hans Schouten
 *
 */
public class ChunkingParser implements SubParser {

	@Override
	public SequentialData parseOperation(String operation, SequentialData data) {
		return data;
	}

}
