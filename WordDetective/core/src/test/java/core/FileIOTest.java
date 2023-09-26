package core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FileIOTest {

    @BeforeEach
    void setUp() {
        // You can perform any setup here if needed.
    }

    @Test
    void testLoadDefaultCategories() {
        Collection<String> defaultCategories = FileIO.loadDefaultCategories();
        assertNotNull(defaultCategories);
        assertEquals(2, defaultCategories.size()); // Update the expected size as we increase number of added categories.
    }

    @Test
    void testLoadCustomCategories() {
        String username = "testUser"; // Change to valid username as we add user functionality
        Collection<String> customCategories = FileIO.loadCustomCategories(username);
        assertNotNull(customCategories);
    }

    @Test
    void testCreateWordlist() {
        boolean pickFromDefaultCategories = true;
        String username = "testUser"; // Change to valid username as we add user functionality
        String category = "testCategory"; // Change to an existing category for your test data.

        WordLists wordLists = FileIO.createWordlist(pickFromDefaultCategories, username, category);
        assertNotNull(wordLists);
        assertEquals(2, wordLists.getWords().size()); // Update when user gets Added TODO
    }

    @Test
    void testGetHighScore() {
        int highScore = FileIO.getHighScore();
        assertEquals(3, highScore); // Highscore should be 0 at the start of the game. O
    }

    @Test
    void testIncrementHighScore() {
        int initialHighScore = FileIO.getHighScore();
        FileIO.incrementHighScore();
        int updatedHighScore = FileIO.getHighScore();
        assertEquals(initialHighScore + 1, updatedHighScore);
        // Reset the highscore to 0 for other tests.
    }
}