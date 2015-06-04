package analyze.condition;

import java.util.HashMap;

import model.Record;
import model.UnsupportedFormatException;
import model.datafield.DataField;
import model.datafield.DataFieldBoolean;
import model.datafield.DataFieldDouble;

/**
 * This enum Contains all the binary operators.
 * @author Matthijs
 *
 */
public enum BinaryOperator implements Operator {

    /**
     * The plus operation a + b.
     */
    PLUS("+", 4) {

        @Override
        public DataField apply(Expression left, Expression right, Record record)
                throws UnsupportedFormatException {
            double result = left.evaluate(record).getDoubleValue()
                    + right.evaluate(record).getDoubleValue();
            return new DataFieldDouble(result);
        }
    },

    /**
     * This minus operation a - b.
     */
    MIN("-", 3) {

        @Override
        public DataField apply(Expression left, Expression right, Record record)
                throws UnsupportedFormatException {
            double result = left.evaluate(record).getDoubleValue() - right.evaluate(record).getDoubleValue();
            return new DataFieldDouble(result);
        }
    },

    /**
     * The and operation a && b.
     */
    AND("and", 2) {

        @Override
        public DataField apply(Expression left, Expression right, Record record)
                throws UnsupportedFormatException {
            boolean result = left.evaluate(record).getBooleanValue() && right.evaluate(record).getBooleanValue();
            return new DataFieldBoolean(result);
        }
    },

    /** The multiplication operation.
     */
    MULTIPLY("*", 5) {

        @Override
        public DataField apply(Expression left, Expression right, Record record)
                throws UnsupportedFormatException {
            double result = left.evaluate(record).getDoubleValue() * right.evaluate(record).getDoubleValue();
            return new DataFieldDouble(result);
        }
    },

    /** The division operation.
     */
    DIVIDED_BY("/", 5) {

        @Override
        public DataField apply(Expression left, Expression right, Record record)
                throws UnsupportedFormatException {
            double result = left.evaluate(record).getDoubleValue() / right.evaluate(record).getDoubleValue();
            return new DataFieldDouble(result);
        }
    },

    /**
     * The or operation a || b.
     */
    OR("or", 2) {

        @Override
        public DataField apply(Expression left, Expression right, Record record)
                throws UnsupportedFormatException {
            boolean result = left.evaluate(record).getBooleanValue() || right.evaluate(record).getBooleanValue();
            return new DataFieldBoolean(result);
        }
    },

    /**
     * The equal operation a == b.
     */
    EQUAL("=", 1) {

        @Override
        public DataField apply(Expression left, Expression right, Record record)
                throws UnsupportedFormatException {
            boolean result = left.evaluate(record).equals(right.evaluate(record));
            return new DataFieldBoolean(result);
        }
    },

    /**
     * The greater than operation a > b.
     */
    GREATER(">", 2) {

        @Override
        public DataField apply(Expression left, Expression right, Record record)
                throws UnsupportedFormatException {
            boolean result = left.evaluate(record).getDoubleValue() > right.evaluate(record).getDoubleValue();
            return new DataFieldBoolean(result);
        }
    },

    /**
     * The smaller than operation a < b.
     */
    SMALLER("<", 2) {

        @Override
        public DataField apply(Expression left, Expression right, Record record)
                throws UnsupportedFormatException {
            boolean result = left.evaluate(record).getDoubleValue() < right.evaluate(record).getDoubleValue();
            return new DataFieldBoolean(result);
        }
    },

    /**
     * The smaller or equal operation a <= b.
     */
    SEQ("<=", 2) {

        @Override
        public DataField apply(Expression left, Expression right, Record record)
                throws UnsupportedFormatException {
            boolean result = left.evaluate(record).getDoubleValue() <= right.evaluate(record).getDoubleValue();
            return new DataFieldBoolean(result);
        }
    },

    /**
     * The greater or equal operation a >= b.
     */
    GEQ(">=", 2) {

        @Override
        public DataField apply(Expression left, Expression right, Record record)
                throws UnsupportedFormatException {
            boolean result = left.evaluate(record).getDoubleValue() >= right.evaluate(record).getDoubleValue();
            return new DataFieldBoolean(result);
        }
    },

    /**
     * The not equal operation a != b.
     */
    NEQ("!=", 1) {

        @Override
        public DataField apply(Expression left, Expression right, Record record)
                throws UnsupportedFormatException {
            boolean result = BinaryOperator.EQUAL.apply(left, right, record).getBooleanValue();
            return new DataFieldBoolean(!result);
        }
    },

    /**
     * The No Operation operator.
     */
    NOOP("NOOP", 100);

    /**
     * This class stores all the operators.
     */
    private static HashMap<String, BinaryOperator> operators;

    /**
     * This variable stores the maximal length of the operators.
     */
    private static int maxLength;

    /**
     * This variable stores the name of the BinaryOperator.
     */
    private String name;

    /**
     * This varaible is used to store the priority of the operator.
     */
    private int priority;

    /**
     * Constructs an BinayOperator.
     * @param nm   - Name of the operator.
     * @param prio - Priority of the operator.
     */
    private BinaryOperator(String nm, int prio) {
        name = nm;
        priority = prio;
        mapOperator(name);
    }

    /**
     * This method maps all the operators in an HashMap.
     * @param nm      - Name of the operator.
     */
    private void mapOperator(String nm) {

        if (operators == null) {
            operators = new HashMap<String, BinaryOperator>();
        }

        if (nm.length() > maxLength) {
            maxLength = nm.length();
        }

        operators.put(nm, this);
    }

    /**
     * This method should apply the operator.
     * @param left      - Left side of the operator.
     * @param right     - Right side of the operator.
     * @param record    - Record to evaluate with.
     * @return          - Return data field with the result.
     * @throws UnsupportedFormatException   - Thrown when format is not right.
     */
    public DataField apply(Expression left, Expression right, Record record) throws UnsupportedFormatException {
        throw new UnsupportedOperationException("The operation you want to perform is not yet supported.");
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * Get an operator by its string key.
     * @param op    - string that represents an operator.
     * @return      - The operator.
     */
    public static BinaryOperator getOperator(String op) {
        return operators.get(op);
    }

    /**
     * Check if an operator is supported or not.
     * @param name      - Name of the operator.
     * @return          - True if the operator exist.
     */
    public static boolean isSupportedOperator(String name) {
        return operators.containsKey(name);
    }

    /**
     * This method returns the maximal length op the operators.
     * @return  - Integer that contains the maximum length of the operators.
     */
    public static int maxOperatorLength() {
        return maxLength;
    }
}
