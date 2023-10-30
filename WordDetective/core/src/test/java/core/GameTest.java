package core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;

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
     * Mock object of game.
     */
    @Mock
    private Game game;

    /**
     * Mock of the user in game.
     */
    @Mock
    private User testUser;
    /**
     * Digit for number of tests.
     */
    private static final int NUMBER_OF_TESTS = 1000;

    /**
     * Sets up two instances of the Category class to be used in the tests.
     */
    @BeforeEach
    public void setUp() {
        testUser = mock(User.class);
        testUser.addCustomCategories("Custom", Arrays.asList("1", "3", "4"));
        game = mock(new Game(testUser));
    }

    /**
     * Checks that getting/setting of category is correct.
     */
    @Test
    @DisplayName("Check correct get/set of category")
    public void categoryTest() {
        assertNull(game.getChosenCategory());
        game.setCategory("fruits");
        assertEquals(game.getChosenCategory(), "fruits");
        assertThrows(IllegalArgumentException.class, () -> game.setCategory("NonExisitingCategory"),
                "Should not be able to set category to a category that does not exist");
        assertEquals(game.getChosenCategory(), "fruits");

    }

    /**
     * Check that quering and setting wordlist is correct.
     */
    @Test
    @DisplayName("Check correct get/set of wordlist")
    public void wordListTest() {
        
        assertTrue(game.getWordList().isEmpty());
        game.setCategory("fruits");
        as

    }

    /**
     * Checks that a randomly pulled word from the selection list is contained in
     * the search list.
     */
    @Test
    @DisplayName("Check valid pull of random word")
    public void testGetRandomWord() {
        for (int i = 0; i < NUMBER_OF_TESTS; i++) {

            // assertTrue(guest.getWordListForSearch().contains(randomWordFromGuest));
            //

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
        }
    }

}
