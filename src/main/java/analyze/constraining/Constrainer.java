package analyze.constraining;

import analyze.condition.Condition;
import analyze.condition.ConditionParseException;
import model.Record;
import model.SequentialData;

/**
 * 	This class represents an object hat contrains the data.
 *
 */
public class Constrainer {

	/**
	 * This method constrains a dataset
	 * @param data
	 * @param operation
	 * @return
	 * @throws ConditionParseException
	 */
	public SequentialData constrain(SequentialData data, String operation) throws ConditionParseException {
		Condition condition = new Condition(operation);
		SequentialData constrained = new SequentialData();
		System.out.println(operation);
		for (Record record: data) {
			if (condition.evaluateWithRecord(record)) {
				constrained.add(record);
			}
		}

		return constrained;
	}
}
