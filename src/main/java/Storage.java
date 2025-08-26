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

public class Storage {
    private final File file;

    /** Saves to ./data/chatty.txt (relative, OS-independent). */
    public Storage() {
        this.file = new File("data" + File.separator + "chatty.txt");
    }

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

    /** Load tasks from disk; skip malformed lines (basic corruption tolerance). */
    public ArrayList<Task> load() {
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            ensureFile();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null) {
                    Task t = parseLine(line);
                    if (t != null) tasks.add(t);
                }
            }
        } catch (ChattyFileException | IOException ignored) {
            // If file missing/unreadable: start with empty list quietly.
            tasks.clear();
        }
        return tasks;
    }

    /** Save all tasks (overwrite). Wrap I/O issues in ChattyFileException. */
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

    // Expected formats:
    // T/-/0/-/desc
    // D/-/1/-/desc/-/by
    // E/-/0/-/desc/-/from/-/to
    private Task parseLine(String line) {
        try {
            String[] p = line.split("/-/");
            if (p.length < 3) {
                return null;
            }
            String kind = p[0].trim();
            boolean done = "1".equals(p[1].trim());
            String desc = p[2].trim();

            Task t;
            switch (kind) {
            case "T":
                t = new Todo(desc);
                break;
            case "D":
                if (p.length < 4) return null;
                t = new Deadline(desc, p[3].trim());
                break;
            case "E":
                if (p.length < 5) return null;
                t = new Event(desc, p[3].trim(), p[4].trim());
                break;
            default:
                return null;
            }
            if (done) t.mark();
            return t;
        } catch (Exception ex) {
            return null; // skip corrupted line
        }
    }
}
