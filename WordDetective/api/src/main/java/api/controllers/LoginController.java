package api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import persistence.JsonIO;
import types.User;

@RestController
public class LoginController {

  private JsonIO jsonIO;

  /**
   * Check if username and password is a match.
   *
   * @param username The username from the URL.
   * @param password The password from the URL.
   * @return true if the login is successful; false otherwise.
   */
  @RequestMapping(value = "/LoginController/performLogin/{username}", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  public User performLogin(final @PathVariable String username) {
    return jsonIO.getUser(username);
  }

}
