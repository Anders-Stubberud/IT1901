package core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        int expectedSize = getDefaultCategoryLength();
        assertEquals(expectedSize, defaultCategories.size());
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

    /**
     * Overview of how many default categories there are in
     * resources/default_categories.json.
     *
     * @return Integer of how many default categories.
     *
     */
    private int getDefaultCategoryLength() {
        Path path = Paths.get("").toAbsolutePath();
        while (!path.endsWith("gr2325")) {
            path = path.getParent();
        }

        File defaultCategoriesDirectory = new File(
                path.toString() + "/WordDetective/core/src/main/resources/default_categories");

        if (defaultCategoriesDirectory.exists() && defaultCategoriesDirectory.isDirectory()) {
            File[] defaultCategoriesArray = defaultCategoriesDirectory.listFiles();
            if (defaultCategoriesArray != null) {
                return defaultCategoriesArray.length;
            }
        }
        return 0;
    }
}