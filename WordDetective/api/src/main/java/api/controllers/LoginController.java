package api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import core.LoginAuthentication;
import types.LoginResult;

@RestController
// @Scope("session")
public class LoginController {

  /**
   * LoginAuthentication instance to provide access to required persistently stored user information.
   */
  private LoginAuthentication authentication;

  /**
   * API endpoint for check of valid login information.
   * @param username The username provided by the user.
   * @param password The password provided by the user.
   * @return SUCCESS, USERNAME_DOES_NOT_EXIST, INCORRECT_PASSWORD, or READ_ERROR, respectively.
   */
  @RequestMapping(value = "/LoginController/performLogin", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  public LoginResult performLogin(final @RequestParam("username") String username, final @RequestParam("password") String password) {
    if (authentication == null) {
      authentication = new LoginAuthentication(username);
    }
    return authentication.authenticate(password);
  }

}
