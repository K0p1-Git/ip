package chatty.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

import chatty.exceptions.ChattyException;
import chatty.exceptions.MalformedArgumentsException;

/**
 * Represents a task with a deadline. A Deadline object contains a description and a deadline.
 * The deadline is stored as a LocalDateTime object.
 */
public class Deadline extends Task {
    private static final DateTimeFormatter FMT = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendPattern("dd-MM-uuuu HHmm")
            .toFormatter()
            .withResolverStyle(ResolverStyle.STRICT);

    private final LocalDateTime by;

    /**
     * Constructs a new Deadline object with the specified description and deadline.
     * The deadline is parsed from a string in the format "dd-MM-yyyy HHmm".
     * If the deadline is not in the correct format, a MalformedArgumentsException is thrown.
     *
     * @param description the description of the deadline.
     * @param by the deadline in the format "dd-MM-yyyy HHmm".
     * @throws MalformedArgumentsException if the deadline is not in the correct format.
     * @see MalformedArgumentsException
     * @see DateTimeFormatter
     * @see LocalDateTime
     */
    public Deadline(String description, String by) throws ChattyException {
        super(description);
        try {
            this.by = LocalDateTime.parse(by, FMT);
        } catch (DateTimeParseException e) {
            throw new MalformedArgumentsException(
                    "deadline <desc> /by dd-MM-yyyy HHmm");
        }
    }

    /**
     * Returns the deadline of the task.
     *
     * @return the deadline of the task.
     */
    public LocalDateTime getBy() {
        return by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(FMT) + ")";
    }

    @Override
    public String toDataString() {
        return "D" + super.toDataString() + "/-/" + by.format(FMT);
    }
}
