package core;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import persistence.JsonIO;
import types.RegistrationStatus;

public class RegistrationAuthenticationTest {

  private RegistrationAuthentication registrationAuthentication;

  @BeforeEach
  public void setUp() {
    registrationAuthentication = new RegistrationAuthentication();
  }

  @Test
  public void testRegistrationSuccess() {
    RegistrationStatus result = registrationAuthentication.registrationResult("newUser", "Password123");
    assertEquals(RegistrationStatus.SUCCESS, result);
  }

  @Test
  public void testRegistrationUsernameTaken() {
    RegistrationStatus result = registrationAuthentication.registrationResult("newUser", "Password123");
    assertEquals(RegistrationStatus.USERNAME_TAKEN, result);
  }

  @Test
  public void testRegistrationInvalidUsername() {
    RegistrationStatus result = registrationAuthentication.registrationResult("invalidUsername$", "Password123");
    assertEquals(RegistrationStatus.USERNAME_NOT_MATCH_REGEX, result);
  }

  @Test
  public void testRegistrationInvalidPassword() {
    RegistrationStatus result = registrationAuthentication.registrationResult("bigBossMan", "weak");
    assertEquals(RegistrationStatus.PASSWORD_NOT_MATCH_REGEX, result);
  }

  @Test
  public void testIsValidPassword() {
    assertTrue(registrationAuthentication.isValidPassword("StrongPassword123"));
    assertFalse(registrationAuthentication.isValidPassword("weak"));
  }

  @Test
  public void testIsValidUsername() {
    assertTrue(registrationAuthentication.isValidUsername("validUsername123"));
    assertFalse(registrationAuthentication.isValidUsername("invalidUsername$"));
    assertFalse(registrationAuthentication.isValidUsername("guestUsername"));
  }

  @AfterAll
  public static void cleanUpAfterAllTests() {
    JsonIO.deleteUser("newUser");
  }

}