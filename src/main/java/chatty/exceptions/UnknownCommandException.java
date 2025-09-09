package chatty.exceptions;

/**
 * Represents an exception specific to the Chatty application.
 * This exception is thrown when an error occurs within the Chatty application
 * and provides a custom error message.
 *
 * @see Exception
 */
public class UnknownCommandException extends ChattyException {
    /**
     * Constructs a new UnknownCommandException with the specified detail message.
     *
     * @param input the input that was not recognized as a valid command.
     * @see Exception#Exception(String)
     * @see ChattyException#ChattyException(String)
     * @see ChattyException
     * @see Exception
     * @see String
     * @see Throwable
     */
    public UnknownCommandException(String input) {
        super("I'm sorry, I don't recognize the command: \"" + input + "\"" + "\n\n"
            + "Here are the available commands:\n"
            + " 1. list\n"
            + " 2. mark <index>\n"
            + " 3. unmark <index>\n"
            + " 4. delete <index>\n"
            + " 5. todo <description>\n"
            + " 6. deadline <description> /by <dd-MM-yyyy HHmm>\n"
            + " 7. event <description> /from <dd-MM-yyyy HHmm> /to <dd-MM-yyyy HHmm>\n"
            + " 8. find <keyword>\n"
            + " 9. bye (EXIT)");
    }
}
