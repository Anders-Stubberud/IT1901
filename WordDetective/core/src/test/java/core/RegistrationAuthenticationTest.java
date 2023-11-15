import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import types.RegistrationStatus;

public class RegistrationAuthenticationTest {

    private RegistrationAuthentication registrationAuthentication;

    @Before
    public void setUp() {
        registrationAuthentication = new RegistrationAuthentication();
    }

    @Test
    public void testRegistrationSuccess() {
        String newUsername = "testUser";
        String newPassword = "Test@123";
        RegistrationStatus result = registrationAuthentication.registrationResult(newUsername, newPassword);
        assertEquals(RegistrationStatus.SUCCESS, result);
    }

    @Test
    public void testUsernameTaken() {
        String existingUsername = "existingUser";
        String newPassword = "Test@123";
        RegistrationStatus result = registrationAuthentication.registrationResult(existingUsername, newPassword);
        assertEquals(RegistrationStatus.USERNAME_TAKEN, result);
    }

    @Test
    public void testInvalidUsername() {
        String invalidUsername = "invalid#user";
        String newPassword = "Test@123";
        RegistrationStatus result = registrationAuthentication.registrationResult(invalidUsername, newPassword);
        assertEquals(RegistrationStatus.USERNAME_NOT_MATCH_REGEX, result);
    }

    @Test
    public void testInvalidPassword() {
        String newUsername = "testUser";
        String invalidPassword = "invalidPassword";
        RegistrationStatus result = registrationAuthentication.registrationResult(newUsername, invalidPassword);
        assertEquals(RegistrationStatus.PASSWORD_NOT_MATCH_REGEX, result);
    }

    @Test
    public void testUploadError() {
        // Assuming there might be an issue with the JSON upload
        String newUsername = "testUser";
        String newPassword = "Test@123";
        // Mock the addUser method to simulate an upload error
        JsonIO.setMockAddUserResult(false);
        RegistrationStatus result = registrationAuthentication.registrationResult(newUsername, newPassword);
        assertEquals(RegistrationStatus.UPLOAD_ERROR, result);
    }
}