package chatty.command;

import chatty.task.TaskList;
import chatty.ui.Ui;

/** A command to exit the bot. */
public class ByeCommand implements Command {
    @Override
    public String execute(TaskList tasks, Ui ui) {
        return ui.showBye();
    }
}
