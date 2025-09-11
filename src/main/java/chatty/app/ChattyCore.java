package chatty.app;

import java.util.ArrayList;

import chatty.command.Command;
import chatty.command.CommandFactory;
import chatty.exceptions.ChattyException;
import chatty.parser.Parser;
import chatty.storage.Storage;
import chatty.task.Task;
import chatty.task.TaskList;
import chatty.ui.Ui;

/** Core logic for ChattyBot. */
public class ChattyCore {
    private final Ui ui;
    private final Storage storage;
    private final TaskList tasks;

    /** Constructor for ChattyCore. */
    public ChattyCore() {
        this.ui = new Ui();
        this.storage = new Storage();
        ArrayList<Task> seed = storage.load();
        this.tasks = new TaskList(seed);
    }

    /** Process one line of user input, return bot’s reply. */
    public String process(String input) {
        try {
            Parser.Parsed parsed = Parser.parse(input);
            Command cmd = CommandFactory.from(parsed, tasks);
            String reply = cmd.execute(tasks, ui);

            if (cmd.isMutating()) {
                storage.save(tasks.asList());
            }

            return reply;
        } catch (ChattyException e) {
            return ui.showError(e.getMessage());
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            return ui.showError("Please provide a valid task number within range.");
        }
    }
    /** Shared greeting */
    public String greeting() {
        return ui.showWelcome();
    }
}
