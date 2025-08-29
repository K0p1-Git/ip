package chatty.task;

/**
 * Represents a todo task. A Todo object contains a description. The Todo class extends the Task class.
 */
public class Todo extends Task {
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String toDataString() {
        return "T" + super.toDataString();
    }
}
