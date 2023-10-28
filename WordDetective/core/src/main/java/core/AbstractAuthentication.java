package core;

import persistence.JsonIO;

public abstract class AbstractAuthentication extends AbstractPersistenceAccess {

  protected AbstractAuthentication(JsonIO jsonIO) {
    super(jsonIO);
  }

  public boolean usernameExists(String username) {
    return jsonIO.getAllUsernames().contains(username);
  }

  protected abstract boolean validPassword(String password);

}
