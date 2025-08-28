package chatty.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class TaskListTest {

    @Test
    void addGetRemove_maintainsSizeAndOrder() {
        TaskList list = new TaskList(new ArrayList<>());

        Todo t1 = new Todo("read book");
        Todo t2 = new Todo("buy milk");

        list.add(t1);
        list.add(t2);

        assertEquals(2, list.size());
        assertSame(t1, list.get(0));
        assertSame(t2, list.get(1));

        Task removed = list.remove(0);
        assertSame(t1, removed);
        assertEquals(1, list.size());
        assertSame(t2, list.get(0));
    }

    @Test
    void markUnmark_viaRetrievedTask_affectsToString() {
        TaskList list = new TaskList(new ArrayList<>());
        Todo t = new Todo("exercise");
        list.add(t);

        // initially not done
        assertTrue(list.get(0).toString().contains("[ ]"));

        // mark done
        list.get(0).mark();
        assertTrue(list.get(0).toString().contains("[X]"));

        // unmark back
        list.get(0).unmark();
        assertTrue(list.get(0).toString().contains("[ ]"));
    }
}
