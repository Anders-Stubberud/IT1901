package core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FileIOTest {

    /**
     * Reset the highscore to 0 before each test.
     */
    @BeforeEach
    public void setUp() {
        FileIO.resetHighScore(); // Reset the highscore to 0 before each test
    }

    /**
     * Test the loading of default categories from the json file.
     */
    @Test
    public void testLoadDefaultCategories() {
        Collection<String> defaultCategories = FileIO.loadDefaultCategories();
        assertNotNull(defaultCategories);
        assertEquals(2, defaultCategories.size());
    }

    /**
     * Test the loading of highscore from the json file.
     */
    @Test
    public void testGetHighScore() {
        int highScore = FileIO.getHighScore();
        assertEquals(0, highScore); // Highscore should be 0 at the start of the game.
    }

    /**
     * Test the increment of highscore.
     */
    @Test
    public void testIncrementHighScore() {
        int initialHighScore = FileIO.getHighScore();
        FileIO.incrementHighScore();
        int updatedHighScore = FileIO.getHighScore();
        assertEquals(initialHighScore + 1, updatedHighScore);
    }
}