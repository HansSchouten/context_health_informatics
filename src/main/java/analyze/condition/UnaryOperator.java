package analyze.condition;

import java.util.HashMap;

import model.DataField;
import model.DataFieldBoolean;
import model.DataFieldInt;
import model.Record;
import model.UnsupportedFormatException;

/**
 * This enum Contains all the binary operators.
 * @author Matthijs
 *
 */
public enum UnaryOperator {

    /**
     * The not operation not a.
     */
    NOT("not", 1) {

        @Override
        public DataField apply(Expression term, Record record) throws UnsupportedFormatException {
            return new DataFieldBoolean(!term.evaluate(record).getBooleanValue());
        }
    },

    /**
     * This unary minus operator -a.
     */
    MIN("-", 1) {

        @Override
        public DataField apply(Expression term, Record record) throws UnsupportedFormatException {
            return new DataFieldInt(-1 * term.evaluate(record).getIntegerValue());
        }
    },

    /**
     * The No Operation operator.
     */
    NOOP("NOOP", 100);

    /**
     * This class stores all the operators.
     */
    private static HashMap<String, UnaryOperator> operators;

    /**
     * This variable stores the name of the BinaryOperator.
     */
    private String name;

    /**
     * This variable stores the priority of the operator.
     */
    private int priority;

    /**
     * Constructs an BinayOperator.
     * @param nm   - Name of the operator.
     * @param prio - Priority of the operator.
     */
    private UnaryOperator(String nm, int prio) {
        name = nm;
        mapOperator(name);
        priority = prio;
    }

    /**
     * This method maps all the operators in an HashMap.
     * @param nm      - Name of the operator.
     */
    private void mapOperator(String nm) {

        if (operators == null)
            operators = new HashMap<String, UnaryOperator>();

        operators.put(nm, this);
    }

    /**
     * Apply this operator to a given set of expression terms.
     * @param expression    - Expression that needs to be applied.
     * @param record        - Record to evaluate with.
     * @return              - Result of the expression.
     * @throws UnsupportedFormatException - thrown when format is not right.
     */
    public DataField apply(Expression expression, Record record) throws UnsupportedFormatException {
        throw new UnsupportedOperationException("this is not supported");
    }

    /**
     * This method returns the priority of the operator.
     * @return      - int containing the priority.
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Get an operator by its string key.
     * @param op    - string that represents an operator.
     * @return      - The operator.
     */
    public static UnaryOperator getOperator(String op) {
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
}
