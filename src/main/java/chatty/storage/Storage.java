package chatty.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import chatty.exceptions.ChattyFileException;
import chatty.task.Deadline;
import chatty.task.Event;
import chatty.task.Task;
import chatty.task.Todo;

/**
 * Handles the loading and saving of tasks to and from a file.
 * The file is located at "data/chatty.txt" by default.
 * If the file or directory does not exist, it will be created.
 * If the file is corrupted or cannot be read, an exception will be thrown.
 */
public class Storage {
    private final File file;

    /**
     * Constructs a new Storage object with the default file path.
     */
    public Storage() {
        this.file = new File("data" + File.separator + "chatty.txt");
    }

    /**
     * Ensures that the file exists and is accessible.
     *
     * @throws ChattyFileException if the file cannot be created or accessed.
     */
    private void ensureFile() throws ChattyFileException {
        try {
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) {
                if (!parent.mkdirs() && !parent.exists()) {
                    throw new IOException("Could not create data directory.");
                }
            }
            if (!file.exists()) {
                if (!file.createNewFile()) {
                    throw new IOException("Could not create data file.");
                }
            }
        } catch (IOException e) {
            throw new ChattyFileException("Could not prepare data file.");
        }
    }

    /**
     * Loads tasks from the file. If the file is missing or unreadable, returns an empty list.
     *
     * @return the list of tasks loaded from the file.
     * @see Task
     * @see ArrayList
     * @see File
     * @see BufferedReader
     * @see IOException
     * @see ChattyFileException
     */
    public ArrayList<Task> load() {
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            ensureFile();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null) {
                    Task t = parseLine(line);
                    if (t != null) {
                        tasks.add(t);
                    }
                }
            }
        } catch (ChattyFileException | IOException ignored) {
            tasks.clear(); // If file missing/unreadable: start with empty list quietly.
        }
        return tasks;
    }

    /**
     * Saves the list of tasks to the file. If the file is missing or unwritable, throws an exception.
     *
     * @param tasks the list of tasks to be saved.
     * @throws ChattyFileException if the file is missing or unwritable.
     * @see Task
     * @see ArrayList
     * @see File
     * @see BufferedWriter
     * @see IOException
     * @see ChattyFileException
     */
    public void save(ArrayList<Task> tasks) throws ChattyFileException {
        try {
            ensureFile();
            try (BufferedWriter bw = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(file, false), StandardCharsets.UTF_8))) {
                for (Task t : tasks) {
                    bw.write(t.toDataString());
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            throw new ChattyFileException("Failed to save tasks to disk.");
        }
    }



    /**
     * Parses a line from the file and returns a Task object.
     *
     * <p>Expected formats:</p>
     * <pre>
     * T / - / 0 / - / desc
     * D / - / 1 / - / desc / - / by
     * E / - / 0 / - / desc / - / from / - / to
     * </pre>
     *
     * @param line the line to be parsed.
     * @return a Task object.
     * @see Task
     * @see Todo
     * @see Deadline
     * @see Event
     */
    private Task parseLine(String line) {
        try {
            String[] p = line.split("/-/");
            if (p.length < 3) {
                return null;
            }
            String kind = p[0].trim();
            boolean isDone = "1".equals(p[1].trim());
            String desc = p[2].trim();

            Task t;
            switch (kind) {
            case "T":
                t = new Todo(desc);
                break;
            case "D":
                if (p.length < 4) {
                    return null;
                }
                t = new Deadline(desc, p[3].trim());
                break;
            case "E":
                if (p.length < 5) {
                    return null;
                }
                t = new Event(desc, p[3].trim(), p[4].trim());
                break;
            default:
                return null;
            }
            if (isDone) {
                t.mark();
            }
            return t;
        } catch (Exception ex) {
            return null; // skip corrupted line
        }
    }
}
