package chatty.exceptions;

/**
 * Represents an exception specific to the Chatty application.
 * This exception is thrown when an error occurs within the Chatty application
 * and provides a custom error message.
 *
 * @see Exception
 */
public class ChattyException extends Exception {
    /**
     * Constructs a new ChattyException with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method.
     * @see Exception#Exception(String)
     */
    public ChattyException(String message) {
        super(message);
    }
}
