package analyze.condition;

import java.util.HashMap;

import model.DataField;
import model.DataFieldBoolean;
import model.DataFieldDouble;
import model.Record;
import model.UnsupportedFormatException;

/**
 * This enum Contains all the binary operators.
 * @author Matthijs
 *
 */
public enum BinaryOperator {

    PLUS("+", 10) {

        @Override 
        public DataField apply(Expression left, Expression right, Record record) throws UnsupportedFormatException {
            double result = left.evaluate(record).getDoubleValue() + right.evaluate(record).getDoubleValue();
            return new DataFieldDouble(result);
        }
    },

    MIN("-", 10) {

        @Override 
        public DataField apply(Expression left, Expression right, Record record) throws UnsupportedFormatException {
            double result = left.evaluate(record).getDoubleValue() - right.evaluate(record).getDoubleValue();
            return new DataFieldDouble(result);
        }
    },

    AND("and", 2) {

        @Override 
        public DataField apply(Expression left, Expression right, Record record) throws UnsupportedFormatException {
            boolean result = left.evaluate(record).getBooleanValue() && right.evaluate(record).getBooleanValue();
            return new DataFieldBoolean(result);
        }
    },

    OR("or", 3) {

        @Override 
        public DataField apply(Expression left, Expression right, Record record) throws UnsupportedFormatException {
            boolean result = left.evaluate(record).getBooleanValue() || right.evaluate(record).getBooleanValue();
            return new DataFieldBoolean(result);
        }
    },

    EQUAL("=", 10) {

        @Override 
        public DataField apply(Expression left, Expression right, Record record) throws UnsupportedFormatException {
            boolean result = left.evaluate(record).equals(right.evaluate(record));
            return new DataFieldBoolean(result);
        }
    },

    GREATER(">", 11) {

        @Override 
        public DataField apply(Expression left, Expression right, Record record) throws UnsupportedFormatException {
            boolean result = left.evaluate(record).getDoubleValue() > right.evaluate(record).getDoubleValue();
            return new DataFieldBoolean(result);
        }
    },

    SMALLER("<", 11) {

        @Override 
        public DataField apply(Expression left, Expression right, Record record) throws UnsupportedFormatException {
            boolean result = left.evaluate(record).getDoubleValue() < right.evaluate(record).getDoubleValue();
            return new DataFieldBoolean(result);
        }
    },
    
    SEQ("<=", 11) {

        @Override 
        public DataField apply(Expression left, Expression right, Record record) throws UnsupportedFormatException {
            boolean result = left.evaluate(record).getDoubleValue() <= right.evaluate(record).getDoubleValue();
            return new DataFieldBoolean(result);
        }
    },
    
    GEQ(">=", 11) {

        @Override 
        public DataField apply(Expression left, Expression right, Record record) throws UnsupportedFormatException {
            boolean result = left.evaluate(record).getDoubleValue() >= right.evaluate(record).getDoubleValue();
            return new DataFieldBoolean(result);
        }
    },
    
    NEQ("!=", 10) {
        
        @Override 
        public DataField apply(Expression left, Expression right, Record record) throws UnsupportedFormatException {
            boolean result = BinaryOperator.EQUAL.apply(left, right, record).getBooleanValue();
            return new DataFieldBoolean(!result);
        }
    }
    ;
    
    /**
     * This class stores all the operators.
     */
    private static HashMap<String, BinaryOperator> operators;
    
    /**
     * This variable stores the name of the BinaryOperator.
     */
    private String name;
    
    private int priority;
    
    /**
     * Constructs an BinayOperator.
     * @param nm   - Name of the operator.
     * @param prio - Priority of the operator.
     */
    private BinaryOperator(String nm, int prio) {
        name = nm;
        mapOperator(name);
    }
    
    /**
     * This method maps all the operators in an HashMap
     * @param name      - Name of the operator.
     */
    private void mapOperator(String name) {
        
        if(operators == null)
            operators = new HashMap<String, BinaryOperator>();
        
        operators.put(name, this);
    }
    
    /**
     * Apply this operator to a given set of expression terms.
     * @param expression    - Expression that needs to be applied.
     * @return              - Result of the expression
     * @throws UnsupportedFormatException 
     */
    public DataField apply(Expression left, Expression right, Record record) throws UnsupportedFormatException {
        throw new UnsupportedOperationException("this is not supported");
    }

    /**
     * This method returns the priority of the operator.
     * @return      - int containing the priority.
     */
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
}
