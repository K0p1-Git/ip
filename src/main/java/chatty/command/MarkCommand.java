package chatty.command;

import chatty.task.Task;
import chatty.task.TaskList;
import chatty.ui.Ui;

/** Represents a command to mark a task as done. */
public class MarkCommand extends MutatingCommand {
    private final int index;

    /** Constructor for MarkCommand. */
    public MarkCommand(int index) {
        this.index = index;
    }

    @Override
    public String execute(TaskList tasks, Ui ui) {
        Task task = tasks.get(index);
        task.mark();
        return ui.showMarked(task);
    }
}
