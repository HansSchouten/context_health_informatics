package analyze.parsing;

import analyze.chunking.ChunkOnPeriod;
import analyze.chunking.ChunkOnValue;
import analyze.chunking.ChunkType;
import analyze.chunking.Chunker;
import analyze.chunking.ChunkingException;
import model.SequentialData;

/**
 * This class represents an object that will parse chunking operations.
 * @author Hans Schouten
 *
 */
public class ChunkingParser implements SubParser {

	@Override
	public SequentialData parseOperation(String operation, SequentialData data) throws ChunkingException {
		Chunker chunker = new Chunker();
		ChunkType chunkType;

		String[] arguments = operation.split(" ", 2);
		String operator = arguments[0];

		if (operator.equals("ON")) {
			if (arguments.length < 2) {
				throw new ChunkingException("No column provided");
			}
			String[] parts = arguments[1].split(" ");
			String columnName = parts[0];
			chunkType = new ChunkOnValue(columnName);

		} else if (operator.equals("PER")) {
			if (arguments.length < 2) {
				throw new ChunkingException("No period length provided");
			}
			String[] parts = arguments[1].split(" ");
			int length = Integer.parseInt(parts[0]);
			chunkType = new ChunkOnPeriod(data, length);

		} else {
			throw new ChunkingException("Use CHUNK ON or CHUNK PER");
		}

		return chunker.chunk(data, chunkType);
	}


}
