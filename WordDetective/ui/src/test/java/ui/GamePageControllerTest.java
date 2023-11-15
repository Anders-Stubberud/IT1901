package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;

import core.Game;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class GamePageControllerTest extends ApplicationTest {

  /**
   * The root of the application is used as reference to the DOM.
   */
  private Parent root;

  /*
   * FXML components used for testing.
   */
  @FXML
  private Label score, highScore;

  /**
   * Mock of the api.
   */
  @Mock
  private ApiConfig apiMock = mock(ApiConfig.class);

  /**
   * Controller that injects the mocks.
   */
  @InjectMocks
  private GamePageController controller;

  /**
   * Textfield where the user writes.
   */
  private TextField playerInputField;

  /**
   * get the RootNode.
   *
   * @return Parent node
   */
  public Parent getRootNode() {
    return root;
  }

  /**
   * Start application.
   */
  @Override
  public void start(final Stage stage) throws IOException {
    try {
      doNothing().when(apiMock).newGame("guest", null);
      doNothing().when(apiMock).savePlayerHighscore(Mockito.anyString());
      when(apiMock.getWord()).thenReturn("TESTWORD");
      when(apiMock.getHighScore()).thenReturn(0);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("GamePage.fxml"));
    root = fxmlLoader.load();
    controller = fxmlLoader.getController();
    controller.setApi(apiMock);
    playerInputField = (TextField) root.lookup("#playerInputField");
    score = (Label) root.lookup("#points");
    highScore = (Label) root.lookup("#highScore");
    stage.setScene(new Scene(root));
    stage.show();
  }

  @BeforeEach
  public void setUp() {
    closeHowToPlay();
  }

  /**
   * Tests if closing and opening howToPlay works.
   */
  @Test
  public void testHowToPlay() {
    Pane htp = (Pane) root.lookup("#howToPlay");
    assertFalse(htp.isVisible());
    clickOn("#openHTPBtn");
    assertTrue(htp.isVisible());
  }

  @Test
  public void restartGame() {
    Pane gameOverPane = (Pane) root.lookup("#gameOverPage");
    gameOverPane.setVisible(true);
    try {
      when(apiMock.getHighScore()).thenReturn(10);
      clickOn("#restartBtn");
      assertEquals(10, highScore.getText());
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
  }

  // @Test
  // public void testMoveLettersTo() {
  // Double layoutX, layoutY = 0.0;
  // Pane letters = (Pane) root.lookup("#lettersCircle");
  // System.out.println("LEtter Circel: " + letters.getLayoutX() + " Y: " +
  // letters.getLayoutY());
  // layoutX = letters.getLayoutX();
  // layoutY = letters.getLayoutY();
  // controller.moveLettersTo(100, 100, 0.1);
  // try {
  // Thread.sleep(1000);
  // letters = (Pane) root.lookup("#lettersCircle");
  // assertNotEquals(layoutX, letters.getLayoutX(), "hadX: " + layoutX + "
  // currentX: " + letters.getLayoutX());
  // assertNotEquals(layoutY, letters.getLayoutY(), "hadY: " + layoutY + "
  // currentY: " + letters.getLayoutY());
  // } catch (InterruptedException e) {
  // e.printStackTrace();
  // }
  // }

  /**
   * Tests if the input from the user is displayed correctly.
   */
  @Test
  public void testWriteInput() {
    assertTrue("TESTWORD".contains(controller.getSubstring()));
    write("TESTWORD", playerInputField);
    assertTrue(getInput().equals("TESTWORD"));
  }

  @ParameterizedTest
  @MethodSource("testWriteWords")
  public void testGuess(final String guess, final boolean isCorrect) {
    try {
      write(guess, playerInputField);
      when(apiMock.getWord()).thenReturn("TESTWORD");
      when(apiMock.checkValidWord(Mockito.anyString(), Mockito.anyString())).thenReturn(isCorrect);
      press(KeyCode.ENTER);
      if (isCorrect) {
        assertTrue(playerInputField.getText().isBlank());
      } else {
        assertTrue(playerInputField.getText().equals(guess));
      }
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
  }

  /**
   * Stream of arguments for testing used in {@code testGuess}.
   *
   * @return - Stream of Arguments
   */
  private static Stream<Arguments> testWriteWords() {
    return Stream.of(
        Arguments.of("TESTWORD", true),
        Arguments.of("TESTWORD", true),
        Arguments.of("WRONGWORD", false));
  }

  /**
   * Helper method to get current input in textfield.
   *
   * @return String in input field
   */
  private String getInput() {
    return ((TextField) root.lookup("#playerInputField")).getText();

  }

  /**
   * Helper method to clean inputfield.
   */
  private void cleanInput() {
    ((TextField) getRootNode().lookup("#playerInputField")).setText("");
  }

  /**
   * Helper method to close the HowToPlay popup window.
   */
  private void closeHowToPlay() {
    clickOn(LabeledMatchers.hasText("Close"));
  }

  /**
   * Helper method to write input instant.
   */
  private void write(final String string, final TextField textfield) {
    textfield.setText(string);
  }

}
