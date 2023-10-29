package api.controllers;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import persistence.AbstractJsonIO;
import persistence.JsonIO;
import core.Game;
import core.UserAccess;

@RestController
public class CategoryController {

  private UserAccess userAccess;

  // private CategoryAccess categoryAccess;

  // /**
  // * Sets ut the gameinstance to use in the game.
  // *
  // * @param user The current user.
  // */
  // @RequestMapping(value = "/CategoryController/newGame", method =
  // RequestMethod.POST)
  // @ResponseStatus(HttpStatus.OK)
  // public void newGameLogic(
  // @RequestBody final String user) {
  // game = new Game(JsonIO.convertToJavaObject(user));
  // }

  @RequestMapping(value = "/CategoryController/getCategories", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  public Set<String> getCategories(final @RequestParam("string") String username) {
    this.userAccess = new UserAccess(username);
    return userAccess.getAllCategories();
  }

}
