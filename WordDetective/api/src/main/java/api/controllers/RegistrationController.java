package api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import persistence.UserIO;

@RestController
public class RegistrationController {

  @RequestMapping(value = "/RegistrationController/fireSignUp/{username}", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  public boolean fireSignUp(final @PathVariable String username,
      final @PathVariable String password) {
    return UserIO.getAllUsernames().contains(username);
  }

}
