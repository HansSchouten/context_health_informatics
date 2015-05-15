package analyze.condition;

import java.util.HashMap;

import model.DataField;
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
    public DataField evaluate(HashMap<String, DataField> colValues) throws UnsupportedFormatException;
}
