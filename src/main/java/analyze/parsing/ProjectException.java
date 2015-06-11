package analyze.parsing;

import analyze.AnalyzeException;

/**
 * This exception is thrown when an projection is not possible.
 * @author Matthijs
 *
 */
public class ProjectException extends AnalyzeException {

    /** Serial version ID. */
    private static final long serialVersionUID = -1556808786775775093L;

    /** This variable stores the message of the exception. */
    protected String message;

    /**
     * Construct a projectException with a detailed message.
     * @param msg       - Message of the exception.
     */
    public ProjectException(String msg) {
        message = msg;
    }

    @Override
    public String getMessage() {
        // TODO Auto-generated method stub
        return null;
    }
}
