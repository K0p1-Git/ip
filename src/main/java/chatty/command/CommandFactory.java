package chatty.command;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

import chatty.exceptions.ChattyException;
import chatty.exceptions.EmptyDescriptionException;
import chatty.exceptions.MalformedArgumentsException;
import chatty.parser.Parser;
import chatty.task.TaskList;

/** Factory class for creating Command objects. */
public final class CommandFactory {

    private static final DateTimeFormatter DATE_FMT =
            new DateTimeFormatterBuilder()
                    .parseCaseInsensitive()
                    .appendPattern("dd-MM-uuuu")
                    .toFormatter()
                    .withResolverStyle(ResolverStyle.STRICT);

    /** Private constructor to prevent instantiation. */
    private CommandFactory() {}

    /**
     * Creates a Command object based on the parsed input.
     *
     * @param p The parsed input.
     * @param tasks The list of tasks.
     * @return The created Command object.
     * @throws ChattyException If the input is invalid.
     * @throws MalformedArgumentsException If the arguments are malformed.
     */
    public static Command from(Parser.Parsed p, TaskList tasks) throws ChattyException {
        switch (p.cmd()) {
        case BYE: {
            return new ByeCommand();
        }
        case LIST: {
            return new ListCommand();
        }
        case MARK: {
            int idx = Parser.parseIndexOrThrow(p.args(), tasks.size());
            return new MarkCommand(idx);
        }
        case UNMARK: {
            int idx = Parser.parseIndexOrThrow(p.args(), tasks.size());
            return new UnmarkCommand(idx);
        }
        case DELETE: {
            if (p.args().isEmpty()) {
                throw new ChattyException("Task number is missing.");
            }
            int idx = Parser.parseIndexOrThrow(p.args(), tasks.size());
            return new DeleteCommand(idx);
        }
        case TODO: {
            if (p.args().isEmpty()) {
                throw new EmptyDescriptionException("a todo");
            }
            return new TodoCommand(p.args());
        }
        case DEADLINE: {
            if (p.args().isEmpty()) {
                throw new MalformedArgumentsException("deadline <desc> /by dd-MM-yyyy HHmm");
            }
            String[] parts = Parser.splitDeadlineArgs(p.args());
            return new DeadlineCommand(parts[0], parts[1]);
        }
        case EVENT: {
            if (p.args().isEmpty()) {
                throw new MalformedArgumentsException("event <desc> /from dd-MM-yyyy HHmm /to dd-MM-yyyy HHmm");
            }
            String[] parts = Parser.splitEventArgs(p.args());
            return new EventCommand(parts[0], parts[1], parts[2]);
        }
        case FIND: {
            if (p.args().isEmpty()) {
                throw new MalformedArgumentsException("find <keyword>");
            }
            return new FindCommand(p.args());
        }
        case VIEW: {
            String arg = p.args();
            if (arg == null || arg.isBlank()) {
                throw new MalformedArgumentsException("view <dd-MM-yyyy>");
            }
            try {
                LocalDate day = LocalDate.parse(arg.trim(), DATE_FMT);
                return new ViewCommand(day);
            } catch (DateTimeParseException e) {
                throw new MalformedArgumentsException("view <dd-MM-yyyy>");
            }
        }
        default:
            throw new ChattyException("Unknown command encountered: " + p.cmd());
        }
    }
}
