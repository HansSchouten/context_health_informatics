package analyze.parsing;

import analyze.AnalyzeException;

/**
 * This exception is thrown when somethings goes wrong during parsing.
 * @author Hans Schouten
 *
 */
public class ParseException extends AnalyzeException {

    /**
     * Serial Version ID. 
     */
    private static final long serialVersionUID = -3912087633470765965L;
    
    /**
     * Variable that stores the message of the exception.
     */
    protected String message;

    /**
     * Construct a new ParseException.
     * @param msg   - Message with more specific information.
     */
    public ParseException(String msg) {
        message = msg;
    }

    @Override
    public String getMessage() {
        return "Parse Error: " + message;
    }

}
