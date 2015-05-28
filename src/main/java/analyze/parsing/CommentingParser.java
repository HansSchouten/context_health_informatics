package analyze.parsing;

import model.Record;
import model.SequentialData;
import analyze.AnalyzeException;
import analyze.condition.Condition;

/**
 * This class parses Comment constructs.
 * @author Hans Schouten
 *
 */
public class CommentingParser implements SubParser {

	@Override
	public SequentialData parseOperation(String operation, SequentialData data) throws AnalyzeException {
		String[] splitted = operation.split(" WHERE ", 2);
		String comment = splitted[0];
		Condition condition = new Condition(splitted[1]);

		for (Record record: data) {
			if (condition.evaluateWithRecord(record)) {
				record.addCommentToRecord(comment);
			}
		}

		return data;
	}

}
