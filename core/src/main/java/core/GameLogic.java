package core;

import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * The GameLogic class is responsible for the logic of the game.
 * It will delegate certain tasks to other objects,
 * who have more suitable functionality.
 */
public class GameLogic {

    /**
     * The wordlistForSearch is implemented as a hashset,
     * which allows for search in average O(1).
     */
    private Set<String> wordlistForSearch;
    /**
     * The wordlistForSelection is implemented as an arraylist,
     * which allows for accessing in O(1).
     */
    private List<String> wordlistForSelection;
    /**
     * The categoryLogic object is responsible for the logic of the categories.
     */
    private CategoryLogic categoryLogic;
    /**
     * The category chosen by the user.
     */
    private String chosenCategory;

    /**
     * Initializes the GameLogic object, which will control the logic.
     * Certain tasks will be delegated to objects with better functionality.
     * @param username The username of the user,
     * used to set up individualized games for different users.
     */
    public GameLogic(final String username) {
        categoryLogic = new CategoryLogic(username);
    }

    /**
     * @return this CategoryLogic class for getting and setting categories.
     */
    public CategoryLogic getCategoryLogic() {
        return categoryLogic;
    }

    /**
     * Sets up up the chosen category.
     * Delegates the task of acquiring the correct
     * words to the CategoryLogic.
     * Will throw IllegalArgumentException if the
     * chosen category is not among the
     * available categories.
     *
     * @param category The category chosen by the user.
     */
    public void setCategory(final String category) {
        if (!getCategoryLogic().getAllAvailableCategories().contains(category)) {
            throw new IllegalArgumentException("The chosen category is not a part of the available categories.");
        }
        this.chosenCategory = category;
        wordlistForSearch = categoryLogic.getWordsFromChosenCategory(category).getWordListForSearch();
        wordlistForSelection = categoryLogic.getWordsFromChosenCategory(category).getWordListForSelection();
    }

    /**
     * @return players chosen category
     *
     */
    public String getChosenCategory() {
        return chosenCategory;
    }

    /**
     * @return a set of strings containing all words...?? TODO
     */
    public Set<String> getWordListForSearch() {
        return wordlistForSearch;
    }

    /**
     * @return a list of strings containing all words from the chosen category
     */
    public List<String> getWordlistForSelection() {
        return wordlistForSelection;
    }

    /**
     * Chooses a word randomly from the selected category.
     *
     * @return A randomly generated substring from the parameter.
     */
    public String getRandomWord() 
    {
        String word = wordlistForSelection.get(new Random().nextInt(wordlistForSelection.size()));
        return word;
    }

    /**
     * Randomly generates a substring from the randomly chosen word.
     *
     * @param word - A word to make the substring from
     * @return A randomly generated substring from the randomly chosen word.
     */
    public static String getRandomSubstring(final String word) {
        int wordLength = word.length();
        int startIndexSubstring = Math.max(new Random().nextInt(wordLength) - 2, 0);
        int endIndexSubstring = startIndexSubstring + 2 + new Random().nextInt(2);
        String substring = word.substring(startIndexSubstring, endIndexSubstring);
        return substring;
    }

    /**
     * Checks if the guess is present in wordlist and wether the substring is
     * present.
     *
     * @param substring The substring used to make a guess.
     * @param guess     The guess provided by the user. Should contain substring,
     *                  and be part of the wordlist.
     * @return True if the guess is valid, else false.
     */
    public boolean checkValidWord(final String substring, final String guess) {
        return guess.matches(".*" + substring + ".*") && wordlistForSearch.contains(guess);
    }

}
