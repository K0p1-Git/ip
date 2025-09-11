package chatty.command;

import chatty.exceptions.ChattyException;
import chatty.task.Event;
import chatty.task.TaskList;
import chatty.ui.Ui;

/** Represents a command to add an event task. */
public class EventCommand extends MutatingCommand {
    private final String description;
    private final String from;
    private final String to;

    /** Constructor for EventCommand. */
    public EventCommand(String description, String from, String s) {
        this.description = description;
        this.from = from;
        this.to = s;
    }

    @Override
    public String execute(TaskList tasks, Ui ui) throws ChattyException {
        Event event = new Event(description, from, to);
        tasks.add(event);

        return ui.showAdded(event, tasks.size());
    }
}
