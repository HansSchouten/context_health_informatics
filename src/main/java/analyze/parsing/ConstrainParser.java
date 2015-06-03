package analyze.parsing;

import analyze.AnalyzeException;
import analyze.constraining.Constrainer;
import model.DataField;
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

    @Override
    public ParseResult parseOperation(String operation, DataField data)
            throws AnalyzeException {
        throw new ParseException("Constraining on a single value is not possible");
    }

}
