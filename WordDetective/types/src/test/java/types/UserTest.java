package types;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class UserTest {
  /**
   * Invalid user used for testing.
   */
  private User invalidUser = new User("guest", "wrong");
  /**
   * Invalid user used for testing.
   */
  private User invalidUser2 = new User("no/()", "wrong");
  /**
   * Valid user used for testing.
   */
  private User validUser = new User("Testuser", "Correct123");

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
  // @Test
  // public void getPasswordTest() {
  //   assertTrue(validUser.isCorrectPassword(), "The password " + validUser.getPassword() + " should be valid");
  //   assertFalse(invalidUser.isCorrectPassword(),
  //       "The username " + invalidUser.getPassword() + " should be invalid");
  // }
}
