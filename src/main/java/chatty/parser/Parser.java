package chatty.parser;

import chatty.exceptions.ChattyException;
import chatty.exceptions.MalformedArgumentsException;
import chatty.exceptions.UnknownCommandException;

public class Parser {

    /** Parse the first word into a command; keep the rest as raw args. */
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
            default -> throw new UnknownCommandException(input);
        };
    }

    /** Parse 1-based index with your existing error messages. */
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

    /** Split "deadline <desc> /by <when>" with your Level-8 format in usage. */
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

    /** Split "event <desc> /from <start> /to <end>" with your Level-8 format in usage. */
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
        BYE, LIST, MARK, UNMARK, DELETE, TODO, DEADLINE, EVENT
    }

    public record Parsed(Command cmd, String args) { }
}