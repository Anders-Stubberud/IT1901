package core;

import persistence.JsonIO;

public class LoginAuthentication extends AbstractAuthentication {

  public LoginAuthentication(JsonIO jsonIO) {
    super(jsonIO);
  }

  @Override
  public boolean validPassword(String password) {
    return true;
  }
  
}
