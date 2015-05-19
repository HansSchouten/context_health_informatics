package analyze.condition;

/**
 * This interface specifies all the methods an operator should implement.
 * @author Matthijs
 *
 */
public interface Operator {

    /**
     * This method should return the priority of the operator.
     * @return      - Integer containing the priority of the operator.
     */
    public int getPriority();
}
