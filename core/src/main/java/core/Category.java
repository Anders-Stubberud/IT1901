package core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Category 
{

    private String category;
    private String username;
    private Collection<String> wordlistForSearch;
    private List<String> wordlistForSelection;
    
    /**
     * Constructor setting up the provided category
     * @param category The name of the chosen category
     * @param pickFromDefaultCategories If true, the category is to be found in the folder of the default categories,
     * else the category is to be found the the folder of the users custom categories.
     * @param username The username of the user. Is used during search for custom categories
     * if "pickFromDefaultCategories" is set to true.
     */
    public Category(String category, boolean pickFromDefaultCategories, String username)
    {
        this.category = category;
        this.username = username;
        createWordlist(pickFromDefaultCategories);
    }

    /**
     * initializes two wordlists.
     * The "wordlistForSearch" wordlist is implemented as a hashset, which allows for search in average O(1).
     * The "wordlistForSelection" wordlist is implemented as an arraylist, which allows for accessing in O(1).
     * The reason behind having separate lists for searching and accessing is to improve time complexity.
     * This solution does require more memory, but since wordlists do not require vast amounts of memory,
     * it is a fair tradeoff in order to improve the user experience.
     * @param pickFromDefaultCategories Set to true if the category is to be chosen among the default categories.
     */
    public void createWordlist(boolean pickFromDefaultCategories)
    {
        File file;
        if (pickFromDefaultCategories)
        {
            file = new File("/gr2325/core/src/main/resources/default_categories/" + category + ".txt");
        }
        else
        {
            file = new File("/gr2325/core/src/main/resources/users/" + username + "/" + category + ".txt");
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) 
        {
            wordlistForSearch = new HashSet<>();
            wordlistForSelection = new ArrayList<>();

            String word;
            while ((word = reader.readLine()) != null)
            {
                wordlistForSearch.add(word);
                wordlistForSelection.add(word);
            }
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Chooses a word randomly from the selected category
     * 
     * @return A randomly generated substring from the parameter
     */
    public String getRandomWord() 
    {
        return wordlistForSelection.get(new Random().nextInt(wordlistForSelection.size()));
    }

    /**
     * Randomly generates a substring from the randomly chosen word
     * 
     * @return A randomly generated substring from the randomly chosen word
     */
    public static String getRandomSubstring(String word) {
        int wordLength = word.length();
        int startIndexSubstring = new Random().nextInt(wordLength);
        int endIndexSubstring = new Random().nextInt(wordLength - startIndexSubstring) + startIndexSubstring + 1;
        String substring = word.substring(startIndexSubstring, endIndexSubstring);
        return substring;
    }

    /**
     * Checks if the guess is present in wordlist and wether the substring is present
     * @param substring The substring used to make a guess
     * @param guess The guess provided by the user. Should contain substring, and be part of the wordlist. 
     * @return True if the guess is valid, else false.
     */
    public boolean checkValidWord(String substring, String guess)
    {
        return guess.matches(".*" + substring + ".*") && wordlistForSearch.contains(guess);
    }

    public String getCategoryName()
    {
        return category;
    }

    public Collection<String> getWordListForSearch()
    {
        return wordlistForSearch;
    }

    public Collection<String> getWordlistForSelection()
    {
        return wordlistForSelection;
    }

}
