package chatty.app;

import chatty.ui.Ui;

/** Main class for the ChattyBot application. */
public class ChattyBot {
    private final ChattyCore core;

    /** Constructor for ChattyBot. */
    public ChattyBot() {
        this.core = new ChattyCore();
    }

    /** Runs the ChattyBot application. */
    public void run() {
        System.out.println(core.greeting());

        Ui ui = new Ui();
        while (true) {
            String input = ui.readCommand();
            String reply = core.process(input);
            System.out.println(reply);

            if ("bye".equalsIgnoreCase(input.trim())) {
                ui.close();
                return;
            }
        }
    }

    /** Main method to start the ChattyBot application. */
    public static void main(String[] args) {
        new ChattyBot().run();
    }
}
