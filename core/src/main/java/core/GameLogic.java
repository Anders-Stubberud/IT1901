package core;

import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class GameLogic 
{

    private Set<String> wordlistForSearch;
    private List<String> wordlistForSelection;
    private CategoryLogic categoryLogic;
    private String category;

    /**
     * Initializes the GameLogic object, which will control the logic of the game. Certain tasks will be delegated to 
     * objects who have more suitable functionality.
     * @param username The username of the user, used to set up individualized games for different users.
     */
    public GameLogic(String username)
    {
        categoryLogic = new CategoryLogic(username);
    }

    public CategoryLogic getCategoryLogic()
    {
        return categoryLogic;
    }

    /**
     * Sets up up the chosen category. Delegates the task of acquiring the correct words to the CategoryLogic.
     * Will throw IllegalArgumentException if the chosen category is not among the available categories.
     * @param category The category chosen by the user.
     */
    public void setCategory(String category)
    {
        if (!getCategoryLogic().getAllAvailableCategories().contains(category))
        {
            throw new IllegalArgumentException("The chosen category is not a part of the available categories.");
        }
        this.category = category;
        wordlistForSearch = categoryLogic.getWordsFromChosenCategory(category).getWordListForSearch();
        wordlistForSelection = categoryLogic.getWordsFromChosenCategory(category).getWordListForSelection();
    }

    public String getCategory()
    {
        return category;
    }

    public Set<String> getWordListForSearch()
    {
        return wordlistForSearch;
    }

    public List<String> getWordlistForSelection()
    {
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
        System.out.println(word);
        return word;
    }

    /**
     * Randomly generates a substring from the randomly chosen word.
     * 
     * @return A randomly generated substring from the randomly chosen word.
     */
    public static String getRandomSubstring(String word) {
        int wordLength = word.length();
        int startIndexSubstring = Math.max(new Random().nextInt(wordLength) - 2, 0);
        int endIndexSubstring = startIndexSubstring + 2 + new Random().nextInt(2);
        String substring = word.substring(startIndexSubstring, endIndexSubstring);
        return substring;
    }

    /**
     * Checks if the guess is present in wordlist and wether the substring is present.
     * @param substring The substring used to make a guess.
     * @param guess The guess provided by the user. Should contain substring, and be part of the wordlist. 
     * @return True if the guess is valid, else false.
     */
    public boolean checkValidWord(String substring, String guess)
    {
        return guess.matches(".*" + substring + ".*") && wordlistForSearch.contains(guess);
    }

}
