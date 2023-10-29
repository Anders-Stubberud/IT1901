package api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import core.LoginAuthentication;
import persistence.JsonIO;
import types.LoginResult;
import types.User;

@RestController
@Scope("session")
public class LoginController {

  /**
   * JsonIO bean to handle files.
   */
  private LoginAuthentication authentication;

  /**
   * Autowired constructor injecting the JsonIO bean into the object.
   * @param jsonIOParameter The bean to be injected.
   */
  @Autowired
  public LoginController(final LoginAuthentication authentication) {
    this.authentication = authentication;
  }

  /**
   * Fetches user in order to gain access to login details.
   * @param username The username of the provided user.
   * @return User object with access to the users login details.
   */
  @RequestMapping(value = "/LoginController/performLogin", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  public LoginResult performLogin(final @RequestParam("username") String username, final @RequestParam("password") String password) {
    if (authentication.getUsername() == null) {
      authentication.setUsername(username);
    }
    return authentication.authenticate(password);
  }

}
