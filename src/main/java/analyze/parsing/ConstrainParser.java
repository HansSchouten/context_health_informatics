package analyze.parsing;

import analyze.condition.ConditionParseException;
import analyze.constraining.Constrainer;
import model.SequentialData;

/**
 * This class parses Constrains constructs.
 *
 */
public class ConstrainParser implements SubParser {

	@Override
	public SequentialData parseOperation(String operation, SequentialData data) {
		String[] splitted = operation.split("WHERE ", 2);

		Constrainer constrainer = new Constrainer();
		SequentialData result = null;
		try {
			result = constrainer.constrain(data, splitted[1]);
		} catch (ConditionParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}
