package analyze.pattern;

import analyze.AnalyzeException;

/**
 * This exception is used to throw an exception while pattern matching.
 * @author Hans Schouten
 *
 */
public class PatternMatcherException extends AnalyzeException {

    /**
     * Serial version-id.
     */
    private static final long serialVersionUID = 3505798208847161170L;

    /**
     * This variable stores a message of the exception.
     */
    private String message;

    /**
     * Construct a PatternMatcherException.
     * @param msg       - Message of the expression.
     */
    public PatternMatcherException(String msg) {
        message = msg;
    }

    @Override
    public String getMessage() {
        return "PatternMatcherException: " + message;
    }

}
