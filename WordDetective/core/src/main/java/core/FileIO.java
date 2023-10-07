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
import com.google.gson.JsonObject;

/**
 * This class is responsible for reading the wordlists from the files.
 * It also provides methods for querying the available categories.
 */
public abstract class FileIO {

    /**
     * The name of the working directory.
     */
    private static final String WORKING_DIRECTORY = "gr2325";

    /**
     * Queries and returns all default categories.
     *
     * @return All default categories.
     */
    public static Collection<String> loadDefaultCategories() {
        Path path = Paths.get("").toAbsolutePath();
        while (!path.endsWith(WORKING_DIRECTORY)) {
            path = path.getParent();
        }
        File[] defaultCategoriesArray = new File(
                path.toString() + "/WordDetective/core/src/main/resources/default_categories")
                .listFiles();
        return Arrays.asList(defaultCategoriesArray).stream()
                .map(File::getName)
                .map(name -> name.substring(0, name.indexOf("."))) // Remove file extension
                .map(name -> name.replace('_', ' ')) // Replace underscores with spaces
                .collect(Collectors.toList());
    }

    /**
     * Queries and returns custom categories if the user is a registered user.
     *
     * @param username The name of the user to provide custom categories for
     * @return All custom categories of given user
     */
    public static Collection<String> loadCustomCategories(final String username) {
        Path path = Paths.get("").toAbsolutePath();
        while (!path.endsWith(WORKING_DIRECTORY)) {
            path = path.getParent();
        }
        File[] customCategories = new File(path.toString() +
                "/WordDetective/core/src/main/resources/users/" + username + "/categories")
                .listFiles();
        return Arrays.asList(customCategories).stream()
                .map(File::getName)
                .map(name -> name.substring(0, name.indexOf(".")))
                .map(name -> name.replace('_', ' '))
                .collect(Collectors.toList());
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
    public static WordLists createWordlist(final boolean pickFromDefaultCategories, final String username,
            final String category) {
        Path path = Paths.get("").toAbsolutePath();
        while (!path.endsWith(WORKING_DIRECTORY)) {
            path = path.getParent();
        }
        Set<String> wordlistForSearch = null;
        List<String> wordlistForSelection = null;
        String chosenCategory = category.replace(' ', '_');
        if (pickFromDefaultCategories) {
            path = Paths.get(
                    path.toString() + "/WordDetective/core/src/main/resources/default_categories/" + chosenCategory
                            + ".json");
        } else {
            path = Paths.get(path.toString() + "/WordDetective/core/src/main/resources/users/" + username
                    + "/categories/" + chosenCategory + ".json");
        }
        try {
            // Files.readAllBytes method reads the file and closes it internally, thus no
            // need to manually close.
            String content = new String(Files.readAllBytes(path));
            Gson gsonParser = new Gson();
            JsonObject jsonObject = gsonParser.fromJson(content, JsonObject.class);
            JsonArray wordListArray = jsonObject.get("wordlist").getAsJsonArray();

            wordlistForSearch = new HashSet<>();
            wordlistForSelection = new ArrayList<>();

            for (int i = 0; i < wordListArray.size(); i++) {
                wordlistForSearch.add(wordListArray.get(i).getAsString());
                wordlistForSelection.add(wordListArray.get(i).getAsString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new WordLists(wordlistForSearch, wordlistForSelection);
    }

    // public static void main(String[] args) {
    // WordLists a = (createWordlist(true, null, "chemical elements"));
    // List<String> b = a.getWordListForSelection();
    // for (String string : b) {
    // System.out.println(string);
    // }
    // }

    /**
     * Access the persistent json file which contains the current score of the game.
     *
     * @return The current score of the game.
     */
    public static int getHighScore(final String username) {
        Path path = Paths.get("").toAbsolutePath();
        while (!path.endsWith(WORKING_DIRECTORY)) {
            path = path.getParent();
        }
        if (username.equals("guest")) {
            path = Paths.get(path.toString() + "/WordDetective/core/src/main/resources/default_stats/stats.json");
        } else {
            path = Paths.get(
                    path.toString() + "/WordDetective/core/src/main/resources/users/" + username + "/stats/stats.json");
        }
        int newHighscore = 0;
        try {
            String content = new String(Files.readAllBytes(path));
            Gson gsonParser = new Gson();
            JsonObject jsonObject = gsonParser.fromJson(content, JsonObject.class);
            newHighscore = jsonObject.get("highscore").getAsInt();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newHighscore;
    }

    /**
     * Access the persistent json file and increments the current score by 1.
     */
    public static void incrementHighScore(final String username) {
        Path path = Paths.get("").toAbsolutePath();
        while (!path.endsWith(WORKING_DIRECTORY)) {
            path = path.getParent();
        }
        String filePath;
        if (username.equals("guest")) {
            filePath = path.toString() + "/WordDetective/core/src/main/resources/default_stats/stats.json";
        } else {
            filePath = path.toString() + "/WordDetective/core/src/main/resources/users/" + username
                    + "/stats/stats.json";
        }
        try {
            FileReader reader = new FileReader(filePath);
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
            reader.close();
            int oldHighscore = jsonObject.get("highscore").getAsInt();
            int newHighscore = oldHighscore + 1;
            jsonObject.addProperty("highscore", newHighscore);
            FileWriter writer = new FileWriter(filePath);
            gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(jsonObject, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Access the persistent json file and resets the current score to 0.
     */
    public static void resetHighScore(final String username) {
        Path path = Paths.get("").toAbsolutePath();
        while (!path.endsWith(WORKING_DIRECTORY)) {
            path = path.getParent();
        }
        String filePath;
        if (username.equals("guest")) {
            filePath = path.toString() +
                    "/WordDetective/core/src/main/resources/default_stats/stats.json";
        } else {
            filePath = path.toString() +
                    "/WordDetective/core/src/main/resources/users/" + username + "/stats/" + "stats.json";
        }
        try {
            FileReader reader = new FileReader(filePath);
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
            reader.close();
            jsonObject.addProperty("highscore", 0); // Reset highscore to 0
            FileWriter writer = new FileWriter(filePath);
            gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(jsonObject, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Number of default categories.
     *
     * @return Integer of how many default Categories.
     */
    public static int getNumberOfDefaultCategories() {
        Path path = Paths.get("").toAbsolutePath();
        while (!path.endsWith(WORKING_DIRECTORY)) {
            path = path.getParent();
        }

        File defaultCategoriesDirectory = new File(
                path.toString() + "/WordDetective/core/src/main/resources/default_categories");

        if (defaultCategoriesDirectory.exists() && defaultCategoriesDirectory.isDirectory()) {
            File[] defaultCategoriesArray = defaultCategoriesDirectory.listFiles();
            if (defaultCategoriesArray != null) {
                return defaultCategoriesArray.length;
            }
        }

        return 0; // Default categories directory is empty or doesn't exist
    }

}
