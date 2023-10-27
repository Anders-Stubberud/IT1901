package persistence;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

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
     * The path where the files will be read/written.
     */
    private final String path;

    /**
     * Gson instance for serialization/deserialization.
     */
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Type of a {@link List} of strings used for deserialitzing in gson.
     */
    private Type listOfStringsType = new TypeToken<List<String>>() {
    }.getType();

    /**
     * Provides absolute path to current working directory.
     *
     * @param directory - The directory to find the path to.
     * @return absolute path to current working directory as {@link String}.
     */
    private String getAbsolutePath(final String directory) {
        Path absolutePath = Paths.get("").toAbsolutePath();
        while (!absolutePath.endsWith(directory)) {
            absolutePath = absolutePath.getParent();
            if (absolutePath == null) {
                throw new IllegalStateException("Working directory not found.");
            }
        }
        return absolutePath.toString();
    }

    /**
     * Constructor used for writing/reading to and from json file.
     * Initializes with default path for storage
     */
    public JsonIO() {
        this.path = getAbsolutePath("gr2325") + "/WordDetective/persistence/src/main/resources";
    }

    @Override
    public void addUser(final User user) {
        try (FileWriter fw = new FileWriter(path + "/users/" + user.getUsername() + ".json", StandardCharsets.UTF_8)) {
            GSON.toJson(user, fw);
            System.out.println("User " + user.getUsername() + " successfully created.");
        } catch (IOException e) {
            System.out.println("Couldn't add user " + user.getUsername() + " because: " + e.getMessage());
        }
    }

    @Override
    public void deleteUser(final String username) {
        File user = new File(path + "/users/" + username + ".json");
        if (user.delete()) {
            System.out.println(username + " deleted successfully");
        } else {
            System.out.println("Error when deleting this file");
        }
    }

    @Override
    public User getUser(final String username) {
        try {
            String jsonString = Files.readString(Paths.get(path + "/users/" + username + ".json"));
            return GSON.fromJson(jsonString, User.class);
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
    public List<String> getAllUsernames() {
        List<String> result = new ArrayList<>();
        File[] nameFiles = new File(path + "/users").listFiles();
        if (nameFiles != null) {
            for (File file : nameFiles) {
                result.add(file.getName().replace(".json", ""));
            }
        } else {
            throw new RuntimeException("User directory not present in" + path);
        }
        return result;
    }

    @Override
    public List<String> getDefaultCategory(final String category) {
        try {
            String answers = Files.readString(Paths.get(path + "/default_categories/" + category + ".json"));
            return GSON.fromJson(answers, listOfStringsType);
        } catch (IOException e) {
            System.out.println("Couldn't find default category: " + category + " because " + e.getMessage());
            return null;
        }
    }

    @Override
    public HashMap<String, List<String>> getAllDefaultCategories() {
        try {
            HashMap<String, List<String>> result = new HashMap<>();
            File[] categories = new File(path + "/default_categories").listFiles();
            if (categories != null) {
                for (File category : categories) {
                    result.put(category.getName().replace(".json", ""),
                            GSON.fromJson(Files.readString(Paths.get(category.toString())),
                                    listOfStringsType));
                }
                return result;
            } else {
                throw new RuntimeException("Could not find categories in " + path + "/default_categories");
            }
        } catch (Exception e) {
            System.out.println("Couldn't get all default categories because: " + e.getMessage());
            return null;
        }
    }

    /**
     * Used in API call to convert string representation of json into java object.
     * @param json String representation of a json file.
     * @return User java object equivalent of the json string representation.
     */
    public static User convertToJavaObject(final String json) {
        return GSON.fromJson(json, User.class);
    }

}
