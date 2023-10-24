package api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.Collection;

import core.CategoryLogic;
import core.GameLogic;

@RestController
public class GamePageController {

  // @Autowired
  private GameLogic gameLogic;

  /**
   * The provided username.
   *
   * @param username The username.
   */
  @RequestMapping(value = "/GamePageController/newGameLogic/{username}", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  public void newGameLogic(@PathVariable final String username) {
    gameLogic = new GameLogic(username);
  }

  /**
   * The selected category.
   *
   * @param category The username.
   */
  @RequestMapping(value = "/GamePageController/setCategory/{category}", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  public void setCategory(@PathVariable final String category) {
    gameLogic.setCategory(category);
  }

  @RequestMapping(value = "/GamePageController/getRandomWord", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  public String getRandomWord() {
    return gameLogic.getRandomWord();
  }

}
