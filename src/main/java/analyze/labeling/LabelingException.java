package analyze.labeling;

import analyze.AnalyzeException;

/**
 * This exception is used to get an LabelingException.
 * @author Matthijs
 *
 */
public class LabelingException extends AnalyzeException {

    /**
     * Serial Version ID.
     */
    private static final long serialVersionUID = 5432981602890278253L;

    /**
     * This variable stores a message of the exception.
     */
    private String message;

    /**
     * Construct an LabelingExpression.
     * @param msg       - Message of the expression.
     */
    public LabelingException(String msg) {
        message = msg;
    }

    @Override
    public String getMessage() {
        return "LabelingException: " + message;
    }

}
