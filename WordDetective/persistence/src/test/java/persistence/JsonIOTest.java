package persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.gson.Gson;

import types.User;

public class JsonIOTest {

  /**
   * The path used for testing.
   */
  private String testPath = JsonIO.getAbsolutePath("gr2325") + "/WordDetective/persistence/src/test/java/persistence";
  /**
   * JsonIO object.
   */
  private JsonIO jsonIO = new JsonIO(testPath);
  /**
   * User for testing.
   */
  private User testuser;

  /**
   * Setup before each test.
   */
  @BeforeEach
  public void init() {
    testuser = new User("Test", "Test");
    // Set up directories in test
    new File(testPath + "/users").mkdir();
    new File(testPath + "/default_categories").mkdir();
  }

  /**
   * Test constructor.
   */
  @Test
  public void testConstructor() {
    assertDoesNotThrow(() -> new JsonIO());
    assertDoesNotThrow(() -> new JsonIO(testPath));
  }

  /**
   * Test adding and deleting a user.
   */
  @Test
  public void testAddDeleteUser() {
    jsonIO.addUser(testuser);
    assertEquals(1, new File(testPath + "/users").listFiles().length);
    assertTrue(jsonIO.getAllUsernames().contains(testuser.getUsername()));
    assertThrows(IllegalArgumentException.class, () -> jsonIO.addUser(testuser),
        "Should not be able to add an existing user");

    assertThrows(IllegalArgumentException.class, () -> jsonIO.deleteUser("NonexistingUser"),
        "Should not be able to delete a non existing user");
    jsonIO.deleteUser(testuser.getUsername());
    assertEquals(0, new File(testPath + "/users").listFiles().length);
  }

  /**
   * Test getting a user from file.
   */
  @Test
  public void testGetUser() {
    jsonIO.addUser(testuser);
    assertEquals(testuser.getUsername(), jsonIO.getUser(testuser.getUsername()).getUsername());
    assertEquals(testuser.getPassword(), jsonIO.getUser(testuser.getUsername()).getPassword());
    assertEquals(testuser.getHighScore(), jsonIO.getUser(testuser.getUsername()).getHighScore());
    assertEquals(testuser.getCustomCategories(), jsonIO.getUser(testuser.getUsername()).getCustomCategories());
    assertThrows(IllegalArgumentException.class, () -> jsonIO.getUser("Not existing user"));
    jsonIO.deleteUser(testuser.getUsername());
  }

  /**
   * Test updating user.
   */
  @Test
  public void testUpdateUser() {
    jsonIO.addUser(testuser);
    assertEquals(testuser.getCustomCategories(), jsonIO.getUser(testuser.getUsername()).getCustomCategories(),
        "TestUser should have 0 custom categories");

    testuser.setHighscore(10);
    List<String> testCategory = Arrays.asList("Test", "Test2");
    testuser.addCustomCategories("TestCategory", testCategory);
    jsonIO.updateUser(testuser);

    User retrivedUser = jsonIO.getUser(testuser.getUsername());
    assertEquals(1, retrivedUser.getCustomCategories().size());
    assertTrue(retrivedUser.getCustomCategories().containsValue(testCategory),
        "User should now have only TestCategory as a custom category, but has: "
            + retrivedUser.getCustomCategories().keySet());

    assertEquals(10, retrivedUser.getHighScore(), "Highscore should be 10 not " + retrivedUser.getHighScore());
    assertThrows(IllegalArgumentException.class, () -> jsonIO.updateUser(new User()));

    jsonIO.deleteUser(testuser.getUsername());
  }

  /**
   * Test getting default categories.
   */
  @Test
  public void testDefaultCategory() {
    assertEquals(0, new File(testPath + "/default_categories").listFiles().length,
        "default_categories folder should be empty");
    List<String> testCategory = Arrays.asList("Hei", "pa", "deg");

    // Adds a testCategory in the default_categories folder
    try (FileWriter fw = new FileWriter(testPath + "/default_categories/testCategory.json", StandardCharsets.UTF_8)) {
      new Gson().toJson(testCategory, fw);
    } catch (Exception e) {
      e.printStackTrace();
    }

    assertEquals(testCategory, jsonIO.getDefaultCategory("testCategory"),
        "TestCategory should be equals retrieved category, but was: " + jsonIO.getDefaultCategory("testCategory"));
    assertEquals(1, jsonIO.getAllDefaultCategories().size());
    assertTrue(jsonIO.getAllDefaultCategories().containsKey("testCategory"));
    assertFalse(jsonIO.getAllDefaultCategories().containsKey("Non existing"));

  }

  /**
   * Delete newly created directories for clean up.
   */
  @AfterAll
  public static void deleteDirectories() {
    String path = JsonIO.getAbsolutePath("gr2325") + "/WordDetective/persistence/src/test/java/persistence";
    try {
      Files.deleteIfExists(Paths.get(path + "/default_categories/testCategory.json"));
      Files.deleteIfExists(Paths.get(path + "/default_categories"));
      Files.deleteIfExists(Paths.get(path + "/users"));
    } catch (IOException e) {
      System.out.println("Couldn't delete directory because, " + e.getLocalizedMessage());
      e.printStackTrace();
    }
  }

}
