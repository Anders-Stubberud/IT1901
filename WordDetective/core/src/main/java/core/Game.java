package core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

import persistence.JsonIO;
import types.User;

/**
 * The GameLogic class is responsible for the logic of the game.
 * It will delegate certain tasks to other objects,
 * who have more suitable functionality.
 */
public final class Game extends UserAccess implements AbstractGame {

    private boolean isGuestUser;

    /**
     * The category chosen by the user.
     */
    private String chosenCategory;
    /**
     * List of answers for the chosen category.
     */
    private List<String> wordlist;
    // /**
    //  * The active user playing this game.
    //  */
    // private final User user;
    /**
     * Random object used to provide random numbers.
     */
    private static Random random = new Random();

    // /**
    //  * A {@link JsonIO} object simulating our database.
    //  */
    // private final JsonIO jsonIO;

    /**
     * Initializes the Game object, which will control the logic of the game.
     * Certain tasks will be delegated to objects with better functionality.
     *
     * @param user The the user, used to set up individualized games for different
     *             users.
     */
    public Game(final String username) {
        super(username);
        this.isGuestUser = username.equals("guest");
        // this.user = jsonIO.getCurrentUser();
        // this.wordlist = new ArrayList<>();
    }

    // /**
    //  * Empty constructor if playing game with guest.
    //  */
    // public Game() {
    //     this.player = new User();
    //     this.database = new JsonIO();
    //     this.wordlist = new ArrayList<>();
    // }

    @Override
    public void setCategory(final String category) {
        try {
            this.wordlist = jsonIO.getCategoryWordlist(category);
        } catch (IOException e) {
            // TODO passende exception
            e.printStackTrace();
        }
        // this.wordlist = jsonIO.getDefaultCategory(category);
        // if (wordlist == null && (!user.getCustomCategories().containsKey(category))) {
        //     throw new IllegalArgumentException(category + " is not a part of the available categories.");
        // } else if (jsonIO.getDefaultCategory(category) != null) {
        //     this.wordlist = jsonIO.getDefaultCategory(category);
        // } else {
        //     this.wordlist = user.getCustomCategories().get(category);
        // }
        // this.chosenCategory = category;
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
        int wordLength = word.length();
        int startIndexSubstring = Math.max(random.nextInt(wordLength) - 2, 0);
        int endIndexSubstring = startIndexSubstring + 2 + random.nextInt(2);
        String substring = word.substring(startIndexSubstring, endIndexSubstring);
        return substring;
    }

    @Override
    public boolean checkValidWord(final String substring, final String guess) {
        return guess.matches(".*" + substring + ".*") && wordlist.contains(guess);
    }

    @Override
    public void savePlayerHighscore(final int highscore) {
        if (isGuestUser) {
            try {
                jsonIO.updateCurrentUser(
                    (user) -> {
                        if (user.getHighScore() < highscore) {
                            user.setHighscore(highscore);
                            return true;
                        }
                        return false;
                    }
                );
            } catch (IOException e) {
                //evt håndtere den
                e.printStackTrace();
            }
        }
    }

}
