package chatty.exceptions;

public class UnknownCommandException extends ChattyException {
    public UnknownCommandException(String input) {
        super("I'm sorry, I don't recognize the command: \"" + input + "\"");
    }
}