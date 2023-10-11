package ui;

import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class GamePageControllerTest extends ApplicationTest {

  /**
   * The root of the application is used as reference to the DOM.
   */
  private Parent root;

  /**
   * Properties t0 get the gitlab CI pipeline to run headless for Integration
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
    FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("GamePage.fxml"));
    fxmlLoader.setControllerFactory(new GamePageFactory("guest", "countries"));
    root = fxmlLoader.load();
    stage.setScene(new Scene(root));
    stage.show();
  }

  /**
   * Extracts the players guess.
   *
   * @return The players guess.
   */
  private String extractGuess() {
    TextFlow extractedGuess = (TextFlow) root.lookup("#outputField");
    if (extractedGuess == null) {
      return null;
    }
    StringBuilder extractedText = new StringBuilder();
    for (Node node : extractedGuess.getChildren()) {
      if (node instanceof Text) {
        Text textNode = (Text) node;
        extractedText.append(textNode.getText());
      }
    }
    return extractedText.toString();
  }

  /**
   * Tests if the input from the user is displayed correctly.
   */
  @Test
  public void testCorrectExtractOfGuess() {
    String input = "This is for testing purposes";
    write(input);
    String extractedString = extractGuess();
    Assert.assertEquals(input.toUpperCase(), extractedString);
  }

  /**
   * Tests if the previous guess of a user gets wiped before a new substring is
   * served.
   */
  @Test
  public void testWipeOfGuess() {
    String input = "This should get wiped";
    write(input);
    type(KeyCode.ENTER);
    String wipedGuess = extractGuess();
    Assert.assertEquals("Text did not get wiped after guess", "", wipedGuess);
  }

}
