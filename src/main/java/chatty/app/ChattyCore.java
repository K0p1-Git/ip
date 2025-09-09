package chatty.app;

import java.util.ArrayList;
import java.util.List;

import chatty.exceptions.ChattyException;
import chatty.exceptions.EmptyDescriptionException;
import chatty.exceptions.MalformedArgumentsException;
import chatty.parser.Parser;
import chatty.storage.Storage;
import chatty.task.Deadline;
import chatty.task.Event;
import chatty.task.Task;
import chatty.task.TaskList;
import chatty.task.Todo;
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
            Parser.Parsed p = Parser.parse(input);

            switch (p.cmd()) {
            case BYE:
                return ui.showBye();
            case LIST:
                return ui.showList(tasks);
            case MARK: {
                int idx = Parser.parseIndexOrThrow(p.args(), tasks.size());
                Task t = tasks.get(idx);
                t.mark();
                storage.save(tasks.asList());
                return ui.showMarked(t);
            }
            case UNMARK: {
                int idx = Parser.parseIndexOrThrow(p.args(), tasks.size());
                Task t = tasks.get(idx);
                t.unmark();
                storage.save(tasks.asList());
                return ui.showUnmarked(t);
            }
            case DELETE: {
                if (p.args().isEmpty()) {
                    throw new ChattyException("Task number is missing.");
                }
                int idx = Parser.parseIndexOrThrow(p.args(), tasks.size());
                Task removed = tasks.remove(idx);
                storage.save(tasks.asList());
                return ui.showDeleted(removed, tasks.size());
            }
            case TODO: {
                if (p.args().isEmpty()) {
                    throw new EmptyDescriptionException("a todo");
                }
                Task t = new Todo(p.args());
                tasks.add(t);
                storage.save(tasks.asList());
                return ui.showAdded(t, tasks.size());
            }
            case DEADLINE: {
                if (p.args().isEmpty()) {
                    throw new MalformedArgumentsException("deadline <desc> /by dd-MM-yyyy HHmm");
                }
                String[] parts = Parser.splitDeadlineArgs(p.args());
                Task t = new Deadline(parts[0], parts[1]);
                tasks.add(t);
                storage.save(tasks.asList());
                return ui.showAdded(t, tasks.size());
            }
            case EVENT: {
                if (p.args().isEmpty()) {
                    throw new MalformedArgumentsException("event <desc> /from dd-MM-yyyy HHmm /to dd-MM-yyyy HHmm");
                }
                String[] parts = Parser.splitEventArgs(p.args());
                Task t = new Event(parts[0], parts[1], parts[2]);
                tasks.add(t);
                storage.save(tasks.asList());
                return ui.showAdded(t, tasks.size());
            }
            case FIND: {
                if (p.args().isEmpty()) {
                    throw new MalformedArgumentsException("find <keyword>");
                }
                List<Task> matches = tasks.find(p.args());
                return ui.showMatches(matches);
            }
            default:
                throw new ChattyException("Unknown command encountered: " + p.cmd());
            }

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
