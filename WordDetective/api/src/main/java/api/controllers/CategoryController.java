package api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.Collection;
import types.User;
import core.Game;

@RestController
public class CategoryController {

  // @Autowired
  private Game game;

  /**
   * The provided username.
   *
   * @param username The username.
   */
  @RequestMapping(value = "/GamePageController/newGame/{username}", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  public void newGameLogic(@PathVariable final String username) {
    game = new Game(new User());
  }

}
