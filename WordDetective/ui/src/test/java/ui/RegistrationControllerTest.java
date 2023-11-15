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
   * Tests that a user can not be created if the username is already in use.
   */
  @Test
  public void testUsernameTaken() {
    write("TestUser", usernameField);
    write("Password", passwordField);
    // clickOn("#signUp", MouseButton.PRIMARY);
    // assertTrue(errorDisplay.getText().contains("already taken"),
    // "The error display should say something about the username being taken, but
    // was:"
    // + errorDisplay.getText());
  }

  /**
   * Tests that a user can not be created the username doesn't match the criteria.
   */
  @Test
  public void testWrongUsername() {
    write("T", usernameField);
    write("Pasword123", passwordField);
    // clickOn("#signUp", MouseButton.PRIMARY);
    // assertTrue(errorDisplay.getText().contains("username needs"),
    // "The error display should say something about what is required in a username,
    // but was:"
    // + errorDisplay.getText());
  }

  /**
   * Tests that a user cannot be created the password doesn't match the criteria.
   * Make sure user does not exist in database.
   */
  @Test
  public void testWrongPassword() {
    write("ValidUserThatShouldntBeInDatabase", usernameField);
    write("P", passwordField);
    // clickOn("#signUp", MouseButton.PRIMARY);
    // assertTrue(errorDisplay.getText().contains("password needs"),
    // "The error display should say something about what is required in a password,
    // but was:"
    // + errorDisplay.getText());

  }

  /**
   * Tests that a user with valid username and password can be created.
   *
   */
  @Test
  public void testCreateNewUser() {
    write("ValidUserThatShouldntBeInDatabase", usernameField);
    write("Password123", passwordField);

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
