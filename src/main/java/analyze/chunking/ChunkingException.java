package analyze.chunking;

import analyze.AnalyzeException;

/**
 * This exception is thrown when something goes wrong during the chunking operation.
 * @author Hans Schouten
 *
 */
public class ChunkingException extends AnalyzeException {

    /**
	 * Serial ID.
	 */
	private static final long serialVersionUID = 6387950530885661259L;

	/**
     * This variable stores the message of the exception.
     */
    protected String message;

    /**
     * Construct an exception that contains a message.
     * @param string    - Messages of the exception.
     */
    public ChunkingException(String string) {
        message = string;
    }

	@Override
	public String getMessage() {
		return message;
	}

}
