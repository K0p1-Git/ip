package chatty.parser;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import chatty.exceptions.ChattyException;
import chatty.exceptions.MalformedArgumentsException;
import org.junit.jupiter.api.Test;

public class ParserTest {

    @Test
    void splitDeadlineArgs_valid_returnsDescAndBy() throws MalformedArgumentsException {
        String[] parts = Parser.splitDeadlineArgs("return book /by 12-08-2025 1800");
        assertEquals("return book", parts[0]);
        assertEquals("12-08-2025 1800", parts[1]);
    }

    @Test
    void splitDeadlineArgs_missingOrEmptyBy_throws() {
        assertThrows(MalformedArgumentsException.class,
                () -> Parser.splitDeadlineArgs("return book"));            // no /by
        assertThrows(MalformedArgumentsException.class,
                () -> Parser.splitDeadlineArgs("return book /by"));        // empty by
        assertThrows(MalformedArgumentsException.class,
                () -> Parser.splitDeadlineArgs("/by 12-08-2025 1800"));    // empty desc
    }

    @Test
    void splitEventArgs_valid_returnsDescFromTo() throws MalformedArgumentsException {
        String[] parts = Parser.splitEventArgs("proj meeting /from 10-09-2025 1000 /to 10-09-2025 1200");
        assertArrayEquals(new String[]{
                "proj meeting", "10-09-2025 1000", "10-09-2025 1200"
        }, parts);
    }

    @Test
    void splitEventArgs_missingPieces_throws() {
        assertThrows(MalformedArgumentsException.class,
                () -> Parser.splitEventArgs("proj meeting"));                 // no /from or /to
        assertThrows(MalformedArgumentsException.class,
                () -> Parser.splitEventArgs("proj /from 10-09-2025 1000"));  // missing /to
        assertThrows(MalformedArgumentsException.class,
                () -> Parser.splitEventArgs(" /from 10-09-2025 1000 /to 10-09-2025 1200")); // empty desc
        assertThrows(MalformedArgumentsException.class,
                () -> Parser.splitEventArgs("proj /from  /to 10-09-2025 1200"));           // empty from
        assertThrows(MalformedArgumentsException.class,
                () -> Parser.splitEventArgs("proj /from 10-09-2025 1000 /to  "));          // empty to
    }

    @Test
    void parseIndexOrThrow_valid_convertsOneBasedToZeroBased() throws ChattyException {
        assertEquals(0, Parser.parseIndexOrThrow("1", 3));
        assertEquals(2, Parser.parseIndexOrThrow("3", 3));
    }

    @Test
    void parseIndexOrThrow_invalidVariants_throwChattyException() {
        assertThrows(ChattyException.class, () -> Parser.parseIndexOrThrow("", 3));     // missing
        assertThrows(ChattyException.class, () -> Parser.parseIndexOrThrow("abc", 3));  // not integer
        assertThrows(ChattyException.class, () -> Parser.parseIndexOrThrow("0", 3));    // < 1
        assertThrows(ChattyException.class, () -> Parser.parseIndexOrThrow("4", 3));    // > size
    }
}