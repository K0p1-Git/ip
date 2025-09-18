package chatty.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

import chatty.exceptions.ChattyException;
import chatty.exceptions.MalformedArgumentsException;

/**
 * Represents an event task with a start and end time.
 * An Event object contains a description, a start time, and an end time.
 * The start and end times are stored as LocalDateTime objects.
 * The Event class extends the Task class.
 */
public class Event extends Task {
    private static final DateTimeFormatter FMT = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendPattern("dd-MM-uuuu HHmm")
            .toFormatter()
            .withResolverStyle(ResolverStyle.STRICT);

    private final LocalDateTime from;
    private final LocalDateTime to;

    /**
     * Constructs a new Event object with the specified description, start time, and end time.
     * The start time and end time are parsed from the specified strings using the FMT formatter.
     *
     * @param description the description of the event.
     * @param from the start time of the event.
     * @param to the end time of the event.
     * @throws MalformedArgumentsException if the start time or end time cannot be parsed.
     * @see DateTimeFormatter
     * @see LocalDateTime
     * @see MalformedArgumentsException
     * @see Task
     * @see Task#Task(String)
     */
    public Event(String description, String from, String to) throws ChattyException {
        super(description);
        try {
            LocalDateTime start = LocalDateTime.parse(from, FMT);
            LocalDateTime end   = LocalDateTime.parse(to, FMT);

            // Enforce ordering only: end must NOT be before start (equal allowed).
            if (end.isBefore(start)) {
                throw new MalformedArgumentsException(
                        "/to must not be before /from (dd-MM-yyyy HHmm)");
            }

            this.from = start;
            this.to = end;
        } catch (DateTimeParseException e) {
            throw new MalformedArgumentsException(
                    "event <desc> /from dd-MM-yyyy HHmm /to dd-MM-yyyy HHmm");
        }
    }

    /**
     * Returns the start time of the event.
     *
     * @return the start time of the event.
     */
    public LocalDateTime getFrom() {
        return from;
    }

    /**
     * Returns the end time of the event.
     *
     * @return the end time of the event.
     */
    public LocalDateTime getTo() {
        return to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString()
                + " (from: " + from.format(FMT)
                + " to: " + to.format(FMT) + ")";
    }

    @Override
    public String toDataString() {
        return "E" + super.toDataString()
                + "/-/" + from.format(FMT)
                + "/-/" + to.format(FMT);
    }
}
