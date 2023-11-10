package persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.contains;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import com.google.gson.Gson;

import types.User;

public class JsonIOTest {

  /**
   * JsonIO object.
   */
  private JsonIO jsonIO;

  private User testUser;

  @BeforeEach
  public void setup() {
    testUser = new User("TestUser", "Password");
    JsonIO.addUser(testUser);
  }

  /**
   * Test user added.
   */
  @Test
  public void testUserAdded() {
    assertTrue(JsonIO.getAllUsernames().contains("TestUser"));
    JsonIO.deleteUser(testUser.getUsername());
    assertFalse(JsonIO.getAllUsernames().contains("TestUser"));
  }

  /**
   * Test adding and deleting a user.
   */
  @Test
  public void testUserOccupied() {
    assertThrows(IllegalArgumentException.class, () -> JsonIO.addUser(testUser),
        "Should not be able to add an existing user");

    assertThrows(IllegalArgumentException.class, () -> JsonIO.deleteUser("NonexistingUser"),
        "Should not be able to delete a non existing user");
    JsonIO.deleteUser(testUser.getUsername());
  }

  /**
   * Test getting a user from file.
   */
  @Test
  public void testGetUser() {
    assertEquals(testUser.getUsername(),
        JsonIO.getUser(testUser.getUsername()).getUsername());
    assertEquals(testUser.getPassword(),
        JsonIO.getUser(testUser.getUsername()).getPassword());
    assertEquals(testUser.getHighScore(),
        JsonIO.getUser(testUser.getUsername()).getHighScore());
    assertEquals(testUser.getCustomCategories(),
        JsonIO.getUser(testUser.getUsername()).getCustomCategories());
    assertThrows(RuntimeException.class, () -> JsonIO.getUser("Not existing user"));
    JsonIO.deleteUser(testUser.getUsername());
  }

  /**
   * Test updating user.
   */
  @Test
  public void testUpdateUser() {
    assertEquals(testUser.getCustomCategories(),
        JsonIO.getUser(testUser.getUsername()).getCustomCategories(),
        "TestUser should have 0 custom categories");

    testUser.setHighscore(10);
    List<String> testCategory = Arrays.asList("Test", "Test2");
    testUser.addCustomCategories("TestCategory", testCategory);
    try {
      jsonIO = new JsonIO(testUser.getUsername());
      jsonIO.updateCurrentUser(
          (user) -> {
            user.setHighscore(10);
            user.addCustomCategories("TestCategory", testCategory);
            return true;
          });
    } catch (IOException e) {
      e.printStackTrace();
    }
    User retrivedUser = JsonIO.getUser(testUser.getUsername());
    assertEquals(1, retrivedUser.getCustomCategories().size());
    assertTrue(retrivedUser.getCustomCategories().containsValue(testCategory),
        "User should now have only TestCategory as a custom category, but has: "
            + retrivedUser.getCustomCategories().keySet());

    assertEquals(10, retrivedUser.getHighScore(), "Highscore should be 10 not "
        + retrivedUser.getHighScore());
    JsonIO.deleteUser(testUser.getUsername());
  }

  /**
   * Test getting default categories.
   */
  @Test
  public void testDefaultCategory() {
    Set<String> categories = new HashSet<>(Set.of("us states", "countries", "fruits"));
    assertTrue(JsonIO.getPersistentFilenames("/default_categories").containsAll(categories));

    // Adds a testCategory in the default_categories folder
    List<String> testCategory = Arrays.asList("Hei", "pa", "deg");
    try (FileWriter fw = new FileWriter(JsonIO.getPathToResources()
        + "/default_categories/testCategory.json", StandardCharsets.UTF_8)) {
      new Gson().toJson(testCategory, fw);
    } catch (Exception e) {
      e.printStackTrace();
    }

    try {
      assertEquals(testCategory, JsonIO.getDefaultCategory("testCategory"),
          "TestCategory should be equal to retrieved category, but was: "
              + JsonIO.getDefaultCategory("testCategory"));
    } catch (IOException e) {
      assertThrows(IOException.class, () -> JsonIO.getDefaultCategory("testCategory"));
    }
    Collection<String> defaultCategories = JsonIO.getPersistentFilenames("/default_categories");
    assertEquals(9, defaultCategories.size());
    assertTrue(defaultCategories.contains("testCategory"));
    assertFalse(defaultCategories.contains("Non existing"));
    JsonIO.deleteUser(testUser.getUsername());
  }

  /**
   * Delete newly created directories for clean up.
   */
  @AfterAll
  public static void deleteDirectories() {
    try {
      Files.deleteIfExists(Paths.get(JsonIO.getPathToResources()
          + "/default_categories/testCategory.json"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
