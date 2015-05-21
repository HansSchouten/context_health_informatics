package analyze;

/**
 * This class represents an exception that is thrown when analyse is done on the null object.
 * @author Matthijs
 *
 */
public class EmptyDataSetException extends AnalyzeException {

    /**
     * Serial version ID.
     */
    private static final long serialVersionUID = -2861439423205822092L;

    @Override
    public String getMessage() {
        return "The dataset you are trying to analyse, is not set.";
    }
}
