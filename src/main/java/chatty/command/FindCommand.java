package chatty.command;

import java.util.List;

import chatty.exceptions.ChattyException;
import chatty.task.Task;
import chatty.task.TaskList;
import chatty.ui.Ui;

/** Represents a command to find tasks containing a specific keyword. */
public class FindCommand extends MutatingCommand {
    private final String searchString;

    public FindCommand(String searchString) {
        this.searchString = searchString;
    }

    @Override
    public String execute(TaskList tasks, Ui ui) throws ChattyException {
        List<Task> result = tasks.find(searchString);

        return ui.showMatches(result);
    }
}
