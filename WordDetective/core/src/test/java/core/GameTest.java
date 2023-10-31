package core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import types.User;

// import static org.junit.jupiter.api.Assertions.assertFalse;
// import static org.junit.jupiter.api.Assertions.assertTrue;
// import java.util.Collection;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;

// import persistence.JsonIO;
// import types.User;

public class GameTest {

    /**
     * Object of game.
     */
    private Game game;

    /**
     * Mock of the user in game.
     */
    @Mock
    private User testUser;
    /**
     * Digit for number of tests.
     */
    private static final int NUMBER_OF_TESTS = 10;
    /**
     * Wordlist used for testing
     */
    private List<String> testWordList = Arrays.asList("Test", "Test2", "Test3");

    /**
     * Sets up two instances of the Category class to be used in the tests.
     */
    @BeforeEach
    public void setUp() {
        testUser = mock(User.class);
        testUser.addCustomCategories("Custom", Arrays.asList("1", "3", "4"));
        game = new Game(testUser);
    }

    @Test
    @DisplayName("Test constructor")
    public void constructorTest() {
        Game testGame = new Game(testUser);
        assertNotNull(new Game(), "Game should not be null");
        assertNotNull(testGame, "Game should not be null");
        assertEquals(testUser.getUsername(), testGame.getPlayer().getUsername(), "Player's username should be "
                + testUser.getUsername() + ", not: " + testGame.getPlayer().getUsername());
    }

    /**
     * Checks that getting/setting of category is correct.
     */
    @Test
    @DisplayName("Check correct get/set of category")
    public void testCategory() {
        assertNull(game.getChosenCategory());
        game.setCategory("fruits");
        assertEquals("fruits", game.getChosenCategory(),
                "The category should now be fruits, not:" + game.getChosenCategory());
        assertThrows(IllegalArgumentException.class, () -> game.setCategory("NonExisitingCategory"),
                "Should not be able to set category to a category that does not exist");
        assertEquals("fruits", game.getChosenCategory());

    }

    /**
     * Check that quering and setting wordlist is correct.
     */
    @Test
    @DisplayName("Check correct get/set of wordlist")
    public void tetsWordList() {
        String fruitWord = "Apple";
        assertTrue(game.getWordList().isEmpty());
        game.setWordList(testWordList);
        assertEquals(game.getWordList(), testWordList);
        game.setCategory("fruits");
        assertTrue(game.getWordList().contains(fruitWord.toUpperCase()),
                "The word " + fruitWord + " should be in wordlist. Your list:" + game.getWordList());
        assertFalse(game.getWordList().contains(testWordList.get(0)),
                " The wordlist should not contain the word " + testWordList.get(0) + " anymore");
    }

    /**
     * Checks that a randomly pulled word from the selection list is contained in
     * the search list.
     */
    @Test
    @DisplayName("Check valid pull of random word")
    public void testGetRandomWord() {
        game.setWordList(testWordList);
        for (int i = 0; i < NUMBER_OF_TESTS; i++) {
            assertTrue(testWordList.contains(game.getRandomWord()));
        }
    }

    /**
     * Checks that a randomly generated substring from a randomly
     * pulled word is indeed recognized as a valid substring.
     */
    @Test
    @DisplayName("Check valid substring of word")
    public void testGetRandomSubstring() {
        for (int i = 0; i < NUMBER_OF_TESTS; i++) {
            assertTrue("RandomSubstring".contains(game.getRandomSubstring("RandomSubstring")));
        }
    }

    /**
     * Check that highscore of user is set correctly.
     */
    @Test
    @DisplayName("Check setting of highscore")
    public void testHighscore() {
        assertEquals(0, game.getPlayer().getHighScore());
        game.savePlayerHighscore(NUMBER_OF_TESTS, false);
        assertEquals(NUMBER_OF_TESTS, game.getPlayer().getHighScore());
    }

}
