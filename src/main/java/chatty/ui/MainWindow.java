package chatty.ui;

import java.util.Objects;

import chatty.app.ChattyEngine;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

/** Controller for the main GUI window. */
public class MainWindow {

    private static final String BOT_PHOTO = "/images/DaDuke.png";
    private static final String USER_PHOTO = "/images/DaUser.png";

    @FXML private ScrollPane scrollPane;
    @FXML private VBox dialogContainer;
    @FXML private TextField userInput;
    @FXML private Button sendButton;

    private ChattyEngine engine;

    private final Image userImage =
            new Image(Objects.requireNonNull(this.getClass().getResourceAsStream(USER_PHOTO)));
    private final Image botImage =
            new Image(Objects.requireNonNull(this.getClass().getResourceAsStream(BOT_PHOTO)));

    /** Called by JavaFX after @FXML members are injected. */
    @FXML
    public void initialize() {
        // Auto-scroll to bottom when new nodes are added
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Dependency injection from Main. */
    public void setEngine(ChattyEngine engine) {
        this.engine = engine;
        // initial greeting
        dialogContainer.getChildren().add(
                DialogBox.getBotDialog(engine.getGreeting(), botImage)
        );
        userInput.requestFocus();
    }

    /** Handles send-button click or Enter key. */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText().trim();
        if (input.isEmpty()) {
            return;
        }

        dialogContainer.getChildren().add(DialogBox.getUserDialog(input, userImage));

        String response = engine.handleInput(input);
        dialogContainer.getChildren().add(DialogBox.getBotDialog(response, botImage));

        userInput.clear();
    }
}
