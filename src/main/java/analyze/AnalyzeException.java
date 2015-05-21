package analyze;

/**
 * This class represents all the exceptions that could be thrown during analysing.
 * @author Matthijs
 *
 */
public abstract class AnalyzeException extends Exception {

    /**
     * Serial Version ID.
     */
    private static final long serialVersionUID = -8467744384382792679L;

    /**
     * This method should return the message of the exception.
     * @return      - Message that says what is wrong.
     */
    public abstract String getMessage();
}
