package core;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CategoryLogic 
{

    private String username;
    private Collection<String> availableDefaultCategories;
    private Collection<String> availableCustomCategories;

    /**
     * Initializes the logic regarding the categories. 
     * Delegates the task of querying the available categories to FileIO.
     * @param username The username of the user, which is used to access the available wordlists of the user.
     */
    public CategoryLogic(String username)
    {
        this.username = username;
        availableDefaultCategories = FileIO.loadDefaultCategories();
        if (! username.equals("guest"))
        {
            availableCustomCategories = FileIO.loadCustomCategories(username);
        }
    }

    public Collection<String> getAvailableDefaultCategories()
    {
        return availableDefaultCategories;
    }

    public Collection<String> getAvailableCustomCategories()
    {
        return availableCustomCategories;
    }

    /**
     * Combines the default and custom categories.
     * @return A collection consisting of all default and custom categories.
     */
    public Collection<String> getAllAvailableCategories()
    {
        return availableCustomCategories != null ? Stream.of(availableDefaultCategories, availableCustomCategories)
        .flatMap(Collection::stream).collect(Collectors.toList()) : availableDefaultCategories;
    }

    /**
     * Delegates the task of acquiring the wordlist for the chosen category to FileIO.
     * @param chosenCategory the category selected by the user.
     * @return A WorList object, which contains the wordlists for searching and selection.
     */
    public WordLists getWordsFromChosenCategory(String chosenCategory)
    {
        return FileIO.createWordlist(getAvailableDefaultCategories().contains(chosenCategory), username, chosenCategory);
    }

    public static void main(String [] args)
    {
        System.out.println("\n\n" + System.getProperty("user.dir") + "\n\n");
    }

}
