package computation;


/**
 * This exception is used when a computation cannot be executed on a particular datafield type
 * @author Elvan
 *
 */

public class ComputationTypeException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1907432791372599909L;
	
	/**
     * Variable that stores the message of the exception
     */
    protected String message;

    /**
     * Construct a new ComputationTypeException
     * @param msg   - Message with more specific information.
     */
    public ComputationTypeException(String msg) {
        message = msg;
    }

 
    		


}




