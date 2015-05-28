package analyze.parsing;

import analyze.AnalyzeException;
import analyze.constraining.Constrainer;
import model.SequentialData;

/**
 * This class parses Constrains constructs.
 *
 */
public class ConstrainParser implements SubParser {

	@Override
	public SequentialData parseOperation(String operation, SequentialData data) throws AnalyzeException {
		String[] splitted = operation.split("WHERE ", 2);

		Constrainer constrainer = new Constrainer();
		return constrainer.constrain(data, splitted[1]);
	}

}
