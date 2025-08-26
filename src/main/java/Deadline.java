import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm");
    private final LocalDateTime by;

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