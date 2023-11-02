package api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import persistence.JsonIO;

@RestController
@Scope("session")
public class LoginController {

  /**
   * JsonIO bean to handle files.
   */
  private JsonIO jsonIO;

  /**
   * Autowired constructor injecting the JsonIO bean into the object.
   * @param jsonIOParameter The bean to be injected.
   */
  @Autowired
  public LoginController(final JsonIO jsonIOParameter) {
    this.jsonIO = jsonIOParameter;
  }

  /**
   * Fetches user in order to gain access to login details.
   * @param username The username of the provided user.
   * @return User object with access to the users login details.
   */
  @RequestMapping(value = "/LoginController/performLogin", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  public String performLogin(final @RequestParam("username") String username) {
    Gson gson = new Gson();
    return gson.toJson(jsonIO.getUser(username));
  }

}
