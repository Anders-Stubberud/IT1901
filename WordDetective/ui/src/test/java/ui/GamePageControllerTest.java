package ui;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;

import core.GameLogic;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
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
   * Controller for GamePage.
   */
  private GamePageController controller;
  /**
   * Gamelogic object.
   *
   */
  private GameLogic gameMock;

  /**
   * List for testing.
   */
  private List<String> testList = new ArrayList<>(Arrays.asList("TESTWORD"));

  /**
   * get the RootNode.
   *
   * @return Parent node
   */
  public Parent getRootNode() {
    return root;
  }

  /**
   * Setup before each test. Uses mockups for future testing.
   */
  @BeforeEach
  public void setUp() {
    gameMock = new GameLogic("guest");
    gameMock.setCategory("us states");
    gameMock.setWordList(testList);
    closeHowToPlay();
  }

  /**
   * Closes the HowToPlay popup window.
   */
  public void closeHowToPlay() {
    clickOn(LabeledMatchers.hasText("Close"));
  }

  /**
   * Get current input in textfield.
   *
   * @return String in input field
   */
  private String getInput() {
    return ((TextField) root.lookup("#playerInputField")).getText();

  }

  /**
   * Clean inputfield.
   */
  private void cleanInput() {
    ((TextField) getRootNode().lookup("#playerInputField")).setText("");
  }

  /**
   * Start application.
   */
  @Override
  public void start(final Stage stage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("GamePage.fxml"));
    root = fxmlLoader.load();
    controller = fxmlLoader.getController();
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
   * Check if guessed words is correct or incorrect.
   *
   * @param word          - The word to check
   * @param wordIsCorrect - If the word is meant to be correct
   */
  private void isCorrect(final String word, final boolean wordIsCorrect) {
    if (wordIsCorrect) {
      assertTrue(testList.contains(word));
    } else {
      assertFalse(testList.contains(word));
    }

  }

  /**
   * Test writing a word input.
   *
   * @param word      - The word to write
   * @param isCorrect - Is word correct
   */
  @ParameterizedTest
  @MethodSource
  public void testWriteWords(final String word, final boolean isCorrect) {
    write(word);
    isCorrect(getInput(), isCorrect);
    cleanInput();
  }

  /**
   * Stram of arguments for writing words.
   *
   * @return - Stream of Arguments
   */
  private static Stream<Arguments> testWriteWords() {
    return Stream.of(
        Arguments.of("TESTWORD", true),
        Arguments.of("TESTWORD", true),
        Arguments.of("WRONGWORD", false));
  }

}
