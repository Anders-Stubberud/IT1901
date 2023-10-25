package api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import persistence.JsonIO;

@RestController
public class RegistrationController {

  /**
   * JsonIO bean to handle files.
   */
  private JsonIO jsonIO;

  /**
   * Autowired constructor injecting the JsonIO bean into the object.
   * @param jsonIOParameter
   */
  @Autowired
  public RegistrationController(final JsonIO jsonIOParameter) {
    this.jsonIO = jsonIOParameter;
  }

  /**
   * API endpoint for checking if the provided username of a new user is already taken.
   * @param username The provided username of the new user.
   * @param password The provided password of the new user.
   * @return True if username is taken, else false.
   */
  @RequestMapping(value = "/RegistrationController/fireSignUp", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  public boolean fireSignUp(final @RequestParam("username") String username,
      final @PathVariable String password) {
    return jsonIO.getAllUsernames().contains(username);
  }

}
