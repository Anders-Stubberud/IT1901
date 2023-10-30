package api.controllers;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import core.UserAccess;

@RestController
public class CategoryController {

  /**
   * UserAccess instance to provide access to persistently stored user information. 
   */
  private UserAccess userAccess;

  /**
   * API endpoint for fetching og categories related to certain user.
   * @param username Username of the suer to fetch the categories of.
   * @return Set<String> with all category names.
   */
  @RequestMapping(value = "/CategoryController/getCategories", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  public Set<String> getCategories(final @RequestParam("username") String username) {
    this.userAccess = new UserAccess(username);
    return userAccess.getAllCategories();
  }

}
