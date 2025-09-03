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

/**
 * Core engine for ChattyBot.
 * - Returns formatted strings (via Ui) for GUI.
 * - GUI calls handleInput(...) with user’s text and gets back bot’s reply string.
 */
public class ChattyEngine {
    private final Storage storage;
    private final TaskList tasks;
    private final chatty.ui.Ui ui; // reusing same Ui for formatting

    /** Initializes ChattyBot with saved tasks. */
    public ChattyEngine() {
        this.ui = new chatty.ui.Ui();
        this.storage = new Storage();
        ArrayList<Task> seed = storage.load();
        this.tasks = new TaskList(seed);
    }

    /** Handles one user input and returns ChattyBot’s reply string. */
    public String handleInput(String input) {
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

    /** For GUI: initial greeting */
    public String getGreeting() {
        return ui.showWelcome();
    }
}
