package chatty.task;

import java.util.ArrayList;

public class TaskList {
    private final ArrayList<Task> tasks = new ArrayList<>();

    /**
     * Constructs a new TaskList object with the specified seed.
     * If the seed is null, the TaskList is initialized with an empty ArrayList.
     * Otherwise, the TaskList is initialized with the seed.
     *
     * @param seed the seed to initialize the TaskList with.
     * @see ArrayList
     * @see Task
     */
    public TaskList(ArrayList<Task> seed) {
        if (seed != null) {
            tasks.addAll(seed);
        }
    }

    /**
     * Returns the number of tasks in the TaskList.
     *
     * @return the number of tasks in the TaskList.
     * @see ArrayList#size()
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns the task at the specified index in the TaskList.
     *
     * @param idx the index of the task to return.
     * @return the task at the specified
     * @see ArrayList#get(int)
     */
    public Task get(int idx) {
        return tasks.get(idx);
    }

    /**
     * Adds the specified task to the TaskList.
     *
     * @param t the task to be added to the TaskList.
     * @see ArrayList#add(Object)
     * @see Task
     * @see TaskList#tasks
     */
    public void add(Task t) {
        tasks.add(t);
    }

    /**
     * Removes the task at the specified index from the TaskList.
     *
     * @param idx the index of the task to be removed.
     * @return the task that was removed from the TaskList.
     * @see ArrayList#remove(int)
     * @see Task
     * @see TaskList#tasks
     */
    public Task remove(int idx) {
        return tasks.remove(idx);
    }

    /**
     * Returns the TaskList as an ArrayList of Task objects.
     *
     * @return the TaskList as an ArrayList of Task objects.
     * @see ArrayList
     * @see Task
     * @see TaskList#tasks
     */
    public ArrayList<Task> asList() {
        return tasks;
    }
}