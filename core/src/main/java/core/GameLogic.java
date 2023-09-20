package core;

import java.util.List;
import java.util.Random;
import java.util.Set;

public final class GameLogic {

    /**
     * Set of words from category.
     * Is used to search faster in FiliIO.java.
     */
    private Set<String> wordlistForSearch;

    /**
     * List of words from category.
     * Is used to select random words from.
     */
    private List<String> wordlistForSelection;
    /**
     * Used for getting and setting categories.
     */
    private CategoryLogic categoryLogic;
    /**
     * Players chosen category.
     */
    private String chosenCategory;

    /**
     * Initializes the GameLogic object,
     * which will control the logic of the game.
     * Certain tasks will be delegated to
     * objects who have more suitable functionality.
     *
     * @param username The username of the user,
     *                 used to set up individualized games for different users.
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
    public String getRandomWord() {
        return wordlistForSelection.get(new Random().nextInt(wordlistForSelection.size()));
    }

    /**
     * Randomly generates a substring from the randomly chosen word.
     *
     * @param word - A word to make the substring from
     * @return A randomly generated substring from the randomly chosen word.
     */
    public static String getRandomSubstring(final String word) {
        int wordLength = word.length();
        int startIndexSubstring = new Random().nextInt(wordLength);
        int endIndexSubstring = new Random().nextInt(wordLength - startIndexSubstring) + startIndexSubstring + 1;
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
