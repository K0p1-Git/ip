package chatty.exceptions;

public class MalformedArgumentsException extends ChattyException {
    /**
     * Constructs a new MalformedArgumentsException with the specified detail message.
     *
     * @param usage the usage message to be displayed to the user
     * @see Exception#Exception(String)
     * @see ChattyException#ChattyException(String)
     * @see ChattyException
     * @see Exception
     * @see String
     * @see Throwable
     */
    public MalformedArgumentsException(String usage) {
        super("Invalid or missing arguments.\nUsage: " + usage);
    }
}
