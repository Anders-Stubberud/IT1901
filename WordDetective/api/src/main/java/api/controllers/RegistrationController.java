package api.controllers;

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

  private JsonIO jsonIO;

  @RequestMapping(value = "/RegistrationController/fireSignUp", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  public boolean fireSignUp(final @RequestParam("username") String username,
      final @PathVariable String password) {
    return jsonIO.getAllUsernames().contains(username);
  }

}
