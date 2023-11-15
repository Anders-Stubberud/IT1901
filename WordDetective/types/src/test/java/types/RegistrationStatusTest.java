package types;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class RegistrationStatusTest {

  @Test
  public void testSuccess() {
    assertEquals(RegistrationStatus.SUCCESS, RegistrationStatus.valueOf("SUCCESS"));
  }

  @Test
  public void testUsernameTaken() {
    assertEquals(RegistrationStatus.USERNAME_TAKEN, RegistrationStatus.valueOf("USERNAME_TAKEN"));
  }

  @Test
  public void testUsernameNotMatchRegex() {
    assertEquals(RegistrationStatus.USERNAME_NOT_MATCH_REGEX, RegistrationStatus.valueOf("USERNAME_NOT_MATCH_REGEX"));
  }

  @Test
  public void testPasswordNotMatchRegex() {
    assertEquals(RegistrationStatus.PASSWORD_NOT_MATCH_REGEX, RegistrationStatus.valueOf("PASSWORD_NOT_MATCH_REGEX"));
  }

  @Test
  public void testUploadError() {
    assertEquals(RegistrationStatus.UPLOAD_ERROR, RegistrationStatus.valueOf("UPLOAD_ERROR"));
  }

  // Add more tests as needed
}
