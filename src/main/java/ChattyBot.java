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

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>();

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
            } else if (input.equals("list")) {
                line();
                System.out.println(" Here are the tasks in your list:");
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println(" " + (i + 1) + ". " + tasks.get(i));
                }
                line();

            } else if (input.startsWith("mark ")) {
                try {
                    int idx = Integer.parseInt(input.substring(5).trim()) - 1;
                    Task t = tasks.get(idx);
                    t.mark();
                    line();
                    System.out.println(" Nice! I've marked this task as done:");
                    System.out.println("   " + t);
                    line();
                } catch (Exception e) {
                    line();
                    System.out.println(" [Error] Please provide a valid task number to mark.");
                    line();
                }

            } else if (input.startsWith("unmark ")) {
                try {
                    int idx = Integer.parseInt(input.substring(7).trim()) - 1;
                    Task t = tasks.get(idx);
                    t.unmark();
                    line();
                    System.out.println(" OK, I've marked this task as not done yet:");
                    System.out.println("   " + t);
                    line();
                } catch (Exception e) {
                    line();
                    System.out.println(" [Error] Please provide a valid task number to unmark.");
                    line();
                }

            } else if (input.startsWith("todo ")) {
                String desc = input.substring(5).trim();
                if (desc.isEmpty()) {
                    line();
                    System.out.println(" [Error] The description of a todo cannot be empty.");
                    line();
                    continue;
                }
                Task t = new Todo(desc);
                tasks.add(t);
                printAdded(t, tasks.size());

            } else if (input.startsWith("deadline ")) {
                // expected format: deadline <desc> /by <when>
                String rest = input.substring(9).trim();
                int at = rest.indexOf("/by");
                if (at == -1) {
                    line();
                    System.out.println(" [Error] Use: deadline <desc> /by <when>");
                    line();
                    continue;
                }
                String desc = rest.substring(0, at).trim();
                String by = rest.substring(at + 3).trim(); // after "/by"
                if (desc.isEmpty() || by.isEmpty()) {
                    line();
                    System.out.println(" [Error] Use: deadline <desc> /by <when>");
                    line();
                    continue;
                }
                Task t = new Deadline(desc, by);
                tasks.add(t);
                printAdded(t, tasks.size());

            } else if (input.startsWith("event ")) {
                // expected format: event <desc> /from <start> /to <end>
                String rest = input.substring(6).trim();

                int fromIdx = rest.indexOf("/from");
                int toIdx = rest.indexOf("/to");

                if (fromIdx == -1 || toIdx == -1 || toIdx <= fromIdx) {
                    line();
                    System.out.println(" [Error] Use: event <desc> /from <start> /to <end>");
                    line();
                    continue;
                }

                String desc = rest.substring(0, fromIdx).trim();
                String from = rest.substring(fromIdx + 5, toIdx).trim(); // after "/from"
                String to = rest.substring(toIdx + 3).trim();            // after "/to"

                if (desc.isEmpty() || from.isEmpty() || to.isEmpty()) {
                    line();
                    System.out.println(" [Error] Use: event <desc> /from <start> /to <end>");
                    line();
                    continue;
                }

                Task t = new Event(desc, from, to);
                tasks.add(t);
                printAdded(t, tasks.size());

            } else if (!input.isEmpty()) {
                // fallback: treat as a todo if user didn't include command
                Task t = new Todo(input);
                tasks.add(t);
                printAdded(t, tasks.size());
            }
        }

        sc.close();
    }
}
