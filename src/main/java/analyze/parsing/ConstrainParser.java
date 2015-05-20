package analyze.parsing;

import model.SequentialData;

public class ConstrainParser implements SubParser {

	@Override
	public SequentialData parseOperation(String operation, SequentialData data) {
		String[] splitted = operation.split(" ", 2);
		String operator = splitted[0];
		//String operation = splitted[1];

		return data;
	}

}
