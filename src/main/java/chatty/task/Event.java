package chatty.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import chatty.exceptions.ChattyException;
import chatty.exceptions.MalformedArgumentsException;

public class Event extends Task {
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm");
    private final LocalDateTime from;
    private final LocalDateTime to;

    public Event(String description, String from, String to) throws ChattyException {
        super(description);
        try {
            this.from = LocalDateTime.parse(from, FMT);
            this.to   = LocalDateTime.parse(to,   FMT);
        } catch (DateTimeParseException e) {
            throw new MalformedArgumentsException("event <desc> /from dd-MM-yyyy HHmm /to dd-MM-yyyy HHmm");
        }
    }

    @Override
    public String toString() {
        return "[E]" + super.toString()
                + " (from: " + from.format(FMT)
                + " to: "   + to.format(FMT) + ")";
    }

    @Override
    public String toDataString() {
        return "E" + super.toDataString()
                + "/-/" + from.format(FMT)
                + "/-/" + to.format(FMT);
    }
}