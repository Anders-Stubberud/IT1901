package core;

import persistence.JsonUtilities;
import types.RegistrationResult;
import types.User;

public final class RegistrationAuthentication extends AbstractAuthentication {

  public RegistrationResult registrationResult(String newUsername, String newPassword) {
    if (usernameExists(newUsername)) {
      return RegistrationResult.USERNAME_TAKEN;
    }
    if (! newUsernameMatchesRegex(newUsername)) {
      return RegistrationResult.USERNAME_NOT_MATCH_REGEX;
    }
    if (! validPassword(newPassword)) {
      return RegistrationResult.PASSWORD_NOT_MATCH_REGEX;
    }
    if (JsonUtilities.SuccessfullyAddedUserPersistently(new User(newUsername, newPassword))) {
      return RegistrationResult.SUCCESS;
     }
    return RegistrationResult.UPLOAD_ERROR;
  }

  /**
   * check if password is correct according to set regex.
   *
   * @return {@link Boolean}
   */
  @Override
  protected boolean validPassword(String password) {
     return password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{4,}$");
  }

    /**
     * check if username is correct according to set regex.
     *
     * @return {@link Boolean}
     */
  public boolean newUsernameMatchesRegex(String newUsername) {
      return newUsername.matches("^(?!guest)[a-zA-Z0-9_ ]{2,}$");
  }
  
}
