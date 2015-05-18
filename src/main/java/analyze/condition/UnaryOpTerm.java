package analyze.condition;

import model.DataField;
import model.Record;
import model.UnsupportedFormatException;

/**
 * This class contains an operator that is a unary operation.
 * @author Matthijs
 *
 */
public class UnaryOpTerm implements Expression{

    /**
     * This variables stores the operation that needs to be done.
     */
    protected UnaryOperator operator;
    
    /**
     * This variable stores the expression.
     */
    protected Expression expression;
    
    public UnaryOpTerm(UnaryOperator op, Expression expr) {
        operator = op;
        expression = expr;
    }

    @Override
    public DataField evaluate(Record record) throws UnsupportedFormatException {
        return operator.apply(expression, record);
    }
}      
    
