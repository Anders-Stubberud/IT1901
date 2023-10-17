package persistence;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import org.junit.jupiter.api.Test;
import com.google.gson.JsonObject;

import persistence.UserIO;

public class UserIOTest {

  /**
   * Tests that the path used to retrieve user information is correct.
   */
  @Test
  public void testCorrectPath() {
    String correctPath = "gr2325/WordDetective/persistence/src/main/resources/users";
    String absolutePath = UserIO.getPath();
    assertTrue(absolutePath.contains(correctPath));
  }

  /**
   * Tests that occupied usernames are not recognized as valid.
   */
  @Test
  public void testRetrievalOfUsernames() {
    Collection<String> assortedUsernames = Arrays.asList("registeredUser", "Anders");
    Collection<String> collectedUsernames = UserIO.getAllUsernames();
    assertTrue(collectedUsernames.containsAll(assortedUsernames),
        "registeredUser and Anders should be among the users");
  }

  /**
   * Tests that the JSON file used to write userstats in is loaded correctly.
   */
  @Test
  public void testRetrieveJSON() {
    JsonObject jsonObject = UserIO.getJsonObject(
        UserIO.getPath() + "/registeredUser/stats/stats.json");
    assertTrue(jsonObject.has("highscore"));
    assertTrue(jsonObject.has("password"));
  }

  /**
   * Tests that valid combinations of usernames and paswords are recognized.
   */
  @Test
  public void testUsernameAndPassword() {
    String username = "registeredUser";
    String correctPassword = "password123";
    String incorrectPassword = "incorrectPassword";
    assertTrue(UserIO.correctUsernameAndPassword(username, correctPassword));
    assertFalse(UserIO.correctUsernameAndPassword(username, incorrectPassword));
  }

  /**
   * Creates the profile of a new user.
   */
  @Test
  public void testCreateUser() {
    String username = "testUser";
    String password = "testPassword";
    assertFalse(UserIO.getAllUsernames().contains(username));
    UserIO.insertNewUser(username, password);
    assertTrue(UserIO.getAllUsernames().contains(username));
  }

  /**
   * Deltes a given user, in this case the user created in the previous test.
   */
  @Test
  public void testDeleteUser() {
    String username = "testUser";
    assertTrue(UserIO.getAllUsernames().contains(username));
    String filepath = UserIO.getPath() + "/" + username;
    File file = new File(filepath);
    UserIO.deleteUser(file);
    assertFalse(UserIO.getAllUsernames().contains(username));
  }

}
