package ui;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;

import core.GameLogic;
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
     * Gamelogic object.
     */
    private GameLogic gameMock;
    /**
     * Root node of GamePage.
     */
    private Parent root;
    /**
     * List for testing.
     */
    private List<String> testList = new ArrayList<>(Arrays.asList("TESTWORD"));

    /**
     * Start application.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("GamePage.fxml"));
        root = fxmlLoader.load();
        System.out.println(root);
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
     * Write a str in input field.
     *
     * @param str - The string to write
     */
    private void writeInput(final String str) {
        ((TextField) getRootNode().lookup("#playerInputField")).setText(str);
    }

    /**
     * Clean inputfield.
     */
    private void cleanInput() {
        ((TextField) getRootNode().lookup("#playerInputField")).setText("");
    }

    /**
     * Setup before each test. Uses mockups for future testing.
     */
    @BeforeEach
    public void setUp() {
        gameMock = mock(GameLogic.class);
        controller.setGame(gameMock);
        gameMock.setCategory("Testing");
        gameMock.setWordList(testList);
        closeHowToPlay();
    }

    /**
     * Check if guessed words is correct or incorrect.
     *
     * @param word          - The word to check
     * @param wordIsCorrect - If the word is meant to be correct
     */
    private void isCorrect(String word, boolean wordIsCorrect) {
        if (wordIsCorrect) {
            assertTrue(testList.contains(word));
        } else {
            assertFalse(testList.contains(word));
        }

    }

    /**
     * Test writing a word input
     *
     * @param word      - The word to write
     * @param isCorrect - Is word correct
     */
    @ParameterizedTest
    @MethodSource
    public void testWriteWords(String word, boolean isCorrect) {
        writeInput(word);
        isCorrect(getInput(), isCorrect);
        cleanInput();
    }

    /**
     * Stram of arguments for writing words
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
