package ui;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

public class RegistrationControllerTest extends ApplicationTest {

  /**
   * The root of the application is used as reference to the DOM.
   */
  private Parent root;
  /**
   * The label used to display error.
   */
  private Label errorDisplay;

  /**
   * The loader used for instantiation.
   */
  private FXMLLoader fxmlLoader;
  /**
   * The textfield used to provide username or password.
   */
  private TextField usernameField, passwordField;

  /**
   * The controller used for testing.
   */
  @Mock
  private RegistrationController controller;

  /**
   * Instantiates the stage.
   */
  @Override
  public void start(final Stage stage) throws IOException {
    fxmlLoader = new FXMLLoader(this.getClass().getResource("Registration.fxml"));
    controller = mock(RegistrationController.class);
    root = fxmlLoader.load();
    stage.setScene(new Scene(root));
    stage.show();
    errorDisplay = (Label) root.lookup("#errorDisplay");
    usernameField = (TextField) root.lookup("#newUsername");
    passwordField = (TextField) root.lookup("#newPassword");
  }

  /**
   * Tests that a user can not be created if the fields are blank.
   */
  @Test
  public void testBlankFields() {
    clickOn("#signUp", MouseButton.PRIMARY);
    assertTrue(errorDisplay.getText().contains("blank"),
        "The error display should say something about blank fields, but was:" + errorDisplay.getText());
  }

  /**
   * write a string to a textfield instant.
   *
   * @param string    - the string to write
   * @param textfield - the textfield to write to
   *
   */
  private void write(final String string, final TextField textfield) {
    textfield.setText(string);
  }

}
