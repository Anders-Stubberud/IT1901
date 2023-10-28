package core;

import persistence.JsonIO;

public abstract class AbstractAuthentication {
  
  protected final JsonIO jsonIO;

  public AbstractAuthentication(JsonIO jsonIO) {
    this.jsonIO = jsonIO;
  }
  
  public boolean usernameExists(String username) {
   return jsonIO.getAllUsernames().contains(username);
  }

  public abstract boolean validPassword(String password);

}
