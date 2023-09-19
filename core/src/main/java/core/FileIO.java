package core;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class FileIO 
{

    private static final String workingDirectory = "gr2325";
    
    /**
     * Queries and returns all default categories.
     * @return All default categories.
     */
    public static Collection<String> loadDefaultCategories() 
    {
        Path path = Paths.get("").toAbsolutePath();
        while (! path.endsWith(workingDirectory))
        {
            path = path.getParent();
        }
        File[] defaultCategoriesArray = new File(path.toString() + "/core/src/main/resources/default_categories").listFiles();
        return Arrays.asList(defaultCategoriesArray).stream().map(File::getName)
        .map(name -> name.substring(0, name.indexOf("."))).collect(Collectors.toList());
    }

    /**
     * Queries and returns custom categories if the user is a registered user
     * @param username The name of the user to provide custom categories for
     * @return All custom categories of given user
     */
    public static Collection<String> loadCustomCategories(String username)
    {
        Path path = Paths.get("").toAbsolutePath();
        while (! path.endsWith(workingDirectory))
        {
            path = path.getParent();
        }
        File[] customCategories = new File(path.toString() + "/core/src/main/resources/users/" + username).listFiles();
        return Arrays.asList(customCategories).stream().map(File::getName)
        .map(name -> name.substring(0, name.indexOf("."))).collect(Collectors.toList());
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
    public static WordLists createWordlist(boolean pickFromDefaultCategories, String username, String category)
    {
        Path path = Paths.get("").toAbsolutePath();
        while (! path.endsWith(workingDirectory))
        {
            path = path.getParent();
        }
        Set<String> wordlistForSearch = null;
        List<String> wordlistForSelection = null;
        if (pickFromDefaultCategories)
        {
            path = Paths.get(path.toString() + "/core/src/main/resources/default_categories/" + category + ".json");
        }
        else
        {
            path = Paths.get(path.toString() + "/core/src/main/resources/users/" + username + "/" + category + ".json");
        }
        try 
        {
            //Files.readAllBytes method reads the file and closes it internally, thus no need to manually close.
            String content = new String(Files.readAllBytes(path));
            Gson gsonParser = new Gson();
            JsonObject jsonObject = gsonParser.fromJson(content, JsonObject.class);
            JsonArray wordListArray = jsonObject.get("wordlist").getAsJsonArray();

            wordlistForSearch = new HashSet<>();
            wordlistForSelection = new ArrayList<>();

            for (int i=0; i<wordListArray.size(); i++)
            {
                wordlistForSearch.add(wordListArray.get(i).getAsString());
                wordlistForSelection.add(wordListArray.get(i).getAsString());
            }
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        return new WordLists(wordlistForSearch, wordlistForSelection);
    }

    public static void main(String [] args)
    {
        WordLists a = createWordlist(false, "registeredUser", "example_category2");
        Collection<String> b = a.getWordListForSelection();
        for (String string : b) {
            System.out.println(string);
        }
    }

}
