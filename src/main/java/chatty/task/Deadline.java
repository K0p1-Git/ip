package chatty.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import chatty.exceptions.ChattyException;
import chatty.exceptions.MalformedArgumentsException;

public class Deadline extends Task {
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm");
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
            throw new MalformedArgumentsException("deadline <desc> /by dd-MM-yyyy HHmm");
        }
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