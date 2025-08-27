import java.util.ArrayList;

public class TaskList {
    private final ArrayList<Task> tasks = new ArrayList<>();

    public TaskList(ArrayList<Task> seed) {
        if (seed != null) {
            tasks.addAll(seed);
        }
    }

    public int size() {
        return tasks.size();
    }

    public Task get(int idx) {
        return tasks.get(idx);
    }

    public void add(Task t) {
        tasks.add(t);
    }

    public Task remove(int idx) {
        return tasks.remove(idx);
    }

    public ArrayList<Task> asList() {
        return tasks;
    }
}