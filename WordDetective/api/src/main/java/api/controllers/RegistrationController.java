package api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import core.RegistrationAuthentication;

@RestController
public class RegistrationController {

  /**
   * JsonIO bean to handle files.
   */
  private RegistrationAuthentication authentication;

  /**
   * Autowired constructor injecting the JsonIO bean into the object.
   * @param jsonIOParameter
   */
  @Autowired
  public RegistrationController(final RegistrationAuthentication authentication) {
    this.authentication = authentication;
  }

  /**
   * API endpoint for registration of User. Returns value indicating result of registration.
   * @param username The provided username of the new user.
   * @param password The provided password of the new user.
   * @return SUCCESS if user created, else USERNAME_TAKEN, USERNAME_NOT_MATCH_REGEX, PASSWORD_NOT_MATCH_REGEX, or UPLOAD_ERROR.
   */
  @RequestMapping(value = "/RegistrationController/registrationResult", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  public String registrationResult(final @RequestParam("username") String username, final @RequestParam("password") String password) {
    return "{ \"registrationResult\": \"" + authentication.registrationResult(username, password).toString() + "\" }";
  }

  // /**
  //  * API endpoint for registering new user.
  //  * @param user String representation of Json file representing the given user.
  //  */
  // @RequestMapping(value = "/registrationController/addUser", method = RequestMethod.POST)
  // @ResponseStatus(HttpStatus.OK)
  // public void addUser(final @RequestBody String user) {
  //   jsonIO.addUser(JsonIO.convertToJavaObject(user));
  // }
 
}
