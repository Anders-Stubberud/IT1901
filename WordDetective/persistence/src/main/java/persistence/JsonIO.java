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

    private User user;
    private final String pathToPersistenceUser;
    private static final Set<String> defaultCategoryNames = JsonUtilities.getPersistentFilenames("/default_categories");
    private Set<String> customCategoryNames;

    public JsonIO(final String username) {
        this.pathToPersistenceUser = JsonUtilities.pathToResources + "/users/" + username + ".json";
        this.user = username.equals("guest") ? null : loadCurrentUser();
        this.customCategoryNames = user != null ? user.getCustomCategories().keySet() : new HashSet<>();
    }

    /**
     * Type of a {@link List} of strings used for deserialitzing in gson.
     */
    private Type listOfStringsType = new TypeToken<List<String>>() {
    }.getType();

    public Set<String> getAllCategories() {
        return Stream.concat(defaultCategoryNames.stream(), customCategoryNames.stream()).collect(Collectors.toSet());
    }

    public List<String> getCategoryWordlist(String category) throws IOException, RuntimeException {
        if (defaultCategoryNames.contains(category)) {
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
            String answers = Files.readString(Paths.get(JsonUtilities.pathToResources + "/default_categories" + JsonUtilities.getCategoryFilename(category)));
            return JsonUtilities.GSON.fromJson(answers, listOfStringsType);
        } catch (IOException e) {
            throw e;
        }
    }

    @Override
    //implementere en mekanisme som hindrer forsøk i å slette "guest" user
    public void deleteCurrentUser() throws RuntimeException {
        File user = new File(pathToPersistenceUser);
        if (!user.delete()) {
            throw new RuntimeException("Error deleting user");
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
