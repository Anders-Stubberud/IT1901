package types;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserTest {

  /**
   * Invalid user used for testing.
   */
  private User invalidUser;
  /**
   * Invalid user used for testing.
   */
  private User invalidUser2;
  /**
   * Valid user used for testing.
   */
  private User validUser;

  /**
   * Initialize before each test.
   */
  @BeforeEach
  public void init() {
    invalidUser = new User("guest", "wrong");
    invalidUser2 = new User("no/()", "wrong");
    validUser = new User("Testuser", "Correct123");
  }

  /**
   * Test constructor.
   */
  @Test
  public void constructorTest() {
    User guest = new User();
    assertEquals(guest.getUsername(), "guest",
        "Username should be guest on empty constructor not: " + guest.getUsername());
    assertEquals(guest.getPassword(), "", "Password should be empty on guest, not:" + guest.getPassword());
    assertEquals(guest.getHighScore(), 0, "Higscore should be 0, not:" + guest.getHighScore());

    User user = new User("Username", "Password123");
    assertEquals(user.getUsername(), "Username", "Username should be 'Username' not: " + user.getUsername());
    assertEquals(user.getPassword(), "Password123",
        "Password should be 'Password123' on user, not:" + user.getPassword());
    assertEquals(user.getHighScore(), 0, "Higscore should be 0, not:" + user.getHighScore());
  }

  // /**
  //  * Test for valid and invalid usernames.
  //  */
  // @Test
  // public void getUsernameTest() {
  //   assertTrue(validUser.isCorrectUsername(), "The username " + validUser.getUsername() + " should be valid");
  //   assertFalse(invalidUser.isCorrectUsername(),
  //       "The username " + invalidUser.getUsername() + " should be invalid");
  //   assertFalse(invalidUser2.isCorrectUsername(),
  //       "The username " + invalidUser2.getUsername() + " should be invalid");
  // }

  /**
   * Test for valid and invalid passwords.
   */
  @Test
  public void getPasswordTest() {
    assertTrue(validUser.isCorrectPassword(), "The password " + validUser.getPassword() + " should be valid");
    assertFalse(invalidUser.isCorrectPassword(),
        "The username " + invalidUser.getPassword() + " should be invalid");
  }

  /**
   * Test highscore setting/getting.
   */
  @Test
  public void highscoreTest() {
    assertEquals(validUser.getHighScore(), 0, "Highscore should be 0, not:" + validUser.getHighScore());
    validUser.setHighscore(20);
    assertEquals(validUser.getHighScore(), 20, "Highscore should be 20, not:" + validUser.getHighScore());
    validUser.setHighscore(50);
    assertEquals(validUser.getHighScore(), 50, "Highscore should be 20, not:" + validUser.getHighScore());
  }

  /**
   * Test set/get custom categories.
   */
  @Test
  public void customCategoriesTest() {
    HashMap<String, List<String>> hashmap = new HashMap<>();
    assertEquals(validUser.getCustomCategories(), hashmap, "Custom categories should be empty");

    hashmap.put("TestCategory", Arrays.asList("Test", "Test", "Test"));
    validUser.addCustomCategories("TestCategory", Arrays.asList("Test", "Test", "Test"));
    assertEquals(validUser.getCustomCategories(), hashmap, "Custom categories should contain " + hashmap.toString());

    hashmap.put("TestCategory2", Arrays.asList("Test", "Test", "Test"));
    hashmap.put("TestCategory3", Arrays.asList("Test", "Test", "Test"));
    validUser.setCustomCategories(hashmap);
    assertEquals(validUser.getCustomCategories(), hashmap, "Custom categories should be same as hashmap");

    validUser.deleteCustomCategories("TestCategory");
    assertNull(validUser.getCustomCategories().get("TestCategory"), "TestCategory should have been deleted");

  }

}
