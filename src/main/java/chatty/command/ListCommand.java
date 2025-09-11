package chatty.command;

import chatty.task.TaskList;
import chatty.ui.Ui;

/** A command to list all tasks. */
public class ListCommand implements Command {
    @Override
    public String execute(TaskList tasks, Ui ui) {
        return ui.showList(tasks);
    }
}
