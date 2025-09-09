package chatty.app;

/** Engine for ChattyBot. */
public class ChattyEngine {
    private final ChattyCore core;

    /** Constructor for ChattyEngine. */
    public ChattyEngine() {
        this.core = new ChattyCore();
    }

    /** Handles user input and returns a reply. */
    public String handleInput(String input) {
        return core.process(input);
    }

    /** Returns the greeting message. */
    public String getGreeting() {
        return core.greeting();
    }
}
