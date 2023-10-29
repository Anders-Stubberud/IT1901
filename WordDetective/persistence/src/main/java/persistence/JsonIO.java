package persistence;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

import com.google.gson.reflect.TypeToken;

import types.User;

/**
 * This class is responsible for reading the wordlists from the files.
 * It also provides methods for querying the available categories.
 */
public final class JsonIO implements AbstractJsonIO {

    // /**
    //  * The path where the files will be read/written.
    //  */
    // private final String path;

    private final User user;
    private final String pathToPersistenceUser;
    private static final Set<String> defaultCategoryNames = JsonUtilities.getPersistentFilenames("/default_categories");

    public JsonIO(String username) {
        this.pathToPersistenceUser = JsonUtilities.pathToResources + "/users/" + username + ".json";
        this.user = loadCurrentUser();
    }

    // /**
    //  * Gson instance for serialization/deserialization.
    //  */
    // private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Type of a {@link List} of strings used for deserialitzing in gson.
     */
    private Type listOfStringsType = new TypeToken<List<String>>() {
    }.getType();

    // @Override
    // public boolean addedUserSuccessfully(final User user) {
    //     try (FileWriter fw = new FileWriter(path + "/users/" + user.getUsername() + ".json", StandardCharsets.UTF_8)) {
    //         GSON.toJson(user, fw);
    //         System.out.println("User " + user.getUsername() + " successfully created.");
    //         return true;
    //     } catch (IOException e) {
    //         System.out.println("Couldn't add user " + user.getUsername() + " because: " + e.getMessage());
    //         return false;
    //     }
    // }

    public List<String> getCategoryWordlist(String category) throws IOException, RuntimeException {
        if (defaultCategoryNames.contains(category)) {
            return getDefaultCategory(category);
        }
        if (user.getCustomCategories().keySet().contains(category)) {
            return user.getCustomCategories().get(category);
        }
        throw new RuntimeException("Error fetching categories");
    }

    @Override
    public List<String> getDefaultCategory(final String category) throws IOException {
        try {
            String answers = Files.readString(Paths.get(pathToPersistenceUser));
            return JsonUtilities.GSON.fromJson(answers, listOfStringsType);
        } catch (IOException e) {
            throw e;
        }
    }

    @Override
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
    public void updateCurrentUser(Predicate<User> predicate) throws IOException {
        if (predicate.test(user)) {
            if (new File(pathToPersistenceUser).exists()) {
                FileWriter fw = new FileWriter(pathToPersistenceUser, StandardCharsets.UTF_8);
                JsonUtilities.GSON.toJson(user, fw);
            } else {
                throw new IOException("User not found in " + pathToPersistenceUser);
            }
        }
    }

    // @Override
    // public List<String> getAllUsernames() {
    //     List<String> result = new ArrayList<>();
    //     File[] nameFiles = new File(path + "/users").listFiles();
    //     if (nameFiles != null) {
    //         for (File file : nameFiles) {
    //             result.add(file.getName().replace(".json", ""));
    //         }
    //     } else {
    //         throw new RuntimeException("User directory not present in" + path);
    //     }
    //     return result;
    // }

    // @Override
    // public HashMap<String, List<String>> getAllDefaultCategories() {
    //     try {
    //         HashMap<String, List<String>> result = new HashMap<>();
    //         File[] categories = new File(pathToPersistenceUser).listFiles();
    //         if (categories != null) {
    //             for (File category : categories) {
    //                 result.put(category.getName().replace(".json", ""),
    //                         JsonUtilities.GSON.fromJson(Files.readString(Paths.get(category.toString())),
    //                                 listOfStringsType));
    //             }
    //             return result;
    //         } else {
    //             throw new RuntimeException("Could not find categories in " + pathToDefaultCategories + "/default_categories");
    //         }
    //     } catch (Exception e) {
    //         System.out.println("Couldn't get all default categories because: " + e.getMessage());
    //         return null;
    //     }
    // }

    // /**
    //  * Used in API call to convert string representation of json into java object.
    //  * 
    //  * @param json String representation of a json file.
    //  * @return User java object equivalent of the json string representation.
    //  */
    // public static User convertToJavaObject(final String json) {
    //     return JsonUtilities.GSON.fromJson(json, User.class);
    // }

}
