package persistence;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import types.User;

/**
 * This class is responsible for reading the wordlists from the files.
 * It also provides methods for querying the available categories.
 */
public final class JsonIO implements AbstractJsonIO {

    /**
     * The game's user instance, which is an object with identical state to the
     * persistent json file of the current user.
     */
    private String user;

    /**
     * Gson object user for seralizastion/deserialazation.
     */
    private Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Set containing the default categories, which are shared amongst all users.
     */
    private static final Set<String> DEFAULT_CATEGORY_NAMES = JsonUtilities
            .getPersistentFilenames("/default_categories");

    /**
     * Set containing teh custom categories, which are unique to each user.
     */
    private Set<String> customCategoryNames;

    /**
     * Type of a {@link List} of strings used for deserialitzing in gson.
     */
    private Type listOfStringsType = new TypeToken<List<String>>() {
    }.getType();

    /**
     * Absolutepath to the resources directory.
     */
    private String path = JsonUtilities.getAbsolutePathAsString();

    /**
     * Constructor for instantiating the JsonIO class, which handles file related
     * tasks for an individual user.
     *
     * @param username
     */
    public JsonIO(final String username) {
        this.user = username.equals("guest") ? null : username;
        this.customCategoryNames = user != null ? new HashSet<>() : null;
    }

    /**
     * Write/Read from jsonfile.
     *
     * @param newPath - The path to read/write files to
     */
    public JsonIO(final Path newPath) {
        path = newPath.toString();
    }

    /**
     * Fetches the names of all the categories available to the current user.
     *
     * @return Set<String> containing all the categories available to the current
     *         user.
     */
    public Set<String> getAllCategories() {
        return Set.copyOf(Arrays.asList(new File(path + "/default_categories").listFiles())
                .stream()
                .map((category) -> category.getName().replace(".json", ""))
                .toList());
    }

    @Override
    public boolean addUser(final User newUser) {
        if (new File(path + "/users/" + newUser.getUsername() + ".json").exists()) {
            throw new IllegalArgumentException("User " + newUser.getUsername() + " already exists.");
        }
        try (FileWriter fw = new FileWriter(path + "/users/" + newUser.getUsername() + ".json",
                StandardCharsets.UTF_8)) {
            GSON.toJson(newUser, fw);
            System.out.println("User " + newUser.getUsername() + " successfully created.");
            return true;
        } catch (IOException e) {
            System.out.println("Couldn't add user " + newUser.getUsername() + " because: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteUser(final String username) {
        File userDel = new File(path + "/users/" + username + ".json");
        if (userDel.delete()) {
            System.out.println(username + " deleted successfully");
            return true;
        } else {
            throw new IllegalArgumentException(
                    "Couldn't delete user because user does not exits or user has security measures that prevent deletion");
        }
    }

    /**
     * Fetches the words contained in the wordlist of the given category.
     *
     * @param category The category to fetch the wordlist of.
     * @return List<String> containing all the words in the categories wordlist.
     * @throws IOException      If any issues are encountered during interaction
     *                          with the files.
     * @throws RuntimeException If the given category is present neither among the
     *                          default nor the custom categories.
     */

    public List<String> getCategoryWordlist(final String category) throws IOException, RuntimeException {
        if (DEFAULT_CATEGORY_NAMES.contains(category)) {
            return getDefaultCategory(category);
        }
        if (user != null && customCategoryNames.contains(category)) {
            return getUser(user).getCustomCategories().get(category);
        }
        throw new RuntimeException("Error fetching categories");
    }

    @Override
    public List<String> getDefaultCategory(final String category) throws IOException {
        try {
            String answers = Files.readString(
                    Paths.get(path + "/default_categories/"
                            + category + ".json"));
            return GSON.fromJson(answers, listOfStringsType);
        } catch (IOException e) {
            throw new IllegalArgumentException("Couldn't get user " + category + "because: " + e.getMessage());
        }
    }

    @Override
    public String getUserAsJson(final String username) {
        try {
            return JsonUtilities.GSON.toJson(Files.readString(Paths.get(path + "/users/" + username + ".json")));
        } catch (IOException e) {
            System.out.println("Couldn't get user " + username + "because: " + e.getMessage());
            return null;
        }
    }

    /**
     * Updates the provided user.
     *
     * @param userParameter The user to update.
     */
    public void updateUser(final User userParameter) {
        if (new File(path + "/users/" + userParameter.getUsername() + ".json").exists()) {
            System.out.println("Eksisterer");
            try (FileWriter fw = new FileWriter(path + "/users/" + userParameter.getUsername() + ".json",
                    StandardCharsets.UTF_8)) {
                GSON.toJson(userParameter, fw);
                System.out.println("User " + userParameter.getUsername() + " successfully updated.");
            } catch (IOException e) {
                System.out
                        .println("Couldn't update user " + userParameter.getUsername() + " because: " + e.getMessage());
            }
        } else {
            throw new IllegalArgumentException("User: " + userParameter.getUsername() + " not found");
        }
    }

    @Override
    public User getUser(final String username) {
        try {
            String jsonString = Files.readString(Paths.get(path + "/users/" + username + ".json"));
            return GSON.fromJson(jsonString, User.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error when retrieving " + username + ": " + e.getMessage());
        }
    }

    @Override
    public void updateCurrentUser(final Predicate<User> predicate) throws IOException {
        String userPath = path + "users/" + user + ".json";
        if (predicate.test(getUser(user))) {
            if (new File(userPath).exists()) {
                FileWriter fw = new FileWriter(userPath, StandardCharsets.UTF_8);
                JsonUtilities.GSON.toJson(user, fw);
                fw.close();
            } else {
                throw new IOException("User not found in " + path);
            }
        }
    }

    /**
     * Provides absolute path to current working directory.
     * Retrieves a certain property from the current user.
     *
     * @param <T>      Specification of return type.
     * @param function Functional interface to access the desired property.
     * @return The retrieved property.
     */
    public <T> T getUserProperty(final Function<User, T> function) {
        return function.apply(getUser(user));
    }

    @Override
    public List<String> getAllUsernames() {
        return Arrays
                .asList(new File(path + "/users").listFiles())
                .stream()
                .map((name) -> name.getName().replace(".json", "")).toList();
    }

    /**
     * API call to convert a {@link String} to {@link User} object.
     *
     * @param json - The json string
     * @return a {@link User} object
     */
    public User convertToJavaObject(final String json) {
        return GSON.fromJson(json, User.class);
    }

    /**
     * Get the absolute path to the spesified directory.
     *
     * @param directory - The directory to find absolute path from
     * @return - A string of the path
     */
    public static String getAbsolutePath(final String directory) {
        Path absolutePath = Paths.get("").toAbsolutePath();
        while (!absolutePath.endsWith(directory)) {
            absolutePath = absolutePath.getParent();
            if (absolutePath == null) {
                throw new IllegalStateException("Working directory not found.");
            }
        }
        return absolutePath.toString();
    }

}
