package core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import types.User;

public class GameTest {

    /**
     * Object of game.
     */
    private Game game;

    /**
     * User in game.
     */
    private User testUser;
    /**
     * Digit for number of tests.
     */
    private static final int NUMBER_OF_TESTS = 10;
    /**
     * Wordlist used for testing.
     */
    private List<String> singleTestList = Arrays.asList("Test");
    /**
     * Wordlist used for testing with mulitple strings.
     */
    private List<String> multipleTestList = Arrays.asList("Test1", "Test2", "Test3");

    /**
     * Sets up two instances of the Category class to be used in the tests.
     */
    @BeforeEach
    public void setUp() {
        testUser = new User("Test", "user");
        testUser.addCustomCategories("Custom", Arrays.asList("1", "3", "4"));
        game = new Game(testUser.getUsername());
    }

    /**
     * Test constructors.
     */
    @Test
    @DisplayName("Test constructor")
    public void constructorTest() {
        Game testGame = new Game(testUser.getUsername());
        assertNotNull(new Game(""), "Game should not be null");
        assertNotNull(testGame, "Game should not be null");
    }

    /**
     * Check that quering and setting wordlist is correct.
     */
    @Test
    @DisplayName("Check correct get/set of wordlist")
    public void testWordList() {
        String fruitWord = "Apple";
        assertNull(game.getWordList());
        game.setWordList(singleTestList);
        assertEquals(game.getWordList(), singleTestList);
        game.setCategory("fruits");
        assertTrue(game.getWordList().contains(fruitWord.toUpperCase()),
                "The word " + fruitWord + " should be in wordlist. Your list:" + game.getWordList());
        assertFalse(game.getWordList().contains(singleTestList.get(0)),
                " The wordlist should not contain the word " + singleTestList.get(0) + " anymore");
    }

    /**
     * Checks that a randomly generated substring from a randomly.
     * pulled word is indeed recognized as a valid substring.
     */
    @Test
    @DisplayName("Check valid substring of word")
    public void testGetRandomSubstring() {
        game.setWordList(singleTestList);
        for (int i = 0; i < NUMBER_OF_TESTS; i++) {
            assertTrue("Test".contains(game.getSubstring()));
        }
    }

    // /**
    // * Test checking valid words.
    // */
    // @Test
    // @DisplayName("Check that guesses are valid")
    // public void testCheckValidWord() {
    // game.setWordList(multipleTestList);
    // assertTrue(game.checkValidWord("s", "Test"));
    // assertTrue(game.checkValidWord("es", "Test"));
    // assertTrue(game.checkValidWord("st2", "Test2"));
    // assertFalse(game.checkValidWord("Tes", "Test4"));
    // assertFalse(game.checkValidWord("2", "Test"));
    // assertFalse(game.checkValidWord("es", "Test4"));
    // }

    // @Test
    // @DisplayName("Check setting of highscore")
    // public void testHighscore() {
    // assertEquals(0, game.getPlayerHighscore(), "Highscore should be 0 on start");
    // game.savePlayerHighscore(100);
    // assertEquals(100, game.getPlayerHighscore(),
    // "Highscore should be 100, but was " + game.getPlayerHighscore());
    // }

}
