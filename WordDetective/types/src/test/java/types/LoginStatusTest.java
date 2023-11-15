package types;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class LoginStatusTest {

  @Test
  public void testSuccess() {
    assertEquals(LoginStatus.SUCCESS, LoginStatus.valueOf("SUCCESS"));
  }

  @Test
  public void testUsernameDoesNotExist() {
    assertEquals(LoginStatus.USERNAME_DOES_NOT_EXIST, LoginStatus.valueOf("USERNAME_DOES_NOT_EXIST"));
  }

  @Test
  public void testIncorrectPassword() {
    assertEquals(LoginStatus.INCORRECT_PASSWORD, LoginStatus.valueOf("INCORRECT_PASSWORD"));
  }

  @Test
  public void testReadError() {
    assertEquals(LoginStatus.READ_ERROR, LoginStatus.valueOf("READ_ERROR"));
  }

  // Add more tests as needed
}
