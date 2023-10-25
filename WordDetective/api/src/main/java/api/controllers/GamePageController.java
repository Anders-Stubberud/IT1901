package api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.Collection;
import persistence.JsonIO;
import types.User;
import core.Game;

@RestController
public class GamePageController {

  private Game game;

  /**
   * The provided username.
   *
   * @param username The username.
   */
  @RequestMapping(value = "/GamePageController/newGame", method = RequestMethod.POST)
  @ResponseStatus(HttpStatus.OK)
  public void newGameLogic(@RequestBody final String user) {
    // System.out.println("gamelogic newgamve username: " + user.getUsername());
    game = new Game(JsonIO.convertToJavaObject(user));
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

  @RequestMapping(value = "/GamePageController/getSubstring", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  public String getSubstring(@RequestParam("string") String string) {
    return game.getRandomSubstring(string);
  }

  @RequestMapping(value = "/GamePageController/checkValidWord", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  public boolean checkValidWord(@RequestParam("substring") String substring, @RequestParam("guess") String guess) {
    return game.checkValidWord(substring, guess);
  }

  @RequestMapping(value = "/GamePageController/savePlayerHighscore", method = RequestMethod.POST)
  @ResponseStatus(HttpStatus.OK)
  public void savePlayerHighscore(@RequestBody final String highscore) {
    System.out.println("\n\n\ncontroller\n" + highscore + "\n\n\n");
    game.savePlayerHighscore(Integer.valueOf(highscore));
  }

  

}
