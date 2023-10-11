package ui;

import java.io.IOException;

import org.testfx.framework.junit5.ApplicationTest;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class GamePageTest extends ApplicationTest {

    /**
     * Controller for GamePage.
     */
    private GamePageController controller;
    /**
     * Root node of GamePage.
     */
    private Parent root;

    /**
     * Start application.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("App.fxml"));
        root = fxmlLoader.load();
        controller = fxmlLoader.getController();
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * get the RootNode.
     *
     * @return Parent node
     */
    public Parent getRootNode() {
        return root;
    }

    /**
     * Get current input in textfield.
     *
     * @return String in input field
     */
    private String getInput() {
        return ((TextField) root.lookup("playerInputField")).getText();
    }

    /**
     * Write a str in input field
     *
     * @param str - The string to write
     */
    private void writeInput(final String str) {
        ((TextField) root.lookup("playerInputField")).setText(str);
    }

}
