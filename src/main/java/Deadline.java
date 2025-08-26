public class Deadline extends Task {
    protected final String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }

    @Override
    public String toDataString() {
        return "D" + super.toDataString() + "/-/" + by;
    }
}