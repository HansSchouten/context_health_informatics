package graphs;

/**
 * This exception is thrown when something goes wrong creating a graph.
 * @author Matthijs
 *
 */
public class GraphException extends Exception {

    /** Serial version ID. */
    private static final long serialVersionUID = 1560298705421973117L;

    /** This variable stores the message of the string. */
    protected String message;

    /**
     * Construct a graphException with an errormessage.
     * @param errormessage  - Message that makes the error more clear.
     */
    public GraphException(String errormessage) {
        message = errormessage;
    }
   
    @Override
    public String getMessage() {
        return message;
    }
}
