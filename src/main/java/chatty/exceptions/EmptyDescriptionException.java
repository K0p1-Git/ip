package chatty.exceptions;

public class EmptyDescriptionException extends ChattyException {
    /**
     * Constructs a new EmptyDescriptionException with the specified detail message.
     *
     * @param what the type of task that has an empty description. (e.g. "todo", "deadline", "event")
     * @see Exception#Exception(String)
     * @see ChattyException#ChattyException(String)
     * @see ChattyException
     * @see Exception
     * @see String
     * @see Throwable
     */
    public EmptyDescriptionException(String what) {
        super("The description of " + what + " cannot be empty. "
                + "Usage hints:\n"
                + (what.equals("a todo") ? "  todo <desc>\n"
                : what.equals("a deadline") ? "  deadline <desc> /by <when>\n"
                : what.equals("an event") ? "  event <desc> /from <start> /to <end>\n"
                : ""));
    }
}