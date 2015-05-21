package analyze.parsing;

import analyze.chunking.Chunker;
import model.SequentialData;

/**
 * This class represents an object that will parse chunking operations.
 * @author Hans Schouten
 *
 */
public class ChunkingParser implements SubParser {

	@Override
	public SequentialData parseOperation(String operation, SequentialData data) {
		Chunker chunker = new Chunker();
		String[] splitted = operation.split(" ", 2);
		String operator = splitted[0];
		String[] parts = splitted[1].split(" ");

		if (operator.equals("ON")) {
			String columnName = parts[0];
		} else if (operator.equals("PER")) {
			int number = Integer.parseInt(parts[0]);
			String unit = parts[1];
		}

		return data;
	}


}
