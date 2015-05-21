package analyze.condition;

import analyze.AnalyzeException;

/**
 * This exception is used when an script is not validly parsed.
 * @author Matthijs
 *
 */
public class ConditionParseException extends AnalyzeException {

    /**
     * Variable that stores the message of the exception.
     */
    protected String message;

    /**
     * Construct a new ConditionParseException.
     * @param msg   - Message with more specific information.
     */
    public ConditionParseException(String msg) {
        message = msg;
    }

    /**
     * Serial Version ID.
     */
    private static final long serialVersionUID = -4783747540821361225L;

    @Override
    public String getMessage() {
        return "Condition Parse Error: " + message;
    }

}
