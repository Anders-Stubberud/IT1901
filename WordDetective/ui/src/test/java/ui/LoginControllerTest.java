package ui;

import java.io.IOException;

// import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import javafx.fxml.FXMLLoader;
// import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
// import javafx.scene.input.MouseButton;
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
   * Properties to get the gitlab CI pipeline to run headless for Integration
   * tests.
   */
  @BeforeClass
  public static void headless() {
    System.setProperty("prism.verbose", "true");
    System.setProperty("java.awt.headless", "true");
    System.setProperty("testfx.robot", "glass");
    System.setProperty("testfx.headless", "true");
    System.setProperty("glass.platform", "Monocle");
    System.setProperty("monocle.platform", "Headless");
    System.setProperty("prism.order", "sw");
    System.setProperty("prism.text", "t2k");
    System.setProperty("testfx.setup.timeout", "2500");
  }

  /**
   * Instantiates the stage.
   */
  @Override
  public void start(final Stage stage) throws IOException {
    fxmlLoader = new FXMLLoader(this.getClass().getResource("LoginPage.fxml"));
    root = fxmlLoader.load();
    stage.setScene(new Scene(root));
    stage.show();
  }

  /**
   * Tests if the program notifies about incorrect login information.
   */
  // @Test
  // public void testIncorrectCombination() {
  // clickOn("#username", MouseButton.PRIMARY);
  // write("incorrectCombination");
  // clickOn("#password", MouseButton.PRIMARY);
  // write("incorrectPassword");
  // clickOn("#login");
  // // If opacity of 'incorrect username/password' label is 1, the login has not
  // // been performed.
  // int opacity = (int) ((Label) root.lookup("#incorrect")).getOpacity();
  // Assert.assertEquals(
  // "The opacity of the 'incorrect username/password' should be 1",
  // 1, opacity);
  // }

  /**
   * Tests if the correct category gets chosen once it is selected.
   */
  // @Test
  // public void testCorrectCombination() {
  // clickOn("#username", MouseButton.PRIMARY);
  // write("registeredUser");
  // clickOn("#password", MouseButton.PRIMARY);
  // write("password123");
  // clickOn("#login");
  // // The vBox element is part of the categories page, so if login is
  // // successful, it will be present.
  // Node elementPresentInCategory = lookup("#vBox").query();
  // Assert.assertNotNull(elementPresentInCategory);
  // }

  /**
   * Tests that a user with valid credentials can be instantiated.
   */
  // @Test
  // public void testRegisterNew() {
  // clickOn("#registerUser", MouseButton.PRIMARY);
  // Node elementPresentInRegistration = lookup("#newUsername").query();
  // Assert.assertNotNull(elementPresentInRegistration);
  // }

}
