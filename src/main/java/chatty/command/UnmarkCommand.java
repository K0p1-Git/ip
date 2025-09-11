package chatty.command;

import chatty.task.Task;
import chatty.task.TaskList;
import chatty.ui.Ui;

/** Command to unmark a task as done. */
public class UnmarkCommand extends MutatingCommand {
    private final int index;

    /** Constructor for UnmarkCommand. */
    public UnmarkCommand(int index) {
        this.index = index;
    }

    @Override
    public String execute(TaskList tasks, Ui ui) {
        Task task = tasks.get(index);
        task.unmark();
        return ui.showUnmarked(task);
    }
}
