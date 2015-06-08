package controller;

/**
 * This exception is thrown when the controller setup goes wrong somewhere.
 * @author Matthijs
 *
 */
public class ControllerSetupException extends Exception {

    /**
     * Construct a ControllerSetupException with a message.
     * @param string        - message of the exception.
     */
    public ControllerSetupException(String string) {
        super(string);
    }

    /**
     * SerialVersion ID.
     */
    private static final long serialVersionUID = 1L;

}

