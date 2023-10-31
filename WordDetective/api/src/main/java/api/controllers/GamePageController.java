package api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import persistence.JsonIO;
import core.Game;

@RestController
public class GamePageController {

  /**
   * Session scoped Bean injected as dependecy from Springboot.
   */
  private Game game;

  /**
   * Sets ut the gameinstance to use in the game.
   * 
   * @param user The current user.
   */
  @RequestMapping(value = "/GamePageController/newGame", method = RequestMethod.POST)
  @ResponseStatus(HttpStatus.OK)
  public void newGameLogic(@RequestBody final String user) {
    game = new Game(JsonIO.convertToJavaObject(user));
  }

  /**
   * The selected category.
   *
   * @param category The username.
   */
  @RequestMapping(value = "/GamePageController/setCategory", method = RequestMethod.POST)
  @ResponseStatus(HttpStatus.OK)
  public void setCategory(final @RequestBody String category) {
    game.setCategory(category);
  }

  /**
   * API endpoint for fetching a random word.
   * 
   * @return A random word pulled from the current wordlist.
   */
  @RequestMapping(value = "/GamePageController/getRandomWord", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  public String getRandomWord() {
    return game.getRandomWord();
  }

  /**
   * API endpoint for fetching substring.
   * 
   * @param string The string to create a substring from.
   * @return Substring og the provided string.
   */
  @RequestMapping(value = "/GamePageController/getSubstring", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  public String getSubstring(final @RequestParam("string") String string) {
    return game.getRandomSubstring(string);
  }

  /**
   * API endpoint for check of valid word.
   * 
   * @param substring The substring provided to the user.
   * @param guess     The guess provided by the user.
   * @return Boolean indicating if guess was correct.
   */
  @RequestMapping(value = "/GamePageController/checkValidWord", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  public boolean checkValidWord(final @RequestParam("substring") String substring,
      final @RequestParam("guess") String guess) {
    return game.checkValidWord(substring, guess);
  }

  /**
   * API endpoint for saving the player's highscore to file.
   * 
   * @param highscore The score to save to file.
   */
  @RequestMapping(value = "/GamePageController/savePlayerHighscore", method = RequestMethod.POST)
  @ResponseStatus(HttpStatus.OK)
  public void savePlayerHighscore(@RequestBody final String highscore, boolean saveToDatabase) {
    game.savePlayerHighscore(Integer.parseInt(highscore), saveToDatabase);
  }

}
