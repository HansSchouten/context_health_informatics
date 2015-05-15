package analyze.condition;

import java.util.HashMap;

import model.DataField;
import model.UnsupportedFormatException;

/**
 * This class contains an operator that is a binary operation.
 * @author Matthijs
 *
 */
public class BinaryOpTerm implements Expression{

    /**
     * This variables stores the operation that needs to be done.
     */
    protected BinaryOperator operator;
    
    /**
     * This variable stores the expression.
     */
    protected Expression expression;
    
    public BinaryOpTerm(BinaryOperator op, Expression expr) {
        operator = op;
        expression = expr;
    }

    @Override
    public DataField evaluate(HashMap<String, DataField> colValues) throws UnsupportedFormatException {
        return operator.apply(expression, colValues);
    }
}      
    
