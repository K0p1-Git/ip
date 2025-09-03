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
 * CLI entry point for ChattyBot.
 * Uses Ui to format messages and prints them to stdout.
 */
public class ChattyBot {
    private final Ui ui;
    private final Storage storage;
    private final TaskList tasks;

    /**
     * Constructs a new ChattyBot instance.
     * Initializes the UI, storage, and task list.
     */
    public ChattyBot() {
        this.ui = new Ui();
        this.storage = new Storage();
        ArrayList<Task> seed = storage.load();
        this.tasks = new TaskList(seed);
    }

    /**
     * Runs the ChattyBot application.
     * Handles user input and executes commands until the user exits.
     */
    public void run() {
        System.out.println(ui.box(ui.showWelcome()));

        while (true) {
            String input = ui.readCommand();
            try {
                Parser.Parsed p = Parser.parse(input);

                switch (p.cmd()) {
                case BYE: {
                    System.out.println(ui.box(ui.showBye()));
                    ui.close();
                    return;
                }
                case LIST: {
                    System.out.println(ui.box(ui.showList(tasks)));
                    break;
                }
                case MARK: {
                    int idx = Parser.parseIndexOrThrow(p.args(), tasks.size());
                    Task t = tasks.get(idx);
                    t.mark();
                    storage.save(tasks.asList());
                    System.out.println(ui.box(ui.showMarked(t)));
                    break;
                }
                case UNMARK: {
                    int idx = Parser.parseIndexOrThrow(p.args(), tasks.size());
                    Task t = tasks.get(idx);
                    t.unmark();
                    storage.save(tasks.asList());
                    System.out.println(ui.box(ui.showUnmarked(t)));
                    break;
                }
                case DELETE: {
                    if (p.args().isEmpty()) {
                        throw new ChattyException("Task number is missing.");
                    }
                    int idx = Parser.parseIndexOrThrow(p.args(), tasks.size());
                    Task removed = tasks.remove(idx);
                    storage.save(tasks.asList());
                    System.out.println(ui.box(ui.showDeleted(removed, tasks.size())));
                    break;
                }
                case TODO: {
                    if (p.args().isEmpty()) {
                        throw new EmptyDescriptionException("a todo");
                    }
                    Task t = new Todo(p.args());
                    tasks.add(t);
                    storage.save(tasks.asList());
                    System.out.println(ui.box(ui.showAdded(t, tasks.size())));
                    break;
                }
                case DEADLINE: {
                    if (p.args().isEmpty()) {
                        throw new MalformedArgumentsException("deadline <desc> /by dd-MM-yyyy HHmm");
                    }
                    String[] parts = Parser.splitDeadlineArgs(p.args());
                    Task t = new Deadline(parts[0], parts[1]);
                    tasks.add(t);
                    storage.save(tasks.asList());
                    System.out.println(ui.box(ui.showAdded(t, tasks.size())));
                    break;
                }
                case EVENT: {
                    if (p.args().isEmpty()) {
                        throw new MalformedArgumentsException("event <desc> /from dd-MM-yyyy HHmm /to dd-MM-yyyy HHmm");
                    }
                    String[] parts = Parser.splitEventArgs(p.args());
                    Task t = new Event(parts[0], parts[1], parts[2]);
                    tasks.add(t);
                    storage.save(tasks.asList());
                    System.out.println(ui.box(ui.showAdded(t, tasks.size())));
                    break;
                }
                case FIND: {
                    if (p.args().isEmpty()) {
                        throw new MalformedArgumentsException("find <keyword>");
                    }
                    List<Task> matches = tasks.find(p.args());
                    System.out.println(ui.box(ui.showMatches(matches)));
                    break;
                }
                default: {
                    throw new ChattyException("Unknown command encountered: " + p.cmd());
                }
                }
            } catch (ChattyException e) {
                System.out.println(ui.box(ui.showError(e.getMessage())));
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                System.out.println(ui.box(ui.showError("Please provide a valid task number within range.")));
            }
        }
    }

    public static void main(String[] args) {
        new ChattyBot().run();
    }
}
