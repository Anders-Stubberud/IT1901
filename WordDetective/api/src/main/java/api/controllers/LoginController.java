package api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import persistence.JsonIO;
import types.User;

@RestController
@Scope("session")
public class LoginController {

  private JsonIO jsonIO;

  @Autowired
  public LoginController(JsonIO jsonIO) {
    this.jsonIO = jsonIO;
  }

  /**
   * Check if username and password is a match.
   *
   * @param username The username from the URL.
   * @param password The password from the URL.
   * @return true if the login is successful; false otherwise.
   */
  @RequestMapping(value = "/LoginController/performLogin", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  public User performLogin(final @RequestParam("username") String username) {
    return jsonIO.getUser(username);
  }

}
