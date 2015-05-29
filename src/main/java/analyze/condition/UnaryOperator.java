package analyze.condition;

import java.util.HashMap;

import analyze.labeling.LabelFactory;
import model.DataField;
import model.DataFieldBoolean;
import model.DataFieldDouble;
import model.EmptyDataField;
import model.Record;
import model.UnsupportedFormatException;

/**
 * This enum Contains all the binary operators.
 * @author Matthijs
 *
 */
public enum UnaryOperator implements Operator {

    /**
     * The not operation not a.
     */
    NOT("not", 5) {

        @Override
        public DataField apply(Expression term, Record record)
                throws UnsupportedFormatException {
            return new DataFieldBoolean(!term.evaluate(record).getBooleanValue());
        }
    },

    /**
     * The column operator, that gets an column.
     */
    COL("COL", 10) {

        @Override
        public DataField apply(Expression term, Record record)
                throws UnsupportedFormatException {
            String result = term.evaluate(record).getStringValue();
            if (record.containsKey(result)) {
                return record.get(result);
            } else {
                return new EmptyDataField();
            }
        }
    },

    /**
     * The min operator, that makes a number negative.
     */
    NEG("NEG", 5) {

        @Override
        public DataField apply(Expression term, Record record)
                throws UnsupportedFormatException {
            return new DataFieldDouble(term.evaluate(record).getDoubleValue() * -1);
        }
    },


    /**
     * The label operator, that checks whether an label is set.
     */
    LABELED("LABELED", 10) {

        @Override
        public DataField apply(Expression term, Record record)
                throws UnsupportedFormatException {
            String labelName = term.evaluate(record).getStringValue();
            Boolean contains = record.containsLabel(LabelFactory.getInstance().getNumberOfLabel(labelName));
            return new DataFieldBoolean(contains);
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
     * This variable stores the maximal lenght of the operators.
     */
    private static int maxLength;

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

        if (operators == null) {
            operators = new HashMap<String, UnaryOperator>();
        }

        if (nm.length() > maxLength) {
            maxLength = nm.length();
        }

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

    /**
     * This method returns the maximal length op the operators.
     * @return  - Integer that contains the maximum length of the operators.
     */
    public static int maxOperatorLength() {
        return maxLength;
    }
}
