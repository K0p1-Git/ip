public class EmptyDescriptionException extends ChattyException {
    public EmptyDescriptionException(String what) {
        super("The description of " + what + " cannot be empty. "
                + "Usage hints:\n"
                + (what.equals("a todo") ? "  todo <desc>\n"
                : what.equals("a deadline") ? "  deadline <desc> /by <when>\n"
                : what.equals("an event") ? "  event <desc> /from <start> /to <end>\n"
                : ""));
    }
}