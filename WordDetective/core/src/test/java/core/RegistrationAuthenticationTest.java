package core;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import persistence.JsonIO;
import types.RegistrationStatus;

class RegistrationAuthenticationTest {

  private RegistrationAuthentication registrationAuthentication;

  @BeforeEach
  void setUp() {
    registrationAuthentication = new RegistrationAuthentication();
  }

  @Test
  void testRegistrationSuccess() {
    RegistrationStatus result = registrationAuthentication.registrationResult("newUser", "Password123");
    assertEquals(RegistrationStatus.SUCCESS, result);
  }

  @Test
  void testRegistrationUsernameTaken() {
    RegistrationStatus result = registrationAuthentication.registrationResult("newUser", "Password123");
    assertEquals(RegistrationStatus.USERNAME_TAKEN, result);
  }

  @Test
  void testRegistrationInvalidUsername() {
    RegistrationStatus result = registrationAuthentication.registrationResult("invalidUsername$", "Password123");
    assertEquals(RegistrationStatus.USERNAME_NOT_MATCH_REGEX, result);
  }

  @Test
  void testRegistrationInvalidPassword() {
    RegistrationStatus result = registrationAuthentication.registrationResult("bigBossMan", "weak");
    assertEquals(RegistrationStatus.PASSWORD_NOT_MATCH_REGEX, result);
  }

  @Test
  void testIsValidPassword() {
    assertTrue(registrationAuthentication.isValidPassword("StrongPassword123"));
    assertFalse(registrationAuthentication.isValidPassword("weak"));
  }

  @Test
  void testIsValidUsername() {
    assertTrue(registrationAuthentication.isValidUsername("validUsername123"));
    assertFalse(registrationAuthentication.isValidUsername("invalidUsername$"));
    assertFalse(registrationAuthentication.isValidUsername("guestUsername"));
  }

  @AfterAll
  static void tearDown() {
    // Clean up by deleting the users created during the tests
    JsonIO.deleteUser("newUser");
    JsonIO.deleteUser("bigBossMan");
    // Add additional calls to deleteUser as needed for other test users
  }
}