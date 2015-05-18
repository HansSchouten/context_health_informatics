package model;

/**
 * This exception is thrown when a datafield cannot be converted.
 * Then the format of the datafield does not support conversion to another format.
 * @author Matthijs
 *
 */
public class UnsupportedFormatException extends Exception {
    
    protected String message;

    /**
     * Construct an exception that contains a message.
     * @param string    - Messages of the exception.
     */
    public UnsupportedFormatException(String string) {
        message = string;
    }

    /**
     * Serial Version ID.
     */
    private static final long serialVersionUID = 1L;

}
