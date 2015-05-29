package analyze.constraining;

import analyze.condition.Condition;
import analyze.condition.ConditionParseException;
import model.Record;
import model.SequentialData;

/**
 *     This class represents an object hat contrains the data.
 *
 */
public class Constrainer {

    /**
     * This method constrains a dataset record for record.
     * @param data the data to be constrained.
     * @param operation the operation to performed.
     * @return return constrained SequentialData
     * @throws ConditionParseException is thrown as condition can't be parsed
     */
    public SequentialData constrain(SequentialData data, String operation) throws ConditionParseException {
        Condition condition = new Condition(operation);
        SequentialData constrained = new SequentialData();

        for (Record record: data) {
            if (condition.evaluateWithRecord(record)) {
                constrained.add(record);
            }
        }

        return constrained;
    }
}
