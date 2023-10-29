package core;

import persistence.JsonIO;
import persistence.JsonUtilities;

public abstract class AbstractAuthentication {

  protected static boolean usernameExists(String username) {
    return JsonUtilities.getPersistentFilenames("/users").contains(username);
  }

  protected abstract boolean validPassword(String password);

}
