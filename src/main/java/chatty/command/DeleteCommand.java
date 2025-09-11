package chatty.command;

import chatty.task.Task;
import chatty.task.TaskList;
import chatty.ui.Ui;

/** Command to delete a task. */
public class DeleteCommand extends MutatingCommand {
    private final int index;

    public DeleteCommand(int index) {
        this.index = index;
    }

    @Override
    public String execute(TaskList tasks, Ui ui) {
        Task removedTask = tasks.remove(index);

        return ui.showDeleted(removedTask, tasks.size());
    }
}
