package api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.Collection;
import types.User;
import core.Game;

@RestController
public class GamePageController {

  // @Autowired
  private Game game;

  /**
   * The provided username.
   *
   * @param username The username.
   */
  @RequestMapping(value = "/GamePageController/newGame", method = RequestMethod.POST)
  @ResponseStatus(HttpStatus.OK)
  public void newGameLogic(@RequestBody final User user) {
    game = new Game(user);
  }

  /**
   * The selected category.
   *
   * @param category The username.
   */
  @RequestMapping(value = "/GamePageController/setCategory", method = RequestMethod.POST)
  @ResponseStatus(HttpStatus.OK)
  public void setCategory(@RequestBody final String category) {
    game.setCategory(category);
  }

  @RequestMapping(value = "/GamePageController/getRandomWord", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  public String getRandomWord() {
    return game.getRandomWord();
  }

  @RequestMapping(value = "GamePageController/getSubstring/{string}", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  public String getSubstring(@PathVariable String string) {
    return game.getRandomSubstring(string);
  }

  @RequestMapping(value = "GamePageController/checkValidWord/{substring}/{guess}", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  public boolean checkValidWord(@PathVariable String substring, @PathVariable String guess) {
    return game.checkValidWord(substring, guess);
  }

}
