package analyze.parsing;

import analyze.AnalyzeException;
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
	public SequentialData parseOperation(String operation, SequentialData data) throws AnalyzeException {
		Chunker chunker = new Chunker();
		ChunkType chunkType;

		String[] splitted = operation.split(" ", 2);
		String operator = splitted[0];
		String[] parts = splitted[1].split(" ");

		if (operator.equals("ON")) {
			String columnName = parts[0];
			chunkType = new ChunkOnValue(columnName);

		} else if (operator.equals("PER")) {
			int length = Integer.parseInt(parts[0]);
			chunkType = new ChunkOnPeriod(data, length);

		} else {
			throw new ChunkingException("Use CHUNK ON or CHUNK PER");
		}

		return chunker.chunk(data, chunkType);
	}


}
