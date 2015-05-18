package analyze.condition;

import model.DataField;
import model.Record;
import model.UnsupportedFormatException;

/**
 * This interface forces terms to be able to evaluate.
 * @author Matthijs
 *
 */
public interface Expression {

    /**
     * This method should evaluate the expression.
     * @param record    - record to evaluate with.
     * @return          - Result of the expression.
     * @throws UnsupportedFormatException - Thrown when format is not right.
     */
    public DataField evaluate(Record record) throws UnsupportedFormatException;
}
