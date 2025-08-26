public class Task {
    protected final String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public void mark() {
        this.isDone = true;
    }

    public void unmark() {
        this.isDone = false;
    }

    public String getStatus() {
        return isDone ? "X" : " ";
    }

    /** Serialize common portion: done flag + description (subclasses prepend their type). */
    public String toDataString() {
        return "/-/" + (isDone ? "1" : "0") + "/-/" + description;
    }

    @Override
    public String toString() {
        return "[" + getStatus() + "] " + description;
    }
}
