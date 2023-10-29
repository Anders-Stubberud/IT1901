package core;

import java.io.IOException;
import persistence.JsonUtilities;
import types.LoginResult;

public class LoginAuthentication extends AbstractAuthentication {

  private String username;

  public LoginResult authenticate(String password) {
    if (! usernameExists(username)) {
      return LoginResult.USERNAME_DOES_NOT_EXIST;
    }
    try {
      if (validPassword(password)) {
        return LoginResult.SUCCESS;
      }
      return LoginResult.INCORRECT_PASSWORD;
    } catch (NullPointerException e) {
      return LoginResult.READ_ERROR;
    }
  }

  @Override
  public boolean validPassword(String password) throws NullPointerException {
    try {
      if (JsonUtilities.usernameAndPasswordMatch(username, password)) {
        return true;
      }
      return false;
    }
    catch (IOException e) {
      throw new NullPointerException("User \"" + username + "\" not found in " + JsonUtilities.pathToResources + "/users");
    }
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getUsername() {
    return this.username;
  }
  
}
