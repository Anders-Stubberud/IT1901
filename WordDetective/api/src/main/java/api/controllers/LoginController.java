package api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import persistence.UserIO;

@RestController
public class LoginController {

  /**
   * Check if username and password is a match.
   *
   * @param username The username from the URL.
   * @param password The password from the URL.
   * @return true if the login is successful; false otherwise.
   */
  @RequestMapping(value = "/LoginController/performLogin/{username}/{password}", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  public boolean performLogin(final @PathVariable String username, final @PathVariable String password) {
    return UserIO.correctUsernameAndPassword(username, password);
  }

}
