import java.util.ArrayList;
import java.util.Scanner;

public class ChattyBot {
    private static void line() {
        System.out.println("____________________________________________________________");
    }

    private static void printAdded(Task t, int count) {
        line();
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + t);
        System.out.println(" Now you have " + count + " tasks in the list.");
        line();
    }

    private static void errorBox(String message) {
        line();
        System.out.println(" [Error] " + message);
        line();
    }

    private static int parseIndex(String s, int size) throws ChattyException {
        if (s.isEmpty()) throw new ChattyException("Task number is missing.");
        int idx;
        try {
            idx = Integer.parseInt(s) - 1;
        } catch (NumberFormatException ex) {
            throw new ChattyException("Task number must be an integer.");
        }
        if (idx < 0 || idx >= size) {
            throw new ChattyException("Task number out of range.");
        }
        return idx;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Storage storage = new Storage();          // Level 7: storage
        ArrayList<Task> tasks = storage.load();   // Load on startup

        line();
        System.out.println(" Hello! I'm ChattyBot");
        System.out.println(" What can I do for you?");
        line();

        while (true) {
            String input = sc.nextLine().trim();

            if (input.equals("bye")) {
                line();
                System.out.println(" Bye. Hope to see you again soon!");
                line();
                break;
            }

            try {
                if (input.equals("list")) {
                    line();
                    System.out.println(" Here are the tasks in your list:");
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println(" " + (i + 1) + ". " + tasks.get(i));
                    }
                    line();

                } else if (input.startsWith("mark ")) {
                    int idx = parseIndex(input.substring(5).trim(), tasks.size());
                    Task t = tasks.get(idx);
                    t.mark();
                    storage.save(tasks);          // persist
                    line();
                    System.out.println(" Nice! I've marked this task as done:");
                    System.out.println("   " + t);
                    line();

                } else if (input.startsWith("unmark ")) {
                    int idx = parseIndex(input.substring(7).trim(), tasks.size());
                    Task t = tasks.get(idx);
                    t.unmark();
                    storage.save(tasks);          // persist
                    line();
                    System.out.println(" OK, I've marked this task as not done yet:");
                    System.out.println("   " + t);
                    line();

                } else if (input.equals("delete")) {
                    // Explicitly handle bare 'delete' with a clear error
                    throw new ChattyException("Task number is missing.");

                } else if (input.startsWith("delete ")) {
                    int idx = parseIndex(input.substring(7).trim(), tasks.size());
                    Task removed = tasks.remove(idx);
                    storage.save(tasks);          // persist
                    line();
                    System.out.println(" Noted. I've removed this task:");
                    System.out.println("   " + removed);
                    System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
                    line();

                } else if (input.equals("todo")) {
                    throw new EmptyDescriptionException("a todo");

                } else if (input.startsWith("todo ")) {
                    String desc = input.substring(5).trim();
                    if (desc.isEmpty()) throw new EmptyDescriptionException("a todo");
                    Task t = new Todo(desc);
                    tasks.add(t);
                    storage.save(tasks);          // persist
                    printAdded(t, tasks.size());

                } else if (input.equals("deadline")) {
                    throw new MalformedArgumentsException("deadline <desc> /by dd-MM-yyyy HHmm");

                } else if (input.startsWith("deadline ")) {
                    String rest = input.substring(9).trim();
                    int at = rest.indexOf("/by");
                    if (at == -1) {
                        throw new MalformedArgumentsException("deadline <desc> /by dd-MM-yyyy HHmm");
                    }
                    String desc = rest.substring(0, at).trim();
                    String by = rest.substring(at + 3).trim();
                    if (desc.isEmpty() || by.isEmpty()) {
                        throw new MalformedArgumentsException("deadline <desc> /by dd-MM-yyyy HHmm");
                    }
                    Task t = new Deadline(desc, by);
                    tasks.add(t);
                    storage.save(tasks);          // persist
                    printAdded(t, tasks.size());

                } else if (input.equals("event")) {
                    throw new MalformedArgumentsException("event <desc> /from dd-MM-yyyy HHmm /to dd-MM-yyyy HHmm");

                } else if (input.startsWith("event ")) {
                    String rest = input.substring(6).trim();
                    int fromIdx = rest.indexOf("/from");
                    int toIdx = rest.indexOf("/to");
                    if (fromIdx == -1 || toIdx == -1 || toIdx <= fromIdx) {
                        throw new MalformedArgumentsException("event <desc> /from dd-MM-yyyy HHmm /to dd-MM-yyyy HHmm");
                    }
                    String desc = rest.substring(0, fromIdx).trim();
                    String from = rest.substring(fromIdx + 5, toIdx).trim();
                    String to = rest.substring(toIdx + 3).trim();
                    if (desc.isEmpty() || from.isEmpty() || to.isEmpty()) {
                        throw new MalformedArgumentsException("event <desc> /from dd-MM-yyyy HHmm /to dd-MM-yyyy HHmm");
                    }
                    Task t = new Event(desc, from, to);
                    tasks.add(t);
                    storage.save(tasks);          // persist
                    printAdded(t, tasks.size());

                } else {
                    throw new UnknownCommandException(input);
                }

            } catch (ChattyFileException e) {
                // I/O related problems wrapped in ChattyFileException
                errorBox(e.getMessage());
            } catch (ChattyException e) {
                errorBox(e.getMessage());
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                errorBox("Please provide a valid task number within range.");
            }
        }

        sc.close();
    }
}