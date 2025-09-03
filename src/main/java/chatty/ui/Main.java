package chatty.ui;

import chatty.app.ChattyEngine;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/** A GUI for ChattyBot using FXML. */
public class Main extends Application {

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxml = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane root = fxml.load();

            // Inject engine into controller
            MainWindow controller = fxml.getController();
            controller.setEngine(new ChattyEngine());

            Scene scene = new Scene(root);
            stage.setTitle("ChattyBot");
            stage.getIcons().add(new Image(this.getClass().getResourceAsStream("/images/icon.png")));
            stage.setResizable(false);
            stage.setScene(scene);

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Launches the JavaFX application.
     *
     * @param args Command line arguments (unused).
     */
    public static void main(String[] args) {
        launch(args);
    }
}
