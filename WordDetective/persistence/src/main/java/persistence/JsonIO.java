package persistence;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
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
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Constructor used for writing/reading to and from json file.
     * Initializes with default path for storage
     */
    public JsonIO() {
        this.path = getAbsolutePath("gr2325") + "/WordDetective/persistence/src/main/resources";
    }

    @Override
    public void addUser(User user) {
        try (FileWriter fw = new FileWriter(path + "/users/" + user.getUsername() + ".json", StandardCharsets.UTF_8)) {
            gson.toJson(user, fw);
            System.out.println("User " + user.getUsername() + " successfully created.");
        } catch (IOException e) {
            System.out.println("Couldn't add user " + user.getUsername() + " because: " + e.getLocalizedMessage());
        }
    }

    @Override
    public void deleteUser(String username) {
        File user = new File(path + "/users/" + username + ".json");
        if (user.delete()) {
            System.out.println(username + " deleted successfully");
        } else {
            System.out.println("Error when deleting this file");
        }
    }

    @Override
    public User getUser(String username) {
        try {
            String jsonString = Files.readString(Paths.get(path + "/users/" + username + ".json"));
            return gson.fromJson(jsonString, User.class);
        } catch (IOException e) {
            System.out.println("Couldn't get user " + username + "because: " + e.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public void updateUser(User user) {
        if (new File(path + "/users/" + user.getUsername() + ".json").exists()) {
            try (FileWriter fw = new FileWriter(path + "/users/" + user.getUsername() + ".json",
                    StandardCharsets.UTF_8)) {
                gson.toJson(user, fw);
                System.out.println("User " + user.getUsername() + " successfully updated.");
            } catch (IOException e) {
                System.out
                        .println("Couldn't update user " + user.getUsername() + " because: " + e.getLocalizedMessage());
            }
        } else {
            System.out.println("User: " + user.getUsername() + " not found");
        }
    }

    @Override
    public List<String> getDefaultCategory(String category) {
        try {
            Type listOfStrings = new TypeToken<List<String>>() {
            }.getClass();
            String answers = Files.readString(Paths.get(path + "/default_categories/" + category + ".json"));
            return gson.fromJson(answers, listOfStrings);
        } catch (IOException e) {
            System.out.println("Couldn't find default category: " + category + " because " + e.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public HashMap<String, List<String>> getAllDefaultCategories() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllDefaultCategories'");
    }

    public static void main(String args[]) {
        JsonIO js = new JsonIO();
        User user = new User("Crayon", "Bob123");
        List<String> list = js.getDefaultCategory("us_states");
        System.out.println(list.toString());

    }

    /**
     * Provides absolute path to current working directory.
     *
     * @param directory - The directory to find the path to.
     * @return absolute path to current working directory as {@link String}.
     */
    private String getAbsolutePath(String directory) {
        Path path = Paths.get("").toAbsolutePath();
        while (!path.endsWith(directory)) {
            path = path.getParent();
            if (path == null) {
                throw new IllegalStateException("Working directory not found.");
            }
        }
        return path.toString();
    }

}
