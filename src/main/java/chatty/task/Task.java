package chatty.task;

public class Task {
    protected final String description;
    protected boolean isDone;

    /**
     * Constructs a new Task object with the specified description.
     * The task is initially marked as not done.
     *
     * @param description
     * @see Task#description
     * @see Task#isDone
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Marks the task as done.
     *
     * @see Task#isDone
     * @see Boolean
     */
    public void mark() {
        this.isDone = true;
    }

    /**
     * Marks the task as not done.
     *
     * @see Task#isDone
     * @see Boolean
     */
    public void unmark() {
        this.isDone = false;
    }

    /**
     * Returns the status of the task as a string.
     * If the task is done, returns "X", otherwise returns " ".
     *
     * @return the status of the task as a string.
     * @see Task#isDone
     * @see String
     * @see Boolean
     */
    public String getStatus() {
        return isDone ? "X" : " ";
    }

    /**
     * Converts the task to a string representation that can be stored in a file.
     * The string is formatted as follows: "/-/{isDone}/-/{description}".
     * The isDone field is represented by a "1" if the task is done, and a "0" otherwise.
     * The description field is represented by the description of the task.
     *
     * @return a string representation of the task that can be stored in a file.
     * @see Task#isDone
     * @see Task#description
     * @see String#format(String, Object...)
     * @see String#valueOf(boolean)
     */
    public String toDataString() {
        return "/-/" + (isDone ? "1" : "0") + "/-/" + description;
    }

    @Override
    public String toString() {
        return "[" + getStatus() + "] " + description;
    }
}
