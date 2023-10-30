package core;

import java.io.IOException;
import persistence.JsonUtilities;
import types.LoginResult;

public class LoginAuthentication extends AbstractAuthentication {

  /**
   * Username of the current user.
   * Used for methods dependent of the users persistently stored information.
   */
  private String username;

  /**
   * Instantiates a new instance of LoginAuthentication.
   * @param providedUsername The provided username of the user to authenticate the login of.
   */
  public LoginAuthentication(final String providedUsername) {
    this.username = providedUsername;
  }

  /**
   * Checks if the password provided by the user matches the password stored for the given username.
   * @param password The password provided by the user.
   * @return SUCCESS, USERNAME_DOES_NOT_EXIST, INCORRECT_PASSWORD, or READ_ERROR, respectively.
   */
  public LoginResult authenticate(final String password) {
    if (!usernameExists(username)) {
      return LoginResult.USERNAME_DOES_NOT_EXIST;
    }
    try {
      if (validPassword(password)) {
        return LoginResult.SUCCESS;
      }
      return LoginResult.INCORRECT_PASSWORD;
    } catch (RuntimeException e) {
      return LoginResult.READ_ERROR;
    }
  }

  @Override
  public final boolean validPassword(final String password) throws RuntimeException {
    try {
      if (JsonUtilities.usernameAndPasswordMatch(username, password)) {
        return true;
      }
      return false;
    } catch (IOException e) {
      throw new RuntimeException("User \"" + username + "\" not found in " + JsonUtilities.PATH_TO_RESOURCES + "/users");
    }
  }

}
