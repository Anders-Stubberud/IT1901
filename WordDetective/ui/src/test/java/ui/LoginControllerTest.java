package ui;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginControllerTest extends ApplicationTest {

  /**
   * The root of the application is used as reference to the DOM.
   */
  private Parent root;

  /**
   * The loader used for instantiation.
   */
  private FXMLLoader fxmlLoader;

  /**
   * The textfield used to provide username or password.
   */
  private TextField usernameField, passwordField;

  /**
   * The label used to display error.
   */
  private Label errorDisplay;

  /**
   * Instantiates the stage.
   */
  @Override
  public void start(final Stage stage) throws IOException {
    fxmlLoader = new FXMLLoader(this.getClass().getResource("LoginPage.fxml"));
    root = fxmlLoader.load();
    usernameField = (TextField) root.lookup("#usernameField");
    passwordField = (TextField) root.lookup("#passwordField");
    errorDisplay = (Label) root.lookup("#errorDisplay");
    stage.setScene(new Scene(root));
    stage.show();
  }

  /**
   * Tests if the program notifies about incorrect login information.
   */
  @Test
  public void testBlankFields() {
    clickOn("#login");
    assertTrue(errorDisplay.getText().contains("blank fields"),
        "The error display should say something about blank fields, but was:" + errorDisplay.getText());
  }

  /**
   * Helper method for writing text to a textfield instant.
   *
   * @param text  - The text to write.
   * @param field - The textfield to write to.
   */
  private void write(final String text, final TextField field) {
    field.setText(text);
  }

}
