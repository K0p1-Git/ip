package chatty.ui;

import java.util.List;
import java.util.Scanner;

import chatty.task.Task;
import chatty.task.TaskList;

/**
 * Handles user interaction. This includes displaying messages to the user and reading user input.
 * The Ui class is responsible for the input/output operations of the application.
 * It uses a Scanner object to read user input from the console.
 * The Ui class is initialized with a Scanner object that reads from the standard input.
 * The Ui class provides methods to display various messages to the user, such as welcome messages,
 * error messages, and task-related messages.
 */
public class Ui {
    private final Scanner sc;

    /**
     * Constructs a new Ui object with a Scanner object that reads from the standard input.
     */

    public Ui() {
        this.sc = new Scanner(System.in);
    }

    /**
     * Displays a welcome message to the user.
     */
    public void showWelcome() {
        line();
        System.out.println(" Hello! I'm ChattyBot");
        System.out.println(" What can I do for you?");
        line();
    }

    /**
     * Displays a goodbye message to the user.
     */
    public void showBye() {
        line();
        System.out.println(" Bye. Hope to see you again soon!");
        line();
    }

    /**
     * Displays an error message to the user.
     *
     * @param message The error message to be displayed.
     */
    public void showError(String message) {
        line();
        System.out.println(" [Error] " + message);
        line();
    }

    /**
     * Displays a message to the user indicating that a task has been added to the list.
     *
     * @param t The task that has been added.
     * @param count The total number of tasks in the list after the addition
     */
    public void showAdded(Task t, int count) {
        line();
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + t);
        System.out.println(" Now you have " + count + " tasks in the list.");
        line();
    }

    /**
     * Displays the list of tasks to the user.
     *
     * @param tasks The list of tasks to be displayed.
     */
    public void showList(TaskList tasks) {
        line();
        System.out.println(" Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(" " + (i + 1) + ". " + tasks.get(i));
        }
        line();
    }

    /**
     * Displays a message to the user indicating that a task has been marked as done.
     *
     * @param t The task that has been marked as done.
     */
    public void showMarked(Task t) {
        line();
        System.out.println(" Nice! I've marked this task as done:");
        System.out.println("   " + t);
        line();
    }

    /**
     * Displays a message to the user indicating that a task has been marked as not done.
     *
     * @param t The task that has been marked as
     */
    public void showUnmarked(Task t) {
        line();
        System.out.println(" OK, I've marked this task as not done yet:");
        System.out.println("   " + t);
        line();
    }

    /**
     * Displays a message to the user indicating that a task has been deleted.
     *
     * @param removed The task that has been deleted.
     * @param remaining The number of tasks remaining in the list.
     */
    public void showDeleted(Task removed, int remaining) {
        line();
        System.out.println(" Noted. I've removed this task:");
        System.out.println("   " + removed);
        System.out.println(" Now you have " + remaining + " tasks in the list.");
        line();
    }

    /**
     * Reads a command from the user input.
     *
     * @return The command entered by the user, trimmed of leading and trailing whitespace.
     */
    public String readCommand() {
        return sc.nextLine().trim();
    }

    /**
     * Closes the scanner used for reading user input.
     */
    public void close() {
        sc.close();
    }

    /**
     * Displays a list of tasks that match a search query.
     *
     * @param matches A list of tasks that match the search query.
     */
    public void showMatches(List<Task> matches) {
        line();
        System.out.println(" Here are the matching tasks in your list:");
        for (int i = 0; i < matches.size(); i++) {
            System.out.println(" " + (i + 1) + ". " + matches.get(i));
        }
        line();
    }

    /**
     * Prints a horizontal line to separate different sections of output.
     */
    private static void line() {
        System.out.println("____________________________________________________________");
    }
}
