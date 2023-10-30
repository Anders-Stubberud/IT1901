package core;

import persistence.JsonUtilities;
import types.RegistrationResult;
import types.User;

public final class RegistrationAuthentication extends AbstractAuthentication {

  /**
   * Attemps to registrate the new user.
   * @param newUsername The new username provided by the user.
   * @param newPassword The new password provided by the user.
   * @return SUCCESS, USERNAME_TAKEN, USERNAME_NOT_MATCH_REGEX, PASSWORD_NOT_MATCH_REGEX, or UPLOAD_ERROR, respectively.
   */
  public RegistrationResult registrationResult(final String newUsername, final String newPassword) {
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
  protected boolean validPassword(final String password) {
     return password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{4,}$");
  }

    /**
     * check if username is correct according to set regex.
     *
     * @return {@link Boolean}
     */
  public boolean newUsernameMatchesRegex(final String newUsername) {
      return newUsername.matches("^(?!guest)[a-zA-Z0-9_ ]{2,}$");
  }
  
}
