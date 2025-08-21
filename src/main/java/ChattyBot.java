import java.util.ArrayList;
import java.util.Scanner;

public class ChattyBot {
    private static void line() {
        System.out.println("____________________________________________________________");
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
                    System.out.println(" Oops! Please provide a valid task number to mark.");
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
                    System.out.println(" Oops! Please provide a valid task number to unmark.");
                    line();
                }
            } else if (!input.isEmpty()) {
                Task t = new Task(input);
                tasks.add(t);
                line();
                System.out.println(" added: " + input);
                line();
            }
        }

        sc.close();
    }
}
