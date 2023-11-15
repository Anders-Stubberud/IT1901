package ui;

// import java.io.IOException;

// import org.junit.Assert;
// import org.junit.BeforeClass;
import org.testfx.framework.junit5.ApplicationTest;
// import javafx.scene.control.Label;
// import javafx.fxml.FXMLLoader;
// import javafx.scene.Parent;
// import javafx.scene.Scene;
// import javafx.scene.input.MouseButton;
// import javafx.stage.Stage;

public class RegistrationControllerTest extends ApplicationTest {

  // /**
  // * The root of the application is used as reference to the DOM.
  // */
  // private Parent root;

  // /**
  // * The loader used to instantiate.
  // */
  // private FXMLLoader fxmlLoader;

  // /**
  // * Properties to get the gitlab CI pipeline to run headless for Integration
  // * tests.
  // */
  // @BeforeClass
  // public static void headless() {
  // System.setProperty("prism.verbose", "true");
  // System.setProperty("java.awt.headless", "true");
  // System.setProperty("testfx.robot", "glass");
  // System.setProperty("testfx.headless", "true");
  // System.setProperty("glass.platform", "Monocle");
  // System.setProperty("monocle.platform", "Headless");
  // System.setProperty("prism.order", "sw");
  // System.setProperty("prism.text", "t2k");
  // System.setProperty("testfx.setup.timeout", "2500");
  // }

  // /**
  // * Instantiates the stage.
  // */
  // @Override
  // public void start(final Stage stage) throws IOException {
  // fxmlLoader = new
  // FXMLLoader(this.getClass().getResource("Registration.fxml"));
  // root = fxmlLoader.load();
  // stage.setScene(new Scene(root));
  // stage.show();
  // }

  // /**
  // * Tests that a user can not be created if the username is already in use.
  // */
  // @Test
  // public void testUsernameTaken() {
  // clickOn("#newUsername", MouseButton.PRIMARY);
  // write("occupiedUser");
  // clickOn("#newPassword", MouseButton.PRIMARY);
  // write("occupied");
  // clickOn("#signUp", MouseButton.PRIMARY);
  // int opacity = (int) ((Label) root.lookup("#usernameTaken")).getOpacity();
  // Assert.assertEquals(
  // "The opacity of the 'occupied username' label should be 1",
  // 1, opacity);
  // }

  // /**
  // * Tests that a user with valid username can be created.
  // */
  // @Test
  // public void testCreateNewUser() {
  // String username = "availableUser";
  // Assert.assertFalse(UserIO.getAllUsernames().contains(username));
  // clickOn("#newUsername", MouseButton.PRIMARY);
  // write(username);
  // clickOn("#newPassword", MouseButton.PRIMARY);
  // write("password123");
  // clickOn("#signUp", MouseButton.PRIMARY);
  // Assert.assertTrue(UserIO.getAllUsernames().contains(username));
  // File file = new File(UserIO.getPath() + "/" + username);
  // UserIO.deleteUser(file);
  // }

}
