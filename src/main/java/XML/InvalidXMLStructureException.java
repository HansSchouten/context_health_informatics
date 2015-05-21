package XML;

public class InvalidXMLStructureException extends Exception {

    /**
     * Serial version ID.
     */
    private static final long serialVersionUID = 864782391521258779L;
    
    /**
     * String containing the message of the exception.
     */
    protected String message;

    /**
     * Construct exception for when the structure is invalid.
     * @param string    - Message to clarify.
     */
    public InvalidXMLStructureException(String string) {
        message = string;
    }

    @Override
    public String toString() {
        return "Invalid structure: " + message;
    }

}
