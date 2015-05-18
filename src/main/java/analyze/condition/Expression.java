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
     * @return
     * @throws UnsupportedFormatException 
     */
    public DataField evaluate(Record record) throws UnsupportedFormatException;
}
