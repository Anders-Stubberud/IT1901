package persistence;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
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
        this.path = getAbsolutePath("gr2325") + "/WordDetective/persistence/src/main/resources/";
    }

    @Override
    public void addUser(User user) {
        try (FileWriter fw = new FileWriter(path + "users/" + user.getUsername() + ".json", StandardCharsets.UTF_8)) {
            gson.toJson(user, fw);
            System.out.println("User " + user.getUsername() + " successfully created.");
        } catch (IOException e) {
            System.out.println("Couldn't add user " + user.getUsername() + " because: " + e.getMessage());
        }
    }

    @Override
    public void deleteUser(String username) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteUser'");
    }

    @Override
    public User getUser(String username) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUser'");
    }

    @Override
    public void updateUser(String username) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateUser'");
    }

    @Override
    public List<String> getDefaultCategory(String category) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDefaultCategory'");
    }

    @Override
    public HashMap<String, List<String>> getAllDefaultCategories() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllDefaultCategories'");
    }

    public static void main(String args[]) {
        JsonIO js = new JsonIO();
        User user = new User("Bob", "Bob123");
        user.addCustomCategories("Test1", Arrays.asList("2", "3", "3", "1"));
        user.addCustomCategories("Test2", Arrays.asList("2", "3", "3", "1"));
        js.addUser(user);
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
