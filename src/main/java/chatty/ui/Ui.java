package chatty.ui;

import java.util.List;
import java.util.Scanner;

import chatty.task.Task;
import chatty.task.TaskList;

public class Ui {
    private final Scanner sc;

    public Ui() {
        this.sc = new Scanner(System.in);
    }

    public void showWelcome() {
        line();
        System.out.println(" Hello! I'm ChattyBot");
        System.out.println(" What can I do for you?");
        line();
    }

    public void showBye() {
        line();
        System.out.println(" Bye. Hope to see you again soon!");
        line();
    }

    public void showError(String message) {
        line();
        System.out.println(" [Error] " + message);
        line();
    }

    public void showAdded(Task t, int count) {
        line();
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + t);
        System.out.println(" Now you have " + count + " tasks in the list.");
        line();
    }

    public void showList(TaskList tasks) {
        line();
        System.out.println(" Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(" " + (i + 1) + ". " + tasks.get(i));
        }
        line();
    }

    public void showMarked(Task t) {
        line();
        System.out.println(" Nice! I've marked this task as done:");
        System.out.println("   " + t);
        line();
    }

    public void showUnmarked(Task t) {
        line();
        System.out.println(" OK, I've marked this task as not done yet:");
        System.out.println("   " + t);
        line();
    }

    public void showDeleted(Task removed, int remaining) {
        line();
        System.out.println(" Noted. I've removed this task:");
        System.out.println("   " + removed);
        System.out.println(" Now you have " + remaining + " tasks in the list.");
        line();
    }

    public String readCommand() {
        return sc.nextLine().trim();
    }

    public void close() {
        sc.close();
    }

    public void showMatches(List<Task> matches) {
        line();
        System.out.println(" Here are the matching tasks in your list:");
        for (int i = 0; i < matches.size(); i++) {
            System.out.println(" " + (i + 1) + ". " + matches.get(i));
        }
        line();
    }

    private static void line() {
        System.out.println("____________________________________________________________");
    }
}