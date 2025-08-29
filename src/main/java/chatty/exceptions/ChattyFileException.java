package chatty.exceptions;

/**
 * Represents an exception specific to the Chatty application.
 * This exception is thrown when an error occurs within the Chatty application
 * and provides a custom error message.
 *
 * @see Exception
 */
public class ChattyFileException extends ChattyException {
    /**
     * Constructs a new ChattyFileException with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method.
     * @see Exception#Exception(String)
     * @see ChattyException#ChattyException(String)
     * @see ChattyException
     * @see Exception
     * @see String
     * @see Throwable
     */
    public ChattyFileException(String message) {
        super(message);
    }
}
