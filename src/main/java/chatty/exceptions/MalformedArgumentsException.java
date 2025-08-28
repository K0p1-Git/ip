package chatty.exceptions;

public class MalformedArgumentsException extends ChattyException {
    public MalformedArgumentsException(String usage) {
        super("Invalid or missing arguments.\nUsage: " + usage);
    }
}
