package chatty.command;

import chatty.exceptions.ChattyException;
import chatty.task.TaskList;
import chatty.ui.Ui;

/** A command that can be executed by the bot. */
public interface Command {
    public String execute(TaskList tasks, Ui ui) throws ChattyException;

    /** Whether this command mutates the task list. */
    default boolean isMutating() {
        return false;
    }
}
