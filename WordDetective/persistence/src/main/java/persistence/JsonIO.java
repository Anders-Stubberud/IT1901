package persistence;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.gson.reflect.TypeToken;

import types.User;

/**
 * This class is responsible for reading the wordlists from the files.
 * It also provides methods for querying the available categories.
 */
public final class JsonIO implements AbstractJsonIO {

    /**
     * The game's user instance, which is an object with identical state to the persistent json file of the current user.
     */
    private User user;

    /**
     * Absolute path for reading from and writing to the persistent json file of the current user.
     */
    private final String pathToPersistenceUser;

    /**
     * Set containing the default categories, which are shared amongst all users.
     */
    private static final Set<String> DEFAULT_CATEGORY_NAMES = JsonUtilities.getPersistentFilenames("/default_categories");

    /**
     * Set containing teh custom categories, which are unique to each user.
     */
    private Set<String> customCategoryNames;

    /**
     * Constructor for instantiating the JsonIO class, which handles file related tasks for an individual user.
     * @param username
     */
    public JsonIO(final String username) {
        this.pathToPersistenceUser = JsonUtilities.PATH_TO_RESOURCES + "/users/" + username + ".json";
        this.user = username.equals("guest") ? null : loadCurrentUser();
        this.customCategoryNames = user != null ? user.getCustomCategories().keySet() : new HashSet<>();
    }

    /**
     * Type of a {@link List} of strings used for deserialitzing in gson.
     */
    private Type listOfStringsType = new TypeToken<List<String>>() {
    }.getType();

    /**
     * Fetches the names of all the categories available to the current user.
     * @return Set<String> containing all the categories available to the current user.
     */
    public Set<String> getAllCategories() {
        return Stream.concat(DEFAULT_CATEGORY_NAMES.stream(), customCategoryNames.stream()).collect(Collectors.toSet());
    }

    /**
     * Fetches the words contained in the wordlist of the given category.
     * @param category The category to fetch the wordlist of.
     * @return List<String> containing all the words in the categories wordlist.
     * @throws IOException If any issues are encountered during interaction with the files.
     * @throws RuntimeException If the given category is present neither among the default nor the custom categories.
     */
    public List<String> getCategoryWordlist(final String category) throws IOException, RuntimeException {
        if (DEFAULT_CATEGORY_NAMES.contains(category)) {
            return getDefaultCategory(category);
        }
        if (user != null && customCategoryNames.contains(category)) {
            return user.getCustomCategories().get(category);
        }
        throw new RuntimeException("Error fetching categories");
    }

    @Override
    public List<String> getDefaultCategory(final String category) throws IOException {
        try {
            String answers = Files.readString(
                Paths.get(JsonUtilities.PATH_TO_RESOURCES + "/default_categories" + JsonUtilities.getCategoryFilename(category))
            );
            return JsonUtilities.GSON.fromJson(answers, listOfStringsType);
        } catch (IOException e) {
            throw e;
        }
    }

    @Override
    public String getUserAsJson(final String username) {
        try {
            return GSON.toJson(Files.readString(Paths.get(path + "/users/" + username + ".json")));
        } catch (IOException e) {
            System.out.println("Couldn't get user " + username + "because: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void updateUser(final User user) {
        if (new File(path + "/users/" + user.getUsername() + ".json").exists()) {
            try (FileWriter fw = new FileWriter(path + "/users/" + user.getUsername() + ".json",
                    StandardCharsets.UTF_8)) {
                        GSON.toJson(user, fw);
                System.out.println("User " + user.getUsername() + " successfully updated.");
            } catch (IOException e) {
                System.out
                        .println("Couldn't update user " + user.getUsername() + " because: " + e.getMessage());
            }
        } else {
            System.out.println("User: " + user.getUsername() + " not found");
        }
    }

    @Override
    public User loadCurrentUser() throws RuntimeException {
        try {
            String jsonString = Files.readString(Paths.get(pathToPersistenceUser));
            return JsonUtilities.GSON.fromJson(jsonString, User.class);
        } catch (IOException e) {
            throw new RuntimeException("Error " + e.getMessage());
        }
    }

    @Override
    public void updateCurrentUser(final Predicate<User> predicate) throws IOException {
        if (predicate.test(user)) {
            if (new File(pathToPersistenceUser).exists()) {
                FileWriter fw = new FileWriter(pathToPersistenceUser, StandardCharsets.UTF_8);
                JsonUtilities.GSON.toJson(user, fw);
                fw.close();
                this.user = loadCurrentUser();
            } else {
                throw new IOException("User not found in " + pathToPersistenceUser);
            }
        }
    }

}
