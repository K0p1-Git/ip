package chatty.command;

import chatty.task.TaskList;
import chatty.task.Todo;
import chatty.ui.Ui;

/** Command to add a todo. */
public class TodoCommand extends MutatingCommand {
    private final String description;

    public TodoCommand(String description) {
        this.description = description;
    }

    @Override
    public String execute(TaskList tasks, Ui ui) {
        Todo todo = new Todo(description);
        tasks.add(todo);
        return ui.showAdded(todo, tasks.size());
    }
}
