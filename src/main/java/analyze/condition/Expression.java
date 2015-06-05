package analyze.condition;

import model.Record;
import model.UnsupportedFormatException;
import model.datafield.DataField;

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
    DataField evaluate(Record record) throws UnsupportedFormatException;
}
