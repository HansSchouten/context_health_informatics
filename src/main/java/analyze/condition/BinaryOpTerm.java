package analyze.condition;

import model.DataField;
import model.Record;
import model.UnsupportedFormatException;

/**
 * This class contains an operator that is a Binary operation.
 * @author Matthijs
 *
 */
public class BinaryOpTerm implements Expression {

    /**
     * This variables stores the operation that needs to be done.
     */
    protected BinaryOperator operator;

    /**
     * This variable stores the left expression.
     */
    protected Expression leftexpr;

    /**
     * This variable stores the right expression.
     */
    protected Expression rightexpr;

    /**
     * Construct a BinaryOpTerm containing an operator and expressions.
     * @param op        - Operator to be done.
     * @param left      - Left side of the operator.
     * @param right     - Right side of the operator.
     */

    public BinaryOpTerm(BinaryOperator op, Expression left, Expression right) {
        operator = op;
        leftexpr = left;
        rightexpr = right;
    }


    @Override
    public DataField evaluate(Record record) throws UnsupportedFormatException {
        return operator.apply(leftexpr, rightexpr , record);
    }
}
