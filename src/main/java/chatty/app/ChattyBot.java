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

/**
 * Entry point for the ChattyBot application.
 * This class is responsible for initializing the application and handling the main event loop.
 * It uses the Ui, Storage, and TaskList classes to manage user interactions, data persistence, and task management.
 *
 * @author K0p1-Git
 */
public class ChattyBot {

    public static void main(String[] args) {
        Ui ui = new Ui();
        Storage storage = new Storage();
        ArrayList<Task> seed = storage.load();
        TaskList tasks = new TaskList(seed);

        ui.showWelcome();

        while (true) {
            String input = ui.readCommand();

            try {
                Parser.Parsed p = Parser.parse(input);

                switch (p.cmd()) {
                case BYE: {
                    ui.showBye();
                    ui.close();
                    return;
                }
                case LIST: {
                    ui.showList(tasks);
                    break;
                }
                case MARK: {
                    int idx = Parser.parseIndexOrThrow(p.args(), tasks.size());
                    Task t = tasks.get(idx);
                    t.mark();
                    storage.save(tasks.asList());
                    ui.showMarked(t);
                    break;
                }
                case UNMARK: {
                    int idx = Parser.parseIndexOrThrow(p.args(), tasks.size());
                    Task t = tasks.get(idx);
                    t.unmark();
                    storage.save(tasks.asList());
                    ui.showUnmarked(t);
                    break;
                }
                case DELETE: {
                    if (p.args().isEmpty()) {
                        throw new ChattyException("Task number is missing.");
                    }
                    int idx = Parser.parseIndexOrThrow(p.args(), tasks.size());
                    Task removed = tasks.remove(idx);
                    storage.save(tasks.asList());
                    ui.showDeleted(removed, tasks.size());
                    break;
                }
                case TODO: {
                    if (p.args().isEmpty()) {
                        throw new EmptyDescriptionException("a todo");
                    }
                    Task t = new Todo(p.args());
                    tasks.add(t);
                    storage.save(tasks.asList());
                    ui.showAdded(t, tasks.size());
                    break;
                }
                case DEADLINE: {
                    if (p.args().isEmpty()) {
                        throw new MalformedArgumentsException("deadline <desc> /by dd-MM-yyyy HHmm");
                    }
                    String[] parts = Parser.splitDeadlineArgs(p.args());
                    Task t = new Deadline(parts[0], parts[1]); // may throw on bad date -> MalformedArgumentsException
                    tasks.add(t);
                    storage.save(tasks.asList());
                    ui.showAdded(t, tasks.size());
                    break;
                }
                case EVENT: {
                    if (p.args().isEmpty()) {
                        throw new MalformedArgumentsException("event <desc> /from dd-MM-yyyy HHmm /to dd-MM-yyyy HHmm");
                    }
                    String[] parts = Parser.splitEventArgs(p.args());
                    Task t = new Event(parts[0], parts[1], parts[2]); // may throw on bad date -> MalformedArgumentsException
                    tasks.add(t);
                    storage.save(tasks.asList());
                    ui.showAdded(t, tasks.size());
                    break;
                }
                case FIND: { // NEW
                    if (p.args().isEmpty()) {
                        throw new MalformedArgumentsException("find <keyword>");
                    }
                    List<Task> matches = tasks.find(p.args());
                    ui.showMatches(matches);
                    break;
                }
                }

            } catch (ChattyException e) {
                ui.showError(e.getMessage());
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                ui.showError("Please provide a valid task number within range.");
            }
        }
    }
}
