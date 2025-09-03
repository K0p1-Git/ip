package chatty.ui;

import java.util.List;
import java.util.Scanner;

import chatty.task.Task;
import chatty.task.TaskList;

/**
 * Handles user interaction formatting and (optionally) reading console input.
 * <p>
 * This Ui does <b>not</b> print directly; instead, it returns formatted strings so the
 * caller (CLI or GUI) can decide how to display them. For CLI usage, {@link #readCommand()}
 * reads from {@code System.in} and callers can {@code System.out.println(...)} the returned
 * strings.
 * </p>
 */
public class Ui {
    private final Scanner sc;

    /**
     * Constructs a new Ui that reads commands from standard input (CLI mode).
     */
    public Ui() {
        this.sc = new Scanner(System.in);
    }

    /**
     * Returns a formatted welcome message.
     *
     * @return the welcome message, boxed with divider lines
     */
    public String showWelcome() {
        return " Hello! I'm ChattyBot\n" + " What can I do for you?";
    }

    /**
     * Returns a formatted goodbye message.
     *
     * @return the goodbye message, boxed with divider lines
     */
    public String showBye() {
        return " Bye. Hope to see you again soon!";
    }

    /**
     * Returns a formatted error message.
     *
     * @param message the error details to include
     * @return the error message, boxed with divider lines
     */
    public String showError(String message) {
        return " [Error] " + message;
    }

    /**
     * Returns a formatted message indicating a task has been added.
     *
     * @param t     the task that was added
     * @param count the total number of tasks after the addition
     * @return the add-task message, boxed with divider lines
     */
    public String showAdded(Task t, int count) {
        return " Got it. I've added this task:\n" + "   " + t + "\n"
                + " Now you have " + count + " tasks in the list.";
    }

    /**
     * Returns a formatted list of tasks.
     *
     * @param tasks the tasks to display
     * @return the list message, boxed with divider lines
     */
    public String showList(TaskList tasks) {
        StringBuilder sb = new StringBuilder();
        sb.append(" Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(" ").append(i + 1).append(". ").append(tasks.get(i)).append("\n");
        }
        return trimEnd(sb);
    }

    /**
     * Returns a formatted message indicating a task has been marked as done.
     *
     * @param t the task that was marked done
     * @return the mark message, boxed with divider lines
     */
    public String showMarked(Task t) {
        return " Nice! I've marked this task as done:\n" + "   " + t;
    }

    /**
     * Returns a formatted message indicating a task has been marked as not done.
     *
     * @param t the task that was unmarked
     * @return the unmark message, boxed with divider lines
     */
    public String showUnmarked(Task t) {
        return " OK, I've marked this task as not done yet:\n" + "   " + t;
    }

    /**
     * Returns a formatted message indicating a task has been deleted.
     *
     * @param removed   the deleted task
     * @param remaining the number of remaining tasks
     * @return the delete message, boxed with divider lines
     */
    public String showDeleted(Task removed, int remaining) {
        return " Noted. I've removed this task:\n" + "   "
                + removed + "\n" + " Now you have " + remaining + " tasks in the list.";
    }

    /**
     * Returns a formatted list of tasks that match a query.
     *
     * @param matches the matching tasks
     * @return the matches message, boxed with divider lines
     */
    public String showMatches(List<Task> matches) {
        StringBuilder sb = new StringBuilder();
        sb.append(" Here are the matching tasks in your list:\n");
        if (matches.isEmpty()) {
            sb.append(" (none)\n");
        } else {
            for (int i = 0; i < matches.size(); i++) {
                sb.append(" ").append(i + 1).append(". ").append(matches.get(i)).append("\n");
            }
        }
        return trimEnd(sb);
    }

    /**
     * Reads a command from standard input (CLI).
     *
     * @return the trimmed command entered by the user
     */
    public String readCommand() {
        return sc.nextLine().trim();
    }

    /**
     * Closes the underlying scanner (CLI).
     */
    public void close() {
        sc.close();
    }

    /* ----------------- formatting helpers ----------------- */

    /**
     * Returns a divider line.
     *
     * @return the divider line
     */
    private static String line() {
        return "____________________________________________________________";
    }

    /**
     * Boxes the given body with divider lines.
     *
     * @param body the body to box
     * @return the boxed body
     */
    public String box(String body) {
        return line() + "\n" + body + "\n" + line();
    }

    /**
     * Trims the end of the given string.
     *
     * @param cs the string to trim
     * @return the trimmed string
     */
    private static String trimEnd(CharSequence cs) {
        int end = cs.length();
        while (end > 0 && Character.isWhitespace(cs.charAt(end - 1))) {
            end--;
        }
        return cs.subSequence(0, end).toString();
    }
}
