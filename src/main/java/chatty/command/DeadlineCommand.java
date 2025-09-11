package chatty.command;

import chatty.exceptions.ChattyException;
import chatty.task.Deadline;
import chatty.task.TaskList;
import chatty.ui.Ui;

/** Command to add a deadline. */
public class DeadlineCommand extends MutatingCommand {
    private final String description;
    private final String by;

    /** Constructor for DeadlineCommand. */
    public DeadlineCommand(String description, String by) {
        this.description = description;
        this.by = by;
    }

    @Override
    public String execute(TaskList tasks, Ui ui) throws ChattyException {
        Deadline deadline = new Deadline(description, by);
        tasks.add(deadline);

        return ui.showAdded(deadline, tasks.size());
    }
}
