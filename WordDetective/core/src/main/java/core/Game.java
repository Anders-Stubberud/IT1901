package core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import persistence.JsonIO;
import types.User;

/**
 * The GameLogic class is responsible for the logic of the game.
 * It will delegate certain tasks to other objects,
 * who have more suitable functionality.
 */
public final class Game implements AbstractGame {

    /**
     * The category chosen by the user.
     */
    private String chosenCategory;
    /**
     * List of answers for the chosen category.
     */
    private List<String> wordlist;
    /**
     * The active user playing this game.
     */
    private final User player;
    /**
     * Random object used to provide random numbers.
     */
    private static Random random = new Random();
    /**
     * A {@link JsonIO} object simulating our database.
     */
    private final JsonIO database;

    /**
     * Initializes the Game object, which will control the logic of the game.
     * Certain tasks will be delegated to objects with better functionality.
     *
     * @param user The the user, used to set up individualized games for different
     *             users.
     */
    public Game(final User user) {
        this.player = user;
        this.database = new JsonIO();
        this.wordlist = new ArrayList<>();
    }

    /**
     * Empty constructor if playing game with guest.
     */
    public Game() {
        this.player = new User();
        this.database = new JsonIO();
        this.wordlist = new ArrayList<>();
    }

    @Override
    public void setCategory(final String category) {
        this.wordlist = database.getDefaultCategory(category);
        if (database.getDefaultCategory(category) != null) {
            this.wordlist = database.getDefaultCategory(category);
        } else if (player.getCustomCategories().containsKey(category)) {
            this.wordlist = player.getCustomCategories().get(category);
        } else {
            throw new IllegalArgumentException(category + " is not a part of the available categories.");
        }
        this.chosenCategory = category;
    }

    @Override
    public String getChosenCategory() {
        return chosenCategory;
    }

    @Override
    public List<String> getWordList() {
        return this.wordlist;
    }

    @Override
    public void setWordList(final List<String> newWordList) {
        this.wordlist = newWordList;
    }

    @Override
    public String getRandomWord() {
        String word = wordlist.get(random.nextInt(wordlist.size()));
        return word;
    }

    @Override
    public String getRandomSubstring(final String word) {
        String substring;
        do {
            int wordLength = word.length();
            int startIndexSubstring = Math.max(random.nextInt(wordLength) - 2, 0);
            int endIndexSubstring = startIndexSubstring + 2 + random.nextInt(2);
            substring = word.substring(startIndexSubstring, endIndexSubstring);
            System.out.println(word);
        } while (substring.contains(" "));

        return substring;
    }

    @Override
    public boolean checkValidWord(final String substring, final String guess) {
        return guess.matches(".*" + substring + ".*") && wordlist.contains(guess);
    }

    @Override
    public void savePlayerHighscore(final int highscore) {
        if (player.getUsername() != "guest") {
            player.setHighscore(highscore);
            database.updateUser(player);
        }
    }

}
