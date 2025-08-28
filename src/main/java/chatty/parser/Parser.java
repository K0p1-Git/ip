package chatty.parser;

import chatty.exceptions.ChattyException;
import chatty.exceptions.MalformedArgumentsException;
import chatty.exceptions.UnknownCommandException;

public class Parser {

    /**
     * Parses the user input and returns a Parsed object containing the command and arguments.
     *
     * @param input the user input to be parsed.
     * @return a Parsed object containing the command and arguments.
     * @throws ChattyException if the input is empty or the command is not recognized.
     * @see Parsed
     */
    public static Parsed parse(String input) throws ChattyException {
        if (input.isEmpty()) {
            throw new UnknownCommandException("");
        }

        String[] parts = input.split(" ", 2);
        String keyword = parts[0];
        String args = (parts.length > 1) ? parts[1].trim() : "";

        return switch (keyword) {
            case "bye" -> new Parsed(Command.BYE, args);
            case "list" -> new Parsed(Command.LIST, args);
            case "mark" -> new Parsed(Command.MARK, args);
            case "unmark" -> new Parsed(Command.UNMARK, args);
            case "delete" -> new Parsed(Command.DELETE, args);
            case "todo" -> new Parsed(Command.TODO, args);
            case "deadline" -> new Parsed(Command.DEADLINE, args);
            case "event" -> new Parsed(Command.EVENT, args);
            case "find" -> new Parsed(Command.FIND, args);
            default -> throw new UnknownCommandException(input);
        };
    }

    /**
     * Parses the index from the given string and checks if it is within the valid range.
     *
     * @param s the string to parse the index from.
     * @param size the size of the list to check the index against.
     * @return the parsed index.
     * @throws ChattyException if the index is out of range or not a valid integer.
     * @see ChattyException
     * @see Integer#parseInt(String)
     * @see String#isEmpty()
     * @see String#trim()
     */
    public static int parseIndexOrThrow(String s, int size) throws ChattyException {
        if (s == null || s.isEmpty()) {
            throw new ChattyException("Task number is missing.");
        }
        int idx;
        try {
            idx = Integer.parseInt(s) - 1;
        } catch (NumberFormatException ex) {
            throw new ChattyException("Task number must be an integer.");
        }
        if (idx < 0 || idx >= size) {
            throw new ChattyException("Task number out of range.");
        }
        return idx;
    }

    /**
     * Splits the given string into an array of strings based on the "/by" delimiter.
     * The first element of the array is the description, and the second element is the deadline.
     *
     * @param rest the string to split.
     * @return an array of strings containing the description and deadline.
     * @throws MalformedArgumentsException if the string does not contain the "/by" delimiter.
     * @see MalformedArgumentsException
     * @see String#indexOf(String)
     * @see String#substring(int, int)
     */
    public static String[] splitDeadlineArgs(String rest) throws MalformedArgumentsException {
        int at = rest.indexOf("/by");
        if (at == -1) {
            throw new MalformedArgumentsException("deadline <desc> /by dd-MM-yyyy HHmm");
        }
        String desc = rest.substring(0, at).trim();
        String by = rest.substring(at + 3).trim();
        if (desc.isEmpty() || by.isEmpty()) {
            throw new MalformedArgumentsException("deadline <desc> /by dd-MM-yyyy HHmm");
        }
        return new String[]{desc, by};
    }

    /**
     * Splits the given string into an array of strings based on the "/from" and "/to" delimiters.
     * The first element of the array is the description, the second element is the start time
     * , and the third element is the end time.
     *
     * @param rest the string to split.
     * @return an array of strings containing the description, start time, and end time.
     * @throws MalformedArgumentsException if the string does not contain the "/from" or "/to
     * delimiters or if the delimiters are not in the correct order.
     * @see MalformedArgumentsException
     * @see String#indexOf(String)
     * @see String#substring(int, int)
     */
    public static String[] splitEventArgs(String rest) throws MalformedArgumentsException {
        int fromIdx = rest.indexOf("/from");
        int toIdx = rest.indexOf("/to");
        if (fromIdx == -1 || toIdx == -1 || toIdx <= fromIdx) {
            throw new MalformedArgumentsException("event <desc> /from dd-MM-yyyy HHmm /to dd-MM-yyyy HHmm");
        }
        String desc = rest.substring(0, fromIdx).trim();
        String from = rest.substring(fromIdx + 5, toIdx).trim();
        String to = rest.substring(toIdx + 3).trim();
        if (desc.isEmpty() || from.isEmpty() || to.isEmpty()) {
            throw new MalformedArgumentsException("event <desc> /from dd-MM-yyyy HHmm /to dd-MM-yyyy HHmm");
        }
        return new String[]{desc, from, to};
    }

    public enum Command {
        BYE, LIST, MARK, UNMARK, DELETE, TODO, DEADLINE, EVENT, FIND
    }

    public record Parsed(Command cmd, String args) { }
}