package core;

import persistence.JsonIO;
import persistence.JsonUtilities;

public abstract class AbstractAuthentication extends AbstractPersistenceAccess {

  protected AbstractAuthentication(JsonIO jsonIO) {
    super(jsonIO);
  }

  protected boolean usernameExists(String username) {
    return JsonUtilities.getAllUsernames().contains(username);
  }

  protected abstract boolean validPassword(String password);

}
