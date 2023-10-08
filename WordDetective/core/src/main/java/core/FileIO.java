package core;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

/**
 * This class is responsible for reading the wordlists from the files.
 * It also provides methods for querying the available categories.
 */
public abstract class FileIO {

    /**
     * The name of the working directory.
     */
    private static final String WORKING_DIRECTORY = "gr2325";

    public static String getPath() {
        Path path = Paths.get("").toAbsolutePath();
        while (!path.endsWith(WORKING_DIRECTORY)) {
            path = path.getParent();
        }
        return path.toString() + "/WordDetective/core/src/main/resources/";
    }

    public static String getPathToCategory(boolean defaultCategory, String username) {
        String path = getPath();
        if (defaultCategory) {
            return path + "default_categories/";
        } else {
            return path + "users/" + username + "/categories/";
        }
    }

    public static String getPathToStats(String username) {
        String path = getPath();
        if (username.equals("guest")) {
            return path + "default_stats/stats.json";
        } else {
            return path + "users/" + username + "/stats/stats.json";
        }
    }

    public static JsonObject getJsonObject(String path) {
        JsonObject jsonObject = null;
        // Try-with-resources closes file automatically, thus no need to manually close.
        try (FileReader reader = new FileReader(path)) {
            Gson gsonParser = new Gson();
            jsonObject = gsonParser.fromJson(reader, JsonObject.class);
        } catch (JsonSyntaxException | JsonIOException | IOException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static Collection<String> loadCategories(boolean defaultCategory, String username) {
        File[] categories = new File(getPathToCategory(defaultCategory, username)).listFiles();
        return Arrays.asList(categories).stream()
                .map(File::getName)
                .filter(name -> !name.equals(".gitkeep"))
                .map(name -> name.substring(0, name.indexOf(".")))
                .map(name -> name.replace('_', ' '))
                .collect(Collectors.toList());
    }

    /**
     * Queries and returns all default categories.
     *
     * @return All default categories.
     */
    public static Collection<String> loadDefaultCategories() {
        return loadCategories(true, "guest");
    }

    /**
     * Queries and returns custom categories if the user is a registered user.
     *
     * @param username The name of the user to provide custom categories for
     * @return All custom categories of given user
     */
    public static Collection<String> loadCustomCategories(final String username) {
        return loadCategories(false, username);
    }

    /**
     * initializes two wordlists.
     * The "wordlistForSearch" wordlist is implemented as a hashset, which allows
     * for search in average O(1).
     * The "wordlistForSelection" wordlist is implemented as an arraylist, which
     * allows for accessing in O(1).
     * The reason behind having separate lists for searching and accessing is to
     * improve time complexity.
     * This solution does require more memory, but since wordlists do not require
     * vast amounts of memory,
     * it is a fair tradeoff in order to improve the user experience.
     *
     * @param pickFromDefaultCategories Set to true if the category is to be chosen
     *                                  among the default categories.
     * @param username                  The username of the user, used to set up
     *                                  individualized games for different users.
     * @param category                  The category chosen by the user.
     * @return A WordLists object containing two wordlists.
     */
    public static WordLists createWordlist(final boolean defaultCategory, final String username,
            final String category) {
        String chosenCategory = category.replace(' ', '_');
        Set<String> wordlistForSearch = null;
        List<String> wordlistForSelection = null;
        String path = getPathToCategory(defaultCategory, username) + chosenCategory + ".json";
        JsonObject jsonObject = getJsonObject(path);
        JsonArray wordListArray = jsonObject.get("wordlist").getAsJsonArray();
        wordlistForSearch = new HashSet<>();
        wordlistForSelection = new ArrayList<>();
        for (int i = 0; i < wordListArray.size(); i++) {
            wordlistForSearch.add(wordListArray.get(i).getAsString());
            wordlistForSelection.add(wordListArray.get(i).getAsString());
        }
        return new WordLists(wordlistForSearch, wordlistForSelection);
    }

    public static JsonObject getHighScoreObject(final String username) {
        return getJsonObject(getPathToStats(username));
    }

    public static void writeToHighScoreObject(final String username, final int score) {
        JsonObject jsonObject = getHighScoreObject(username);
        try {
            jsonObject.addProperty("highscore", score);
            FileWriter writer = new FileWriter(getPathToStats(username));
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(jsonObject, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Access the persistent json file which contains the current score of the game.
     *
     * @return The current score of the game.
     */
    public static int getHighScore(final String username) {
        return getHighScoreObject(username).get("highscore").getAsInt();
    }

    /**
     * Access the persistent json file and increments the current score by 1.
     */
    public static void incrementHighScore(final String username) {
        writeToHighScoreObject(username, getHighScore(username) + 1);
    }

    /**
     * Access the persistent json file and resets the current score to 0.
     */
    public static void resetHighScore(final String username) {
        writeToHighScoreObject(username, 0);
    }

    /**
     * Number of default categories.
     *
     * @return Integer of how many default Categories.
     */
    public static int getNumberOfDefaultCategories() {
        File defaultCategoriesDirectory = new File(getPathToCategory(true, "guest"));
        if (defaultCategoriesDirectory.exists() && defaultCategoriesDirectory.isDirectory()) {
            File[] defaultCategoriesArray = defaultCategoriesDirectory.listFiles();
            if (defaultCategoriesArray != null) {
                return defaultCategoriesArray.length;
            }
        }
        return 0; // Default categories directory is empty or doesn't exist
    }

}
