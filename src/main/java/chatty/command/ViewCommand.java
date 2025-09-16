package chatty.command;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import chatty.task.Deadline;
import chatty.task.Event;
import chatty.task.Task;
import chatty.task.TaskList;
import chatty.ui.Ui;

/** Command to view a day's schedule (morning to night). */
public final class ViewCommand implements Command {
    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern("HHmm");

    private final LocalDate day;

    /**
     * Constructs a ViewCommand object.
     *
     * @param day The day to view.
     */
    public ViewCommand(LocalDate day) {
        this.day = day;
    }

    @Override
    public String execute(TaskList tasks, Ui ui) {
        LocalDateTime startOfDay = day.atStartOfDay();
        LocalDateTime endOfDay = day.atTime(LocalTime.MAX);

        // Collect timed entries from tasks
        List<Entry> entries = new ArrayList<>();

        for (Task t : tasks.asList()) {
            if (t instanceof Deadline d) {
                if (d.getBy().toLocalDate().equals(day)) {
                    entries.add(new Entry(d.getDescription(), d.getBy(), d.getBy(), "[D] ", d.getIsDone()));
                }
            } else if (t instanceof Event e) {
                if (!e.getTo().isBefore(startOfDay) && !e.getFrom().isAfter(endOfDay)) {
                    LocalDateTime s = e.getFrom().isBefore(startOfDay) ? startOfDay : e.getFrom();
                    LocalDateTime en = e.getTo().isAfter(endOfDay) ? endOfDay : e.getTo();
                    entries.add(new Entry(e.getDescription(), s, en, "[E] ", e.getIsDone()));
                }
            }
        }

        // Sort: by start time ascending, then by description (case-insensitive)
        entries.sort(Comparator
                .comparing((Entry en) -> en.start)
                .thenComparing(en -> en.description.toLowerCase()));

        // Format output
        StringBuilder sb = new StringBuilder();
        sb.append("Schedule for ").append(day.format(DATE_FMT)).append(System.lineSeparator());
        if (entries.isEmpty()) {
            sb.append("(No timed tasks on this day)");
            return sb.toString();
        }

        buildOutputString(entries, sb);

        return sb.toString();
    }

    /**
     * Builds the output string for the schedule.
     *
     * @param entries The list of entries to be included in the schedule.
     * @param sb      The StringBuilder to append the output string to.
     */
    private static void buildOutputString(List<Entry> entries, StringBuilder sb) {
        // Build output string
        for (Entry en : entries) {
            String status = en.done ? "[X]" : "[ ]";

            if (en.isInstant()) {
                // Deadline: single time point
                sb.append(status).append(" ")
                        .append(en.start.format(TIME_FMT))
                        .append("  ")
                        .append(en.type)
                        .append(en.description)
                        .append(" (by ")
                        .append(en.start.format(TIME_FMT))
                        .append(")")
                        .append(System.lineSeparator());
            } else {
                // Event: time range
                sb.append(status).append(" ")
                        .append(en.start.format(TIME_FMT))
                        .append("-")
                        .append(en.end.format(TIME_FMT))
                        .append("  ")
                        .append(en.type)
                        .append(en.description)
                        .append(System.lineSeparator());
            }
        }
    }

    /** Local struct for schedule entries. */
    private static final class Entry {
        final String description;
        final LocalDateTime start;
        final LocalDateTime end;
        final String type; // "[D] " or "[E] "
        final boolean done;

        /**
         * Constructs a new Entry object with the specified parameters.
         *
         * @param description the description of the entry.
         * @param start the start time of the entry.
         * @param end the end time of the entry.
         * @param type the type of the entry.
         * @param done the completion status of the entry.
         */
        Entry(String description, LocalDateTime start, LocalDateTime end, String type, boolean done) {
            this.description = description;
            this.start = start;
            this.end = end;
            this.type = type;
            this.done = done;
        }

        /**
         * Checks if the entry is an instant event.
         *
         * @return true if the entry is an instant event, false otherwise.
         */
        public boolean isInstant() {
            return start.equals(end);
        }
    }
}
